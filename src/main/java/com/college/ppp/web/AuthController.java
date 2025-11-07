package com.college.ppp.web;

import com.college.ppp.user.Role;
import com.college.ppp.user.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService users;

    public AuthController(UserService users) {
        this.users = users;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") RegisterForm form, BindingResult binding, Model model) {
        if (form.username == null || form.username.isBlank() || form.password == null || form.password.length() < 4) {
            model.addAttribute("error", "Provide a username and a password with at least 4 characters.");
            return "register";
        }
        if (users.findByUsername(form.username) != null) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }
        Role role = Role.valueOf(form.role);
        users.register(form.username, form.password, role);
        return "redirect:/login";
    }

    public static class RegisterForm {
        @NotBlank
        public String username;
        @NotBlank @Size(min = 4)
        public String password;
        public String role = "VIEWER";
    }
}