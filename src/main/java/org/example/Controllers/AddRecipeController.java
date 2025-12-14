package org.example.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.AddRecipeDto;
import org.example.Models.Entites.Difficult;
import org.example.Models.Entites.Ingredient;
import org.example.Services.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class AddRecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;
    private final DifficultService difficultService;
    private final IngredientService ingredientService;
    private final UserService userService;

    public AddRecipeController(RecipeService recipeService, CategoryService categoryService,
                               DifficultService difficultService, IngredientService ingredientService,
                               UserService userService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.difficultService = difficultService;
        this.ingredientService = ingredientService;
        this.userService = userService;
    }

    private void addAttributesToModel(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("difficulties", Arrays.asList("Легко", "Средне", "Шеф-повар"));
    }

    private void syncLists(List<String> names, List<String> amounts, List<String> units) {
        int targetSize = names.size();
        while (amounts.size() < targetSize) {
            amounts.add("");
        }
        while (units.size() < targetSize) {
            units.add("");
        }
    }

    @GetMapping("/add")
    public String showAddRecipeForm(Model model) {
        log.debug("Открытие формы добавления рецепта");
        model.addAttribute("addRecipeDto", new AddRecipeDto());
        List<String> initialList = new ArrayList<>();
        initialList.add("");

        model.addAttribute("ingNames", initialList);
        model.addAttribute("ingAmounts", new ArrayList<>(initialList));
        model.addAttribute("ingUnits", new ArrayList<>(initialList));

        addAttributesToModel(model);
        return "addNewRecipe";
    }

    @PostMapping(value = "/add", params = "action=addIngredient")
    public String addIngredientRow(
            @ModelAttribute("addRecipeDto") AddRecipeDto dto,
            @RequestParam(value = "ingName", required = false) List<String> ingNames,
            @RequestParam(value = "ingAmount", required = false) List<String> ingAmounts,
            @RequestParam(value = "ingUnit", required = false) List<String> ingUnits,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "difficultType", required = false) String difficultType,
            Model model) {
        log.debug("Пользователь добавил строку ингредиента в форму");

        if (ingNames == null) ingNames = new ArrayList<>();
        if (ingAmounts == null) ingAmounts = new ArrayList<>();
        if (ingUnits == null) ingUnits = new ArrayList<>();

        syncLists(ingNames, ingAmounts, ingUnits);

        ingNames.add("");
        ingAmounts.add("");
        ingUnits.add("");

        model.addAttribute("ingNames", ingNames);
        model.addAttribute("ingAmounts", ingAmounts);
        model.addAttribute("ingUnits", ingUnits);

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("difficultType", difficultType);

        addAttributesToModel(model);
        return "addNewRecipe";
    }

    @PostMapping(value = "/add", params = "removeIngredient")
    public String removeIngredientRow(
            @ModelAttribute("addRecipeDto") AddRecipeDto dto,
            @RequestParam(value = "ingName", required = false) List<String> ingNames,
            @RequestParam(value = "ingAmount", required = false) List<String> ingAmounts,
            @RequestParam(value = "ingUnit", required = false) List<String> ingUnits,
            @RequestParam("removeIngredient") int rowIndex,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "difficultType", required = false) String difficultType,
            Model model) {
        log.debug("Пользователь удалил строку ингредиента в форме");

        if (ingNames == null) ingNames = new ArrayList<>();
        if (ingAmounts == null) ingAmounts = new ArrayList<>();
        if (ingUnits == null) ingUnits = new ArrayList<>();

        syncLists(ingNames, ingAmounts, ingUnits);

        if (rowIndex >= 0 && rowIndex < ingNames.size()) {
            ingNames.remove(rowIndex);
            ingAmounts.remove(rowIndex);
            ingUnits.remove(rowIndex);
        }

        model.addAttribute("ingNames", ingNames);
        model.addAttribute("ingAmounts", ingAmounts);
        model.addAttribute("ingUnits", ingUnits);

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("difficultType", difficultType);

        addAttributesToModel(model);
        return "addNewRecipe";
    }

    @PostMapping(value = "/add", params = "action=save")
    public String saveRecipe(
            @ModelAttribute("addRecipeDto") AddRecipeDto addRecipeDto,
            @RequestParam(value = "ingName", required = false) List<String> ingNames,
            @RequestParam(value = "ingAmount", required = false) List<String> ingAmounts,
            @RequestParam(value = "ingUnit", required = false) List<String> ingUnits,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("difficultType") String difficultType,
            RedirectAttributes redirectAttributes,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Попытка сохранения рецепта пользователем: {}", userDetails.getUsername());

        try {
            if (ingNames == null) ingNames = new ArrayList<>();
            if (ingAmounts == null) ingAmounts = new ArrayList<>();
            if (ingUnits == null) ingUnits = new ArrayList<>();

            syncLists(ingNames, ingAmounts, ingUnits);

            addRecipeDto.setImageRoot("/pic/nullFood.png");

            addRecipeDto.setCategory(categoryService.forSetCategory(categoryName));

            Difficult difficult = difficultService.translateDifficult(difficultType);

            addRecipeDto.setDifficult(difficult);

            List<Ingredient> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingNames.size(); i++) {
                String rawName = ingNames.get(i);
                if (rawName != null && !rawName.isBlank()) {
                    String amount = ingAmounts.get(i);
                    String unit = ingUnits.get(i);
                    String fullName = rawName + (amount.isBlank() ? "" : " - " + amount + " " + unit);

                    ingredientsList.add(ingredientService.forSetIngredient(fullName));
                }
            }

            addRecipeDto.setIngredients(ingredientsList);
            addRecipeDto.setAvgRating(0.0);

            String username = userDetails.getUsername();
            addRecipeDto.setMaster(userService.forSetUser(username));

            recipeService.saveAddRecipeDto(addRecipeDto);

            log.info("Рецепт '{}' успешно сохранен", addRecipeDto.getTitle());
            redirectAttributes.addFlashAttribute("successMessage", "Рецепт успешно добавлен!");
            return "redirect:/recipes/all";

        } catch (Exception e) {
            log.error("Ошибка при сохранении рецепта: ", e);

            model.addAttribute("errorMessage", "Ошибка: " + e.getMessage());

            syncLists(ingNames, ingAmounts, ingUnits);
            model.addAttribute("ingNames", ingNames);
            model.addAttribute("ingAmounts", ingAmounts);
            model.addAttribute("ingUnits", ingUnits);

            model.addAttribute("categoryName", categoryName);
            model.addAttribute("difficultType", difficultType);

            addAttributesToModel(model);
            return "addNewRecipe";
        }
    }
}