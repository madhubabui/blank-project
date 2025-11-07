# College PPP Operations - Java Web Application

A Spring Boot web application to maintain PPP (Public-Private Partnership) operations of the college: partners, projects, contracts, payments, and KPI tracking. Data is persisted as JSON on disk for simplicity.

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
   mvn spring-boot:run
   ```
   or
   ```
   java -jar target/ppp-ops-0.1.0.jar
   ```

3. Open the app:
   - http://localhost:8080

Data is persisted to `ppp_data.json` in the project root.

## Features
- Dashboard with counts
- Partners: list and add
- Projects: list and add
- Project detail: link partners, add contracts, payments, and KPIs
- JSON persistence (no external DB needed)

## Next Extensions
- Authentication and roles (Admin/Coordinator/Viewer)
- Reporting: financial summaries, KPI charts, CSV/Excel exports
- Switch to a database (PostgreSQL/MySQL) if needed