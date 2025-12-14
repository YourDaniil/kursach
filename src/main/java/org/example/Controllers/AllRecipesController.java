package org.example.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForShow.ShowInfoRecipeDto;
import org.example.Models.Entites.Category;
import org.example.Models.Enums.RecipeDifficult;
import org.example.Repositories.CategoryRepository;
import org.example.Services.DifficultService;
import org.example.Services.RecipeService;
import org.example.Services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class AllRecipesController {
    private final RecipeService recipeService;
    private final DifficultService difficultService;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public AllRecipesController(RecipeService recipeService, DifficultService difficultService,
                                CategoryRepository categoryRepository, UserService userService) {
        this.recipeService = recipeService;
        this.difficultService = difficultService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String showAllRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(required = false) String categoryType,
            @RequestParam(required = false) String difficultType,
            @RequestParam(required = false) Integer timeType,
            @RequestParam(required = false) Integer countOfServings,
            @RequestParam(required = false) String howSort,
            Model model) {
        log.debug("Запрос списка рецептов. Сортировка: {}, Категория: {}", sortBy, categoryType);

        model.addAttribute("category", categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList()));
        model.addAttribute("recipeDifficult", Arrays.asList("Легко", "Средне", "Шеф-повар"));
        model.addAttribute("time", Arrays.asList(15, 30, 45, 60, 90, 120));
        model.addAttribute("servings", Arrays.asList(3, 6, 9, 12));
        model.addAttribute("howSort", Arrays.asList("По алфавиту"));

        List<ShowInfoRecipeDto> recipesResult = recipeService.findAll();

        System.out.println(recipesResult);

        if (categoryType != null && !categoryType.trim().isEmpty()) {
            recipesResult = recipesResult.stream().filter(recipe ->
                    recipe.getCategory().getName().equals(categoryType)).collect(Collectors.toList());
            model.addAttribute("categoryType", categoryType); // чтобы страница запомнила выбор
        }

        if (difficultType != null && !difficultType.trim().isEmpty()) {

            RecipeDifficult difficult = difficultService.translateDifficult(difficultType).getRecipeDifficult();

            recipesResult = recipesResult.stream().filter(recipe ->
                    recipe.getDifficult().getRecipeDifficult().equals(difficult)).collect(Collectors.toList());
            model.addAttribute("difficultType", difficultType);
        }

        if (timeType != null) {
            recipesResult = recipesResult.stream().filter(recipe ->
                    recipe.getCookTime() <= timeType).collect(Collectors.toList());
            model.addAttribute("timeType", timeType);
        }

        if (countOfServings != null) {
            recipesResult = recipesResult.stream().filter(recipe ->
                    recipe.getServings() >= countOfServings).collect(Collectors.toList());
            model.addAttribute("countOfServings", countOfServings);
        }

        if (howSort != null && !howSort.trim().isEmpty() && howSort.equals("По алфавиту")) {
            recipesResult = recipesResult.stream().sorted(Comparator.comparing(ShowInfoRecipeDto::getTitle)).collect(Collectors.toList());
            model.addAttribute("howSort", howSort);
        }

        log.info("Найдено {} рецептов", recipesResult.size());

        int start = page * size;
        int end = Math.min((start + size), recipesResult.size());

        List<ShowInfoRecipeDto> pageContent;

        if (start > recipesResult.size()) {
            // если запросили 100 страницу а элементов всего 10
            pageContent = new ArrayList<>();
        } else {
            pageContent = recipesResult.subList(start, end);
        }

        model.addAttribute("countAllRecipes", recipesResult.size());
        model.addAttribute("recipes", pageContent);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) recipesResult.size() / size));
        model.addAttribute("totalItems", recipesResult.size());

        return "showAllRecipes";
    }

    @GetMapping("/favourite")
    public String showAllFavouriteRecipes(Model model,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        List<ShowInfoRecipeDto> showInfoRecipeDtos = recipeService.findRecipeByWhoLikeIt(userDetails.getUsername());
        model.addAttribute("favorites", showInfoRecipeDtos);
        return "favouriteRecipes";
    }

    @PostMapping("/favourite/remove")
    public String removeFromFavorites(
            @RequestParam("recipeTitle") String recipeTitle,
            @AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        recipeService.removeRecipeFromFavorites(recipeTitle, userDetails.getUsername());
        redirectAttributes.addFlashAttribute("successMessage", "Рецепт удален из избранного.");

        return "redirect:/recipes/favourite";
    }
}