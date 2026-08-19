# Technology Stack and Decision Status

Status: **approved technical direction**. Distinguish verified existing implementation from future target capabilities.

## Verified implementation baseline

| Technology or structure | Existing evidence | Status |
| --- | --- | --- |
| Java 21 | Kotlin JVM toolchain in the application subproject | Present |
| Kotlin 2.2 | Kotlin JVM and Spring plugins in `app` | Present |
| Spring Boot | Existing Gradle plugin configuration | Present |
| Gradle Kotlin DSL | `build.gradle.kts` and `settings.gradle.kts` | Present |
| Single deployable `app` Gradle subproject | `server/settings.gradle.kts` and the Spring Boot entry point | Present |
| Spring Modulith boundaries | Architecture test discovers and verifies `categories`, `budget`, and `expenses` | Present |
| GitHub Actions Gradle build | Existing workflow | Present |
| Spotless configuration | Existing Gradle configuration | Present |

Complete PostgreSQL/Flyway persistence, Keycloak security, and the full budgeting workflow remain targets until implemented and verified.

## M1 target stack

| Concern | Approved target | Purpose |
| --- | --- | --- |
| Runtime | Java 21 | Existing stable runtime baseline |
| New application code | Kotlin; existing Java may remain temporarily | Kotlin/Spring backend portfolio |
| Framework | Spring Boot | One deployable web application |
| Module architecture | Spring Modulith | Verify `categories`, `budget`, and `expenses` boundaries |
| Build | Gradle Kotlin DSL | Existing repository build; no Maven introduction |
| Database | One PostgreSQL database | Relational owner-scoped product data |
| Migration | Flyway | One reproducible migration mechanism |
| Persistence | Spring Data JPA initially | Clear initial persistence model |
| Cross-module relations | Identifier references only | Preserve ownership boundaries |
| Money | Exact decimal representation such as `BigDecimal`; PLN only | Avoid floating-point financial errors |
| HTTP API | REST and OpenAPI | Demonstrate the first complete flow without a dedicated UI |
| Validation | Jakarta Bean Validation plus domain rules | Reject malformed/future expense inputs |
| Identity provider | Keycloak OIDC with two demo owners | Real local authentication |
| API security | Spring Security OAuth2 Resource Server | Validated JWT and trusted issuer/subject identity |
| Tests | JUnit 5, unit/integration tests, Testcontainers, Modulith verification | Business correctness, security, and architecture |
| Local environment | Docker Compose: application, PostgreSQL, Keycloak | Reproducible demonstration |
| Automation | GitHub Actions | Build and unit/integration/architecture checks |

## Approved later technologies

- **M3:** AWS and Terraform; hosting service selected only after a documented architecture comparison.
- **M3:** basic AWS/application logs, metrics, operational alarms, and AWS budget/cost alert.
- **M4:** Angular + TypeScript; lightweight UI only.
- **M5:** CSV parsing; S3 and asynchronous AWS services only when the ingestion case justifies them.
- **M7-M8:** an LLM/model provider selected when evaluating privacy, capability, operating cost, and integration requirements.
- **M9:** optional OpenTelemetry, Datadog, SQS/EventBridge/Lambda, Kafka, Redis, notifications, or service extraction when supported by an ADR.

No provider, AWS compute service, queue, AI model, or messaging architecture is selected merely because it appeared in historical documentation.

## Architecture boundaries

- `categories` owns category rules and persistence.
- `expenses` owns actual expense records and exposes spending queries.
- `budget` owns month plans, limits, warnings, and composed summaries.
- Allowed dependencies: `expenses -> categories`; `budget -> categories`; `budget -> expenses`.
- Public application interfaces are the only cross-module integration point.
- Repositories and entities remain private to their owning modules.
- Do not add cross-module JPA associations.
- Build subprojects and application modules remain distinct concepts.

## References

- [Spring Boot documentation](https://docs.spring.io/spring-boot/)
- [Kotlin documentation](https://kotlinlang.org/docs/home.html)
- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Spring Modulith testing](https://docs.spring.io/spring-modulith/reference/testing.html)
- [Spring Security OAuth2 Resource Server JWT](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Keycloak documentation](https://www.keycloak.org/documentation)
- [Flyway migration documentation](https://documentation.red-gate.com/fd/migrations-271585107.html)
- [Terraform AWS tutorials](https://developer.hashicorp.com/terraform/tutorials/aws-get-started)
- [AWS Well-Architected Framework](https://docs.aws.amazon.com/wellarchitected/latest/framework/welcome.html)
- [Angular documentation](https://angular.dev/overview)
