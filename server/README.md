Server (backend)
=================

Overview
--------
The Smart Budget backend is a modular application written in Java 21, built with Spring Boot and Spring Modulith. The project separates domain concerns (budget, categories, transactions, notifications) into modules to improve maintainability and testability.

Requirements
------------
- Java 21 (the project uses the Gradle toolchain)
- Gradle Wrapper (included: gradlew / gradlew.bat)
- Optional: Docker (for containerization)

Quick start
-----------
1. Change to the server directory and run the application:
   - Windows: `cd server && gradlew.bat bootRun`
   - *nix: `cd server && ./gradlew bootRun`

2. Build artifacts:
   - Windows: `gradlew.bat build`
   - *nix: `./gradlew build`
   The JAR will be available in build/libs.

3. Run tests:
   - `./gradlew test`

Project structure
-----------------
- src/main/java/com/sokolowska — application source code
  - SmartBudgetApplication.java — application entry point
  - packages: budget/, categories/, transactions/, notifications/ — domain modules
- src/main/resources/application.properties — application configuration
- src/test/java — unit and integration tests
- build/ — build artifacts and generated reports (including spring-modulith-docs)

Spring Modulith — documentation generation
-----------------------------------------
The project uses Spring Modulith to generate PlantUML diagrams describing modules and their relationships. When tests are executed, the generated PUML files are placed in:

  `build/spring-modulith-docs/`

You can convert those files to SVG/PNG using PlantUML or have CI produce images from them.

Configuration
-------------
- application.properties — configure port, data sources and profiles.
- When adding new module dependencies, update build.gradle and ensure tests pass.

Running in Docker (example)
---------------------------
A Dockerfile is not included in the repository. Example steps to build an image:
1. Build the JAR: `./gradlew build`
2. Create a Dockerfile based on openjdk:21-jdk, copy the JAR and set ENTRYPOINT to `java -jar /app/app.jar`
3. Build image: `docker build -t smart-budget:latest .`
4. Run: `docker run -p 8080:8080 smart-budget:latest`

Debugging and troubleshooting
-----------------------------
- Connection issues on startup: check application.properties and the configured port (default 8080).
- Build errors: run gradlew clean build and inspect logs in build/reports/tests.
- Application logs: written to stdout by default; configure logback/log4j for custom logging.

Best practices for contributors
------------------------------
- Keep code modular: place domain-specific logic in the appropriate module package.
- Add unit and integration tests for new functionality.
- Document changes in docs/ (PRD, user stories) as features evolve.

Contributing
------------
Follow CONTRIBUTING.md: fork → feature branch → PR. Each PR should include a description, steps to run and test locally, and accompanying tests.

Where to find additional information
-----------------------------------
- General project README: ../README.md
- Product documents and requirements: ../docs/
- Generated module diagrams: build/spring-modulith-docs/components.puml

License
-------
The project is released under the MIT License (see LICENSE in the repository root).
