package com.college.ppp.web;

import com.college.ppp.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

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
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
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
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public String linkPartner(@PathVariable int id, @RequestParam int partnerId) {
        store.addPartnerToProject(partnerId, id);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/contracts")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public String addContract(@PathVariable int id, @RequestParam String terms, @RequestParam double value) {
        store.createContract(id, terms, value);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/payments")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public String addPayment(@PathVariable int id, @RequestParam double amount, @RequestParam String note) {
        store.addPayment(id, amount, note);
        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/kpis")
    @PreAuthorize("hasAnyRole('ADMIN','COORDINATOR')")
    public String addKpi(@PathVariable int id, @RequestParam String name, @RequestParam String value) {
        store.addKPI(id, name, value);
        return "redirect:/projects/" + id;
    }

    @GetMapping("/{id}/payments.csv")
    public ResponseEntity<byte[]> exportPaymentsCsv(@PathVariable int id) {
        Project project = store.getProjectById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("PaymentID,Amount,Note,Timestamp\n");
        for (Payment p : project.payments()) {
            sb.append(p.id()).append(",").append(p.amount()).append(",")
              .append("\"").append(p.note() == null ? "" : p.note().replace("\"", "\"\"")).append("\"").append(",")
              .append(p.timestamp()).append("\n");
        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project-" + id + "-payments.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(bytes);
    }
}