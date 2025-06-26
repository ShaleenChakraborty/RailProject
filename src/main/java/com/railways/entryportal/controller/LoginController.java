package com.railways.entryportal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html will be rendered
    }

    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        if (username.equals("admin") && password.equals("admin123")) {
            session.setAttribute("username", "admin");
            session.setAttribute("role", "ADMIN");
            return "redirect:/admin";
        } else if (username.equals("user") && password.equals("user123")) {
            session.setAttribute("username", "user");
            session.setAttribute("role", "USER");
            return "redirect:/user";
        } else {
            return "redirect:/login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/admin")
    public String showAdminDashboard(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("ADMIN")) {
            return "redirect:/login";
        }
        return "admin";
    }
    @GetMapping("/user")
    public String showUserDashboard(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("USER")) {
            return "redirect:/login";
        }
        return "user";
    }

}
