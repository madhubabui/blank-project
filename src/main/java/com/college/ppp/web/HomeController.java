package com.college.ppp.web;

import com.college.ppp.Store;
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
    public String index(Model model) {
        model.addAttribute("partnerCount", store.getPartners().size());
        model.addAttribute("projectCount", store.getProjects().size());
        return "index";
    }
}