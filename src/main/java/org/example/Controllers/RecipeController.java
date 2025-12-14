package org.example.Controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.AddCommentDto;
import org.example.Models.Entites.Recipe;
import org.example.Models.Entites.User;
import org.example.Services.CommentService;
import org.example.Services.RecipeService;
import org.example.Services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final CommentService commentService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, CommentService commentService,
                            UserService userService) {
        this.recipeService = recipeService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @GetMapping("/{recipeTitle}")
    public String recipeDetails(@PathVariable("recipeTitle") String recipeTitle, Model model) {
        log.debug("Запрос детальной страницы для рецепта: {}", recipeTitle);
        model.addAttribute("recipeDetails", recipeService.findRecipe(recipeTitle));
        model.addAttribute("userDetails", recipeService.findRecipe(recipeTitle));
        return "recipe";
    }

    @PostMapping("/{recipeTitle}/comment/add")
    public String addCompany(@Valid AddCommentDto commentModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @PathVariable("recipeTitle") String recipeTitle,
                             @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Пользователь {} пытается добавить комментарий к рецепту: {}",
                userDetails.getUsername(), recipeTitle);

        if (bindingResult.hasErrors()) {
            log.warn("Ошибка валидации комментария от пользователя {}: {}",
                    userDetails.getUsername(), bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("commentModel", commentModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentModel",
                    bindingResult);
            return "redirect:/recipe/{recipeTitle}";
        }

        commentService.saveNewCommentIntoRecipe(commentModel, userDetails, recipeTitle);

        log.info("Комментарий успешно добавлен к рецепту '{}'", recipeTitle);

        redirectAttributes.addFlashAttribute("successMessage",
                "Комментарий успешно добавлен!");

        return "redirect:/recipe/{recipeTitle}";
    }

    @PostMapping("/{recipeTitle}/favourite/add")
    public String addFavouriteRecipe(@PathVariable("recipeTitle") String recipeTitle,
                             @AuthenticationPrincipal UserDetails userDetails) {
        log.info("Пользователь {} добавляет рецепт '{}' в избранное",
                userDetails.getUsername(), recipeTitle);

        Recipe recipe = recipeService.findRecipeByTitle(recipeTitle);
        List<User> whoLikeIt = recipe.getWhoLikeIt();
        whoLikeIt.add(userService.forSetUser(userDetails.getUsername()));
        recipe.setWhoLikeIt(whoLikeIt);
        recipeService.saveRecipe(recipe);
        log.info("Рецепт '{}' успешно добавлен в избранное", recipeTitle);

        return "redirect:/recipe/{recipeTitle}";
    }
}
