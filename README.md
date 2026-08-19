# Smart Budget

Smart Budget is a personal-finance backend and portfolio project focused on deliberate architecture, Kotlin/Spring engineering, and cloud-readiness.

> **Project status:** documentation realignment. The target architecture has been selected, but several target technologies are not implemented on `main` yet.

## Current implementation versus target

| Area | Currently present on `main` | Target direction |
| --- | --- | --- |
| Application | Early-stage Java/Spring Boot application | Kotlin-first Spring Boot application |
| Build | Gradle Kotlin DSL, Java 21, `app` and `transaction` subprojects | One deployable application with explicit domain-module boundaries |
| Architecture | Partially implemented structure and inconsistent legacy documentation | Modular monolith supported by Spring Modulith |
| Persistence | Not established consistently on `main` | PostgreSQL with Flyway migrations |
| Authentication | Not implemented | Keycloak OIDC with Smart Budget as an OAuth2 resource server |
| Quality | Gradle build and GitHub Actions workflow | Domain tests, integration tests, and module-boundary verification |

Do not interpret an architectural target as functionality that already exists.

## First usable product slice

The first milestone is one end-to-end workflow:

1. Create a personal expense category.
2. Set a monthly spending limit for that category.
3. Record an expense assigned to the category.
4. Retrieve how much was spent and how much remains for the selected month.

CSV import, AI-generated budgets, notifications, recurring payments, a dedicated frontend, cloud deployment, and microservices are intentionally outside that first slice.

## Documentation

- [Product requirements](docs/prd.md): product purpose, users, scope, and success criteria.
- [Functional and non-functional requirements](docs/requirements.md): implementation-oriented requirements and acceptance boundaries.
- [User stories](docs/user_stories.md): small, verifiable stories for the first product slice.
- [Technology decisions](docs/tech-stack.md): current implementation, target stack, and explicitly deferred technologies.
- [Domain context](CONTEXT.md): shared vocabulary, ownership rules, and unresolved decisions.
- [Architecture decision: modular monolith first](docs/architecture/adr/0001-modular-monolith-first.md).
- [Architecture model](docs/architecture/workspace.dsl): target C4 context, container, and module views.
- [Documentation review guide](docs/documentation-review.md): owner interview and Cursor review workflow.
- [Existing issue audit](docs/backlog-audit.md): review recommendations for the legacy backlog.
- [Historical microservice documents](docs/archive/2025-microservices/README.md): superseded material preserved for traceability.

## Existing local build

Run commands from `server/` using the Gradle wrapper already committed to the repository:

```bash
./gradlew build
./gradlew test
./gradlew :app:bootRun
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

These commands describe the existing Gradle project. They do not imply that the target Kotlin, Spring Modulith, PostgreSQL, or authentication setup has already been delivered.

## Working agreements

- Keep one repository and one current set of product and architecture documents.
- Implement features as small, reviewable vertical slices.
- Keep ownership and authorization explicit; never accept a client-provided user identifier as proof of identity.
- Prefer clear synchronous module contracts until asynchronous communication solves a demonstrated problem.
- Record consequential architectural decisions as ADRs.
- Treat historical documentation and older issues as inputs to review, not active specifications.

## License

This project is distributed under the [MIT License](LICENSE).
