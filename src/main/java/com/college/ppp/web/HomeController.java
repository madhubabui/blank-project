package com.college.ppp.web;

import com.college.ppp.Store;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final Store store;

    public HomeController(Store store) {
        this.store = store;
    }

    @GetMapping("/")
    public String index(Model model, Authentication auth) {
        model.addAttribute("partnerCount", store.getPartners().size());
        model.addAttribute("projectCount", store.getProjects().size());
        model.addAttribute("username", auth != null ? auth.getName() : "Guest");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}