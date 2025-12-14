package org.example.Services;

import org.example.Dtos.ForAdd.AddCommentDto;
import org.example.Dtos.ForShow.ShowInfoCommentDto;
import org.example.Models.Entites.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommentService {
    void addComment(AddCommentDto addCommentDto);
    List<ShowInfoCommentDto> allComments();
    void deleteComment(String id);
    List<ShowInfoCommentDto> findByUser(User user);
    ShowInfoCommentDto findByComment(String comment);
    void saveNewCommentIntoRecipe(AddCommentDto commentModel,
                                  UserDetails userDetails,
                                  String recipeTitle);
}
