package org.example.Services.Impl;

import org.example.Dtos.ForAdd.AddCommentDto;
import org.example.Dtos.ForShow.ShowInfoCommentDto;
import org.example.Models.Entites.Comment;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.User;
import org.example.Models.Exceptions.CommentNotFoundException;
import org.example.Repositories.CommentRepository;
import org.example.Repositories.RecipeRepository;
import org.example.Repositories.UserRepository;
import org.example.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository,
                              RecipeRepository recipeRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "comments", allEntries = true)
    public void addComment(AddCommentDto addCommentDto) {
        commentRepository.save(mapper.map(addCommentDto, Comment.class));
    }

    @Override
    @Cacheable(value = "comments", key = "'all'")
    public List<ShowInfoCommentDto> allComments() {
        return commentRepository.findAll().stream().
                map(comment -> mapper.map(comment, ShowInfoCommentDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"comments", "recipes", "recipe"}, allEntries = true)
    public void deleteComment(String id) {
        if (!commentRepository.existsById(id))
            throw new CommentNotFoundException("Такой комментарий не найден!");
        commentRepository.deleteById(id);
    }

    @Override
    public List<ShowInfoCommentDto> findByUser(User user) {
        return commentRepository.findByUser(user).stream().map(comment ->
                mapper.map(comment, ShowInfoCommentDto.class)).collect(Collectors.toList());
    }

    @Override
    public ShowInfoCommentDto findByComment(String comment) {
        return mapper.map(commentRepository.findByComment(comment), ShowInfoCommentDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"comments", "recipes", "recipe"}, allEntries = true)
    public void saveNewCommentIntoRecipe(AddCommentDto commentModel,
                                         UserDetails userDetails,
                                         String recipeTitle) {
        String username = userDetails.getUsername();
        User userEntity = userRepository.findByUserName(username).get();
        commentModel.setUser(userEntity);

        Comment comment = mapper.map(commentModel, Comment.class);
        commentRepository.save(comment);

        Recipe recipe = recipeRepository.findByTitle(recipeTitle).get();
        List<Comment> comments = recipe.getComments();
        comments.add(comment);
        recipe.setComments(comments);

        recipeRepository.save(recipe);
    }
}