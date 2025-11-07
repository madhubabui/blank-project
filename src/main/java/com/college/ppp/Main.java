package com.college.ppp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    static class App {
        private final Scanner scanner = new Scanner(System.in);
        private final Store store = new Store();

        public void run() {
            store.load();
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("&gt; ");
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -&gt; addPartner();
                    case "2" -&gt; listPartners();
                    case "3" -&gt; addProject();
                    case "4" -&gt; listProjects();
                    case "5" -&gt; linkPartnerToProject();
                    case "6" -&gt; createContract();
                    case "7" -&gt; addPayment();
                    case "8" -&gt; recordKPI();
                    case "9" -&gt; saveData();
                    case "0" -&gt; {
                        saveData();
                        running = false;
                    }
                    default -&gt; System.out.println("Invalid option");
                }
            }
            System.out.println("Goodbye.");
        }

        private void printMenu() {
            System.out.println();
            System.out.println("=== College PPP Operations ===");
            System.out.println("1) Add Partner");
            System.out.println("2) List Partners");
            System.out.println("3) Add Project");
            System.out.println("4) List Projects");
            System.out.println("5) Link Partner to Project");
            System.out.println("6) Create Contract");
            System.out.println("7) Add Payment");
            System.out.println("8) Record KPI");
            System.out.println("9) Save");
            System.out.println("0) Exit");
        }

        private void addPartner() {
            System.out.print("Partner name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Contact email: ");
            String email = scanner.nextLine().trim();
            Partner p = store.createPartner(name, email);
            System.out.println("Created partner: " + p);
        }

        private void listPartners() {
            System.out.println("Partners:");
            for (Partner p : store.getPartners()) {
                System.out.println(" - " + p);
            }
        }

        private void addProject() {
            System.out.print("Project title: ");
            String title = scanner.nextLine().trim();
            System.out.print("Description: ");
            String desc = scanner.nextLine().trim();
            Project pr = store.createProject(title, desc);
            System.out.println("Created project: " + pr);
        }

        private void listProjects() {
            System.out.println("Projects:");
            for (Project p : store.getProjects()) {
                System.out.println(" - " + p);
            }
        }

        private void linkPartnerToProject() {
            listPartners();
            System.out.print("Partner ID: ");
            int pid = readInt();
            listProjects();
            System.out.print("Project ID: ");
            int prid = readInt();
            boolean ok = store.addPartnerToProject(pid, prid);
            System.out.println(ok ? "Linked." : "Failed to link (check IDs).");
        }

        private void createContract() {
            listProjects();
            System.out.print("Project ID: ");
            int prid = readInt();
            System.out.print("Terms: ");
            String terms = scanner.nextLine().trim();
            System.out.print("Value (amount): ");
            double value = readDouble();
            Contract c = store.createContract(prid, terms, value);
            if (c != null) {
                System.out.println("Created contract: " + c);
            } else {
                System.out.println("Invalid project ID.");
            }
        }

        private void addPayment() {
            listProjects();
            System.out.print("Project ID: ");
            int prid = readInt();
            System.out.print("Amount: ");
            double amount = readDouble();
            System.out.print("Note: ");
            String note = scanner.nextLine().trim();
            Payment p = store.addPayment(prid, amount, note);
            if (p != null) {
                System.out.println("Added payment: " + p);
            } else {
                System.out.println("Invalid project ID.");
            }
        }

        private void recordKPI() {
            listProjects();
            System.out.print("Project ID: ");
            int prid = readInt();
            System.out.print("KPI name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Value: ");
            String value = scanner.nextLine().trim();
            KPI k = store.addKPI(prid, name, value);
            if (k != null) {
                System.out.println("Recorded KPI: " + k);
            } else {
                System.out.println("Invalid project ID.");
            }
        }

        private void saveData() {
            store.save();
            System.out.println("Data saved.");
        }

        private int readInt() {
            while (true) {
                try {
                    String s = scanner.nextLine().trim();
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    System.out.print("Enter a valid integer: ");
                }
            }
        }

        private double readDouble() {
            while (true) {
                try {
                    String s = scanner.nextLine().trim();
                    return Double.parseDouble(s);
                } catch (NumberFormatException e) {
                    System.out.print("Enter a valid number: ");
                }
            }
        }
    }
}