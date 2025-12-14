package org.example.Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                log.warn("Ошибка 404: Страница не найдена. URI: {}", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
                model.addAttribute("errorCode", "404");
                model.addAttribute("errorMessage", "Страница, которую вы ищете, не существует.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.error("Ошибка 500: Внутренняя ошибка сервера");
                model.addAttribute("errorCode", "500");
                model.addAttribute("errorMessage", "Произошла внутренняя ошибка сервера. Мы уже работаем над этим.");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                log.warn("Ошибка 403: Доступ запрещен");
                model.addAttribute("errorCode", "403");
                model.addAttribute("errorMessage", "У вас нет прав для просмотра этой страницы.");
            } else {
                model.addAttribute("errorCode", String.valueOf(statusCode));
                model.addAttribute("errorMessage", "Произошла неизвестная ошибка.");
            }
        }
        return "error";
    }
}