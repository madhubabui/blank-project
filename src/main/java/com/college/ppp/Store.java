package com.college.ppp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Store {
    private final Path dataPath = Path.of("ppp_data.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Map&lt;Integer, Partner&gt; partners = new LinkedHashMap&lt;&gt;();
    private final Map&lt;Integer, Project&gt; projects = new LinkedHashMap&lt;&gt;();
    private int nextPartnerId = 1;
    private int nextProjectId = 1;
    private int nextContractId = 1;
    private int nextPaymentId = 1;
    private int nextKpiId = 1;

    public void load() {
        if (!Files.exists(dataPath)) return;
        try (Reader reader = new FileReader(dataPath.toFile())) {
            Type type = new TypeToken&lt;PersistedData&gt;() {}.getType();
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
        PersistedData pd = new PersistedData(new ArrayList&lt;&gt;(partners.values()), new ArrayList&lt;&gt;(projects.values()));
        try (FileWriter writer = new FileWriter(dataPath.toFile())) {
            gson.toJson(pd, writer);
        } catch (Exception e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    public Partner createPartner(String name, String email) {
        Partner p = new Partner(nextPartnerId++, name, email);
        partners.put(p.id(), p);
        return p;
    }

    public List&lt;Partner&gt; getPartners() {
        return new ArrayList&lt;&gt;(partners.values());
    }

    public Project createProject(String title, String description) {
        Project p = new Project(nextProjectId++, title, description, new ArrayList&lt;&gt;(), new ArrayList&lt;&gt;(), new ArrayList&lt;&gt;(), new ArrayList&lt;&gt;());
        projects.put(p.id(), p);
        return p;
    }

    public List&lt;Project&gt; getProjects() {
        return new ArrayList&lt;&gt;(projects.values());
    }

    public boolean addPartnerToProject(int partnerId, int projectId) {
        Partner partner = partners.get(partnerId);
        Project project = projects.get(projectId);
        if (partner == null || project == null) return false;
        if (!project.partnerIds().contains(partnerId)) {
            project.partnerIds().add(partnerId);
        }
        return true;
    }

    public Contract createContract(int projectId, String terms, double value) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        Contract c = new Contract(nextContractId++, terms, value);
        project.contracts().add(c);
        return c;
    }

    public Payment addPayment(int projectId, double amount, String note) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        Payment p = new Payment(nextPaymentId++, amount, note, new Date().getTime());
        project.payments().add(p);
        return p;
    }

    public KPI addKPI(int projectId, String name, String value) {
        Project project = projects.get(projectId);
        if (project == null) return null;
        KPI k = new KPI(nextKpiId++, name, value, new Date().getTime());
        project.kpis().add(k);
        return k;
    }

    static class PersistedData {
        List&lt;Partner&gt; partners;
        List&lt;Project&gt; projects;

        PersistedData(List&lt;Partner&gt; partners, List&lt;Project&gt; projects) {
            this.partners = partners;
            this.projects = projects;
        }
    }
}