package com.college.ppp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class Store {
    private final Path dataPath = Path.of("ppp_data.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Map<Integer, Partner> partners = new LinkedHashMap<>();
    private final Map<Integer, Project> projects = new LinkedHashMap<>();
    private int nextPartnerId = 1;
    private int nextProjectId = 1;
    private int nextContractId = 1;
    private int nextPaymentId = 1;
    private int nextKpiId = 1;

    @PostConstruct
    public void init() {
        load();
    }

    public void load() {
        if (!Files.exists(dataPath)) return;
        try (Reader reader = new FileReader(dataPath.toFile())) {
            Type type = new TypeToken<PersistedData>() {}.getType();
            PersistedData pd = gson.fromJson(reader, type);
            if (pd != null) {
                partners.clear();
                projects.clear();
                for (Partner p : pd.partners) {
                    partners.put(p.id(), p);
                    nextPartnerId = Math.max(nextPartnerId, p.id() + 1);
                }
                for (Project pr : pd.projects) {
                    projects.put(pr.id(), pr);
                    nextProjectId = Math.max(nextProjectId, pr.id() + 1);
                    for (Contract c : pr.contracts()) {
                        nextContractId = Math.max(nextContractId, c.id() + 1);
                    }
                    for (Payment pay : pr.payments()) {
                        nextPaymentId = Math.max(nextPaymentId, pay.id() + 1);
                    }
                    for (KPI k : pr.kpis()) {
                        nextKpiId = Math.max(nextKpiId, k.id() + 1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }

    public void save() {
        PersistedData pd = new PersistedData(new ArrayList<>(partners.values()), new ArrayList<>(projects.values()));
        try (FileWriter writer = new FileWriter(dataPath.toFile())) {
            gson.toJson(pd, writer);
        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    public Partner createPartner(String name, String email) {
        Partner p = new Partner(nextPartnerId++, name, email);
        partners.put(p.id(), p);
        save();
        return p;
    }

    public List<Partner> getPartners() {
        return new ArrayList<>(partners.values());
    }

    public Partner getPartnerById(int id) {
        return partners.get(id);
    }

    public Project createProject(String title, String description) {
        Project p = new Project(nextProjectId++, title, description, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        projects.put(p.id(), p);
        save();
        return p;
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects.values());
    }

    public Project getProjectById(int id) {
        return projects.get(id);
    }

    public boolean addPartnerToProject(int partnerId, int projectId) {
        Partner partner = partners.get(partnerId);
        Project project = projects.get(projectId);
        if (partner == null || project == null) return false;
        if (!project.partnerIds().contains(partnerId)) {
            project.partnerIds().add(partnerId);
        }
        save();
        return true;
    }

    public Contract createContract(int projectId, String terms, double value) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        Contract c = new Contract(nextContractId++, terms, value);
        project.contracts().add(c);
        save();
        return c;
    }

    public Payment addPayment(int projectId, double amount, String note) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        Payment p = new Payment(nextPaymentId++, amount, note, new Date().getTime());
        project.payments().add(p);
        save();
        return p;
    }

    public KPI addKPI(int projectId, String name, String value) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        KPI k = new KPI(nextKpiId++, name, value, new Date().getTime());
        project.kpis().add(k);
        save();
        return k;
    }

    static class PersistedData {
        List<Partner> partners;
        List<Project> projects;

        PersistedData(List<Partner> partners, List<Project> projects) {
            this.partners = partners;
            this.projects = projects;
        }
    }
}