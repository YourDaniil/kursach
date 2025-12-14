package org.example.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.AddUserDto;
import org.example.Repositories.UserRepository;
import org.example.Services.CategoryService;
import org.example.Services.RecipeService;
import org.example.Services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class HomeController {
    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public HomeController(RecipeService recipeService,
                          CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        log.debug("Переход на главную страницу");
        model.addAttribute("randomRecipeInfos", recipeService.findRandom());
        model.addAttribute("categoryInfos", categoryService.findFiveRandom());
        model.addAttribute("fiveRandomRecipeInfos", recipeService.findFiveRandom());
        return "homePage";
    }
}
