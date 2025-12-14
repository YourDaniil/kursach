package org.example.Services;

import org.example.Dtos.ForAdd.AddRecipeDto;
import org.example.Dtos.ForShow.ShowInfoRecipeDto;
import org.example.Models.Entites.Difficult;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipeService {
    void saveAddRecipeDto(AddRecipeDto addRecipeDto);
    void saveRecipe(Recipe recipe);
    List<ShowInfoRecipeDto> allRecipes();
    ShowInfoRecipeDto findRecipe(String title);
    ShowInfoRecipeDto findRandom();
    void deleteRecipe(String title);
    List<ShowInfoRecipeDto> findFiveRandom();
    int countAllRecipes();
    List<ShowInfoRecipeDto> findByCookTime(int begin, int end);
    List<ShowInfoRecipeDto> findByDifficult(Difficult difficult);
    List<ShowInfoRecipeDto> findAll();
    Page<ShowInfoRecipeDto> allRecipesPaginated(Pageable pageable);
    void saveNewReceptByUser();
    List<ShowInfoRecipeDto> findByMaster(User master);
    Recipe findRecipeByTitle(String title);
    List<ShowInfoRecipeDto> findRecipeByWhoLikeIt(String userName);
    void removeRecipeFromFavorites(String recipeTitle, String username);
}
