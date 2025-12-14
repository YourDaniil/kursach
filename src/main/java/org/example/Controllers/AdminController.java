package org.example.Controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.Services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public String userList(Model model) {
        log.debug("Администратор просматривает список пользователей");
        model.addAttribute("users", adminService.findAllUsers());
        return "admin/userList";
    }

    @PostMapping("/users/ban")
    public String toggleBan(@RequestParam("userId") String userId) {
        log.info("Администратор изменил статус бана для пользователя ID: {}", userId);
        adminService.toggleBan(userId);
        return "redirect:/admin/users";
    }
}