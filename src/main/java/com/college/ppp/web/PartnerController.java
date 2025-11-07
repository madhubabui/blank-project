package com.college.ppp.web;

import com.college.ppp.Partner;
import com.college.ppp.Store;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/partners")
public class PartnerController {
    private final Store store;

    public PartnerController(Store store) {
        this.store = store;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("partners", store.getPartners());
        return "partners";
    }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam String email) {
        store.createPartner(name, email);
        return "redirect:/partners";
    }
}