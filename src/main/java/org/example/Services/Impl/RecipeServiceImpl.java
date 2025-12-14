package org.example.Services.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.AddRecipeDto;
import org.example.Dtos.ForShow.ShowInfoRecipeDto;
import org.example.Models.Entites.Difficult;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.User;
import org.example.Models.Exceptions.RecipeNotFoundException;
import org.example.Repositories.RecipeRepository;
import org.example.Services.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper mapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper mapper) {
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "recipes", allEntries = true)
    public void saveAddRecipeDto(AddRecipeDto addRecipeDto) {
        Recipe recipe = mapper.map(addRecipeDto, Recipe.class);
        recipeRepository.save(recipe);
        log.info("Рецепт сохранен: {}", recipe.getTitle());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"recipes", "recipe"}, allEntries = true)
    public void saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    @Cacheable(value = "recipes", key = "'all'")
    public List<ShowInfoRecipeDto> allRecipes() {
        log.debug("Поиск всех рецептов (из БД)");
        return recipeRepository.findAll().stream()
                .map(recipe -> mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "recipe", key = "#title")
    public ShowInfoRecipeDto findRecipe(String title) {
        log.debug("Поиск рецепта: {}", title);
        return mapper.map(recipeRepository.findByTitle(title), ShowInfoRecipeDto.class);
    }

    @Override
    @Cacheable(value = "recipes", key = "'random_one'")
    public ShowInfoRecipeDto findRandom() {
        return mapper.map(recipeRepository.findRandom(), ShowInfoRecipeDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"recipes", "recipe"}, allEntries = true)
    public void deleteRecipe(String title) {
        if (!recipeRepository.existsByTitle(title))
            throw new RecipeNotFoundException("Рецепт \"" + title + "\" не найден");
        recipeRepository.deleteByTitle(title);
    }

    @Override
    @Cacheable(value = "recipes", key = "'random_five'")
    public List<ShowInfoRecipeDto> findFiveRandom() {
        return recipeRepository.findFiveRandom().stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    public int countAllRecipes() {
        return recipeRepository.countAllRecipes();
    }

    @Override
    public List<ShowInfoRecipeDto> findByCookTime(int begin, int end) {
        return recipeRepository.findByCookTimeBetween(begin, end).stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ShowInfoRecipeDto> findByDifficult(Difficult difficult) {
        return recipeRepository.findByDifficult(difficult).stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "recipes", key = "'all_dto'")
    public List<ShowInfoRecipeDto> findAll() {
        return recipeRepository.findAll().stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    public Page<ShowInfoRecipeDto> allRecipesPaginated(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class));
    }

    @Override
    public void saveNewReceptByUser() {
    }

    @Override
    public List<ShowInfoRecipeDto> findByMaster(User master) {
        return recipeRepository.findByMaster(master).stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    public Recipe findRecipeByTitle(String title) {
        return recipeRepository.findByTitle(title).orElseThrow(() -> new RecipeNotFoundException("Рецепт не найден"));
    }

    @Override
    public List<ShowInfoRecipeDto> findRecipeByWhoLikeIt(String userName) {
        return recipeRepository.findByWhoLikeIt_UserName(userName).stream().map(recipe ->
                mapper.map(recipe, ShowInfoRecipeDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = {"recipes", "recipe"}, allEntries = true)
    public void removeRecipeFromFavorites(String recipeTitle, String username) {
        Recipe recipe = recipeRepository.findByTitle(recipeTitle)
                .orElseThrow(() -> new RecipeNotFoundException("Рецепт не найден: " + recipeTitle));
        boolean removed = recipe.getWhoLikeIt().removeIf(user -> user.getUserName().equals(username));

        if (removed) {
            recipeRepository.save(recipe);
        }
    }
}