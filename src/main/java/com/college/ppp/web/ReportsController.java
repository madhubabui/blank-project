package com.college.ppp.web;

import com.college.ppp.Project;
import com.college.ppp.Store;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    private final Store store;

    public ReportsController(Store store) {
        this.store = store;
    }

    @GetMapping("/finance")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR','VIEWER')")
    public String finance(Model model) {
        model.addAttribute("projects", store.getProjects());
        double totalPayments = store.getProjects().stream()
                .flatMap(p -> p.payments().stream())
                .mapToDouble(pay -> pay.amount())
                .sum();
        double totalContracts = store.getProjects().stream()
                .flatMap(p -> p.contracts().stream())
                .mapToDouble(c -> c.value())
                .sum();
        model.addAttribute("totalPayments", totalPayments);
        model.addAttribute("totalContracts", totalContracts);
        return "finance";
    }
}