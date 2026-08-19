# Transaction Module (Onion Architecture)

A small Spring Boot module implementing transaction-related domain logic using an Onion Architecture approach. Designed to separate domain logic from infrastructure and expose a clean API.

## Tech stack
1. Java 21
2. Spring Boot
3. Gradle
4. SQL (Postgres recommended)

## Architecture / Module mapping

```
module/
├── core/                # Domain entities and interfaces (domain layer)
├── service/             # Domain services, business logic
├── infrastructure/      # Implementation of external concerns (DB, messaging)
│    ├── repository/     # Repository implementations (e.g., Ktorm)
│    ├── entities/       # Entities coupled to infrastructure concerns
├── api/                 # Web controllers or API endpoints
└── config/              # Wiring and configuration
```

## Quickstart (Windows)
1. Requirements:
    1. JDK 21+
    2. `gradlew.bat` (included)
    3. IntelliJ IDEA (recommended)
2. Build:
    - PowerShell / CMD:
        - `.\gradlew.bat clean build`
3. Run:
    - From Gradle:
        - `.\gradlew.bat :server:bootRun`
    - From IntelliJ:
        - Open the `server` module and run the Spring Boot application main class.
4. Run tests:
    - `.\gradlew.bat test`

## Configuration
1. Application properties live in `src/main/resources` (e.g., `application.yml` or `application.properties`).
2. Typical environment variables:
    - `SPRING_DATASOURCE_URL`
    - `SPRING_DATASOURCE_USERNAME`
    - `SPRING_DATASOURCE_PASSWORD`
3. Example Postgres (Docker):
    - `docker run --name tx-db -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=app -e POSTGRES_DB=txdb -p 5432:5432 -d postgres:15`

## Testing
1. Unit tests: JUnit + Kotlin test support
2. Integration tests: profile or testcontainers as preferred
3. Run with Gradle: `.\gradlew.bat test`

## License
Specify the project license in the repository root (e.g., `LICENSE`).
