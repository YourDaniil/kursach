package org.example.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Dtos.ForAdd.UserRegistrationDto;
import org.example.Services.Impl.AuthServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/users")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
        log.info("AuthController инициализирован");
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        log.debug("Переход на страницу регистрации");
        if (!model.containsAttribute("userRegistrationDto")) {
            model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto dto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        log.debug("Попытка регистрации пользователя: {}", dto.getUsername());
        if (bindingResult.hasErrors()) {
            log.warn("Ошибки валидации при регистрации: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDto", bindingResult);
            redirectAttributes.addFlashAttribute("userRegistrationDto", dto);
            return "redirect:/users/register";
        }
        try {
            authService.register(dto);
            log.info("Новый пользователь зарегистрирован: {}", dto.getUsername());
            redirectAttributes.addFlashAttribute("successMessage", "Успешная регистрация! Войдите.");
            return "redirect:/users/login";
        } catch (RuntimeException e) {
            log.error("Ошибка бизнес-логики при регистрации: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("userRegistrationDto", dto);
            return "redirect:/users/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        log.debug("Переход на страницу входа");
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute("username") String username, RedirectAttributes redirectAttributes) {
        log.warn("Неудачная попытка входа для пользователя: {}", username);
        redirectAttributes.addFlashAttribute("username", username);
        redirectAttributes.addFlashAttribute("errorMessage", "Неверный логин или пароль");
        return "redirect:/users/login";
    }
}