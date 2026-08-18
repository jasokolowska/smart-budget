# Technology Stack and Decision Status

Status: **draft for owner review**. Technology selections describe either verified current code or an architectural target; the two must not be confused.

## Verified current state on `main`

| Technology or structure | Evidence | Status |
| --- | --- | --- |
| Java 21 | Gradle Java toolchains in `server/app/build.gradle.kts` and `server/transaction/build.gradle.kts` | Implemented |
| Spring Boot 3.5.6 | Gradle plugin declarations in the existing subprojects | Implemented |
| Gradle Kotlin DSL | Existing `build.gradle.kts` and `settings.gradle.kts` files | Implemented |
| `app` and `transaction` Gradle subprojects | `server/settings.gradle.kts` | Implemented |
| GitHub Actions Gradle build | `.github/workflows/ci.yml` | Implemented |
| Spotless configuration | Root `server/build.gradle.kts` | Implemented |

The current `main` does not yet establish a Kotlin-first application, a Spring Modulith dependency, PostgreSQL/Flyway persistence, or the complete product workflow. Older README statements suggesting otherwise were inaccurate.

## Selected target stack for the first product milestone

| Concern | Target | Why |
| --- | --- | --- |
| Runtime | Java 21 | Stable runtime baseline already present in the repository. |
| Primary application language | Kotlin | Matches the project's learning goals and intended backend portfolio. |
| Application framework | Spring Boot | Supports REST endpoints, validation, security, testing, and the existing build. |
| Architecture verification | Spring Modulith | Makes logical domain boundaries explicit and testable within one application. |
| Build | Gradle Kotlin DSL | Already present; do not introduce Maven instructions or `pom.xml`. |
| Database | One PostgreSQL database | Sufficient for a single deployable application and the initial domain model. |
| Schema migrations | Flyway | One selected migration tool; Liquibase is not a parallel option. |
| Persistence | Spring Data JPA initially | Prefer one clear persistence approach before considering jOOQ for a demonstrated reporting need. |
| API | REST plus OpenAPI documentation | Enables an understandable, demonstrable backend-first product flow. |
| Validation | Jakarta Bean Validation | Provides consistent request validation. |
| Security | Spring Security with owner identity derived from a trusted principal | Protects per-owner financial data. |
| Tests | JUnit 5, focused domain tests, integration tests, Testcontainers for PostgreSQL | Verifies business rules, persistence, and owner isolation. |
| Local development | Docker Compose when persistence is introduced | Provides one local PostgreSQL instance without a distributed stack. |
| Continuous integration | Existing GitHub Actions workflow, adjusted only in a separate implementation task | Keeps build/test feedback visible without expanding this documentation branch. |

## Decisions deliberately deferred

- Spring Boot/Kotlin/Spring Modulith version upgrades and compatibility verification.
- Keycloak versus another suitable OIDC provider.
- Angular or any other dedicated frontend framework.
- CSV libraries, AI integrations, or an LLM provider.
- jOOQ, Kotest, Mailpit, Redis, RabbitMQ, Kafka, SQS, and EventBridge.
- Terraform, ECS/Fargate, App Runner, RDS, S3, and production observability.
- Microservice extraction and infrastructure for independently deployed services.

## Architecture boundaries

The target is **one Spring Boot deployment and one PostgreSQL database**. Logical application modules may include `categories`, `budgeting`, `transactions`, and `reporting`, but the final boundaries require product-owner review.

The existing Gradle subproject layout is a physical build structure. Spring Modulith application modules are logical domain boundaries. The project must not assume these concepts are automatically identical.

## Official references

- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith module verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Spring Modulith integration testing](https://docs.spring.io/spring-modulith/reference/testing.html)
- [Spring Boot system requirements](https://docs.spring.io/spring-boot/system-requirements.html)
- [Kotlin releases](https://kotlinlang.org/docs/releases.html)
