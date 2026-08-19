# Smart Budget Agent Instructions

## Canonical sources

Read these documents before proposing changes:

1. `README.md`
2. `CONTEXT.md`
3. `docs/roadmap.md`
4. `docs/prd.md`
5. `docs/requirements.md`
6. `docs/user_stories.md`
7. `docs/tech-stack.md`
8. `docs/architecture/adr/`
9. `docs/backlog-audit.md` when working with existing GitHub issues

Historical documents in `docs/archive/` explain rejected ideas and must not override approved requirements.

## Repository and architecture

- Canonical repository: `jasokolowska/smart-budget`.
- Target: one deployable Kotlin-first Spring Boot modular monolith on Java 21.
- First-milestone application modules: `categories`, `budget`, and `expenses`.
- Spring Modulith application modules are logical domain boundaries; they are not automatically equivalent to Gradle subprojects.
- Use one PostgreSQL database and Flyway. Each module owns its tables.
- Access another module only through its explicit public application API.
- Do not expose repositories, persistence entities, or implementation classes across module boundaries.
- Store cross-module references as identifiers; do not introduce cross-module JPA object relationships.
- Initial module collaboration is synchronous. Introduce events, queues, or independently deployed services only for a documented need.
- Kotlin is the language for new domain modules. Existing Java may remain during migration.

## Approved product rules

- M1: owner-isolated categories, optional overall monthly limit, monthly category limits, positive PLN expenses, paginated expense list, and overall/category monthly summaries.
- Owner identity comes from a validated Keycloak/OIDC access token. Never trust request-body `userId`.
- Category names are trimmed and unique case-insensitively per owner.
- A budget month is created by setting its overall limit or first category limit.
- One owner/category/month has at most one category limit; setting it again updates the same limit.
- Overall and category limits are advisory; excessive allocations and actual overspending produce visible warnings or negative remaining values, never rejected expenses.
- Expense date is the actual date incurred and cannot be in the future.
- Expense category is mandatory, owner-owned, and need not have a limit.
- Monthly summaries include limited categories without expenses and expenses in categories without limits.
- M1 has no subcategories, category rename/archive, expense edit/delete, income, opening balance, automatic limit copying, future expenses, CSV, Angular, AWS, AI, or recurring payments.
- Subsequent milestones and dependency order are defined in `docs/roadmap.md`.

## Security and quality

- M1 requires real OIDC authentication, two seeded demo owners, OpenAPI, local Docker Compose, and cross-owner integration tests.
- Verify architecture with Spring Modulith and run unit, integration, and architecture tests in GitHub Actions.
- Do not commit `.env`, access tokens, passwords, or other secrets. Commit only sanitized examples.
- Preserve owner isolation in reads, writes, filtering, pagination, summaries, imports, and AI proposals.

## Delivery discipline

- Distinguish current implementation from target state.
- Keep one issue and pull request focused on one reviewable outcome.
- Update requirements, user stories, roadmap, and C4/ADR documentation when an approved decision changes.
- Do not silently resurrect Gateway, multiple application databases, RabbitMQ, mandatory asynchronous messaging, or microservices.
- Do not rewrite or merge PR #89 without a separate explicit decision.
- Follow the latest direct instruction from the repository owner when working on this branch or backlog.
