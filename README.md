# College PPP Operations - Java Application

A simple console-based Java application to maintain PPP (Public-Private Partnership) operations of the college: partners, projects, contracts, payments, and KPI tracking.

## Requirements
- Java 17+
- Maven 3.8+

## Setup
1. Build:
   ```
   mvn clean package
   ```
2. Run:
   ```
   java -jar target/ppp-ops-0.1.0.jar
   ```

Data is persisted to `ppp_data.json` in the project root.

## Features
- Add and list partners
- Add and list projects
- Link partners to projects
- Create contracts with terms and value
- Record payments against projects
- Record KPIs for monitoring outcomes
- Save/load data (JSON)

## Notes
This is a console MVP. If you want a desktop GUI or web interface, I can extend it with JavaFX (desktop) or Spring Boot (web/API) based on your preferences.