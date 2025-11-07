package com.college.ppp.web;

import com.college.ppp.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final Store store;

    public ProjectController(Store store) {
        this.store = store;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("projects", store.getProjects());
        return "projects";
    }

    @PostMapping
    public String add(@RequestParam String title, @RequestParam String description) {
        store.createProject(title, description);
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Project project = store.getProjectById(id);
        if (project == null) {
            return "redirect:/projects";
        }
        model.addAttribute("project", project);
        model.addAttribute("partners", store.getPartners());
        return "project_detail";
    }

    @PostMapping("/{id}/link-partner")
    public String linkPartner(@PathVariable int id, @RequestParam int partnerId) {
        store.addPartnerToProject(partnerId, id);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/contracts")
    public String addContract(@PathVariable int id, @RequestParam String terms, @RequestParam double value) {
        store.createContract(id, terms, value);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/payments")
    public String addPayment(@PathVariable int id, @RequestParam double amount, @RequestParam String note) {
        store.addPayment(id, amount, note);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/kpis")
    public String addKpi(@PathVariable int id, @RequestParam String name, @RequestParam String value) {
        store.addKPI(id, name, value);
        return "redirect:/projects/" + id;
    }
}