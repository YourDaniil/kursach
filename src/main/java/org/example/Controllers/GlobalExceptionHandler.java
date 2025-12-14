package org.example.Controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResourceFound(NoResourceFoundException e, Model model) {

        log.warn("Пользователь запросил несуществующий ресурс: /{}", e.getResourcePath());

        model.addAttribute("errorCode", "404");
        model.addAttribute("errorMessage", "Страница, которую вы ищете, не существует.");

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.error("Перехвачено необработанное исключение: ", e);

        model.addAttribute("errorCode", "500");
        model.addAttribute("errorMessage", "Что-то пошло не так на сервере. Подробности в логах.");

        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAccessDenied(AccessDeniedException e, Model model) {
        log.warn("Попытка доступа без прав: {}", e.getMessage());
        model.addAttribute("errorCode", "403");
        model.addAttribute("errorMessage", "У вас недостаточно прав для выполнения этого действия.");
        return "error";
    }
}