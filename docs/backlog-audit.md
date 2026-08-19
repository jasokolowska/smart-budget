# GitHub Issue Backlog Alignment

Status: **owner-approved issue reorganization**. Source: 37 previously open issues reviewed against the accepted product interview and `docs/roadmap.md`.

## Decision rules

- Preserve useful existing issues by updating titles, scope, and acceptance criteria.
- Prefix active titles with the roadmap milestone, e.g. `[M1]`, `[M3]`, or `[M9][OPTIONAL]`.
- Close only issues tied exclusively to rejected independent Gateway/Notification services or obsolete Gateway-specific AI routing.
- Retain later CSV, Angular, AI, recurring, cloud, and observability work in the appropriate milestone.
- Keep M1 restricted to the accepted owner-isolated budgeting backend.
- Add narrowly scoped issues for missing architecture checks, product rules, M2 capabilities, Terraform/AWS decisions, and AI categorization.

## Existing issue disposition

| Issue | Milestone | Action | Approved direction |
| --- | --- | --- | --- |
| [#46](https://github.com/jasokolowska/smart-budget/issues/46) | M1 | UPDATE | Establish the Kotlin-first modular-monolith foundation |
| [#49](https://github.com/jasokolowska/smart-budget/issues/49) | M1 | UPDATE | Run the backend, PostgreSQL, and Keycloak with Docker Compose |
| [#51](https://github.com/jasokolowska/smart-budget/issues/51) | M1 | UPDATE | Configure Keycloak realm, OIDC client, and two demo owners |
| [#52](https://github.com/jasokolowska/smart-budget/issues/52) | obsolete | CLOSE | An independent API Gateway and routing between separately deployed services contradict ADR 0001. JWT validation is handled directly by the modular-monolith backend in issue #55. |
| [#53](https://github.com/jasokolowska/smart-budget/issues/53) | obsolete | CLOSE | There are no separately deployed transaction/budget/notification services to route to. The selected architecture exposes one Spring Boot application API. |
| [#54](https://github.com/jasokolowska/smart-budget/issues/54) | obsolete | CLOSE | The product has no standalone Gateway. AI-assisted budgeting is preserved separately as issue #62 (M8), with provider/cache decisions deferred until justified. |
| [#55](https://github.com/jasokolowska/smart-budget/issues/55) | M1 | UPDATE | Validate OIDC JWTs inside the Spring Boot resource server |
| [#56](https://github.com/jasokolowska/smart-budget/issues/56) | M1 | UPDATE | Implement the owner-scoped expenses application module |
| [#57](https://github.com/jasokolowska/smart-budget/issues/57) | M1 | UPDATE | Add owner-scoped category and expense Flyway migrations |
| [#58](https://github.com/jasokolowska/smart-budget/issues/58) | M5 | UPDATE | Import owner-scoped expenses from CSV with row-level reporting |
| [#59](https://github.com/jasokolowska/smart-budget/issues/59) | M1 | UPDATE | List monthly expenses with category filtering and bounded pagination |
| [#60](https://github.com/jasokolowska/smart-budget/issues/60) | M9 | UPDATE | Evaluate cash-withdrawal classification and event processing |
| [#61](https://github.com/jasokolowska/smart-budget/issues/61) | M1 | UPDATE | Implement monthly budgets, advisory limits, and summaries |
| [#62](https://github.com/jasokolowska/smart-budget/issues/62) | M8 | UPDATE | Generate explainable AI budget proposals requiring owner approval |
| [#63](https://github.com/jasokolowska/smart-budget/issues/63) | M1 | UPDATE | Calculate monthly summaries synchronously through public module APIs |
| [#64](https://github.com/jasokolowska/smart-budget/issues/64) | M6 | UPDATE | Model recurring, planned, and periodic financial obligations |
| [#65](https://github.com/jasokolowska/smart-budget/issues/65) | M1 | UPDATE | Add Flyway schema for owner-scoped monthly budgets and limits |
| [#66](https://github.com/jasokolowska/smart-budget/issues/66) | obsolete | CLOSE | An independently deployed Notification Service contradicts the modular-monolith-first architecture. Optional owner-controlled notifications remain represented by issues #67, #68, and #77. |
| [#67](https://github.com/jasokolowska/smart-budget/issues/67) | M9 | UPDATE | Evaluate owner-controlled notification preferences |
| [#68](https://github.com/jasokolowska/smart-budget/issues/68) | M9 | UPDATE | Evaluate web-push notifications and subscription security |
| [#69](https://github.com/jasokolowska/smart-budget/issues/69) | M4 | UPDATE | Build a focused Angular frontend for the deployed budgeting API |
| [#70](https://github.com/jasokolowska/smart-budget/issues/70) | M4 | UPDATE | Create the minimal Angular application shell and routing |
| [#71](https://github.com/jasokolowska/smart-budget/issues/71) | M4 | UPDATE | Integrate Angular OIDC authentication with Keycloak |
| [#72](https://github.com/jasokolowska/smart-budget/issues/72) | M5 | UPDATE | Add Angular CSV upload and import-result review |
| [#73](https://github.com/jasokolowska/smart-budget/issues/73) | M4 | UPDATE | Build the Angular expense list with month/category filtering |
| [#74](https://github.com/jasokolowska/smart-budget/issues/74) | M8 | UPDATE | Review and approve explainable AI budget proposals in Angular |
| [#75](https://github.com/jasokolowska/smart-budget/issues/75) | M6 | UPDATE | Manage recurring and periodic obligations in Angular |
| [#76](https://github.com/jasokolowska/smart-budget/issues/76) | M4 | UPDATE | Build a focused monthly budget and expense dashboard |
| [#77](https://github.com/jasokolowska/smart-budget/issues/77) | M9 | UPDATE | Add notification settings only after product validation |
| [#78](https://github.com/jasokolowska/smart-budget/issues/78) | M1 | UPDATE | Run Gradle unit, integration, and architecture tests in GitHub Actions |
| [#79](https://github.com/jasokolowska/smart-budget/issues/79) | M3 | UPDATE | Build and publish a deployable backend container image |
| [#80](https://github.com/jasokolowska/smart-budget/issues/80) | M1 | UPDATE | Verify PostgreSQL integration and cross-owner isolation |
| [#81](https://github.com/jasokolowska/smart-budget/issues/81) | M3 | UPDATE | Add deployment logs, essential metrics, alarms, and cost protection |
| [#82](https://github.com/jasokolowska/smart-budget/issues/82) | M1 | UPDATE | Publish OpenAPI documentation for the single budgeting backend |
| [#83](https://github.com/jasokolowska/smart-budget/issues/83) | M1 | UPDATE | Document and automate the approved budgeting acceptance scenarios |
| [#84](https://github.com/jasokolowska/smart-budget/issues/84) | M9 | UPDATE | Define release/versioning practices for a usable product |
| [#85](https://github.com/jasokolowska/smart-budget/issues/85) | M1 | UPDATE | Implement owner-scoped categories inside the modular monolith |

## New backlog coverage

- M1: Verify Spring Modulith boundaries and permitted module dependencies.
- M1: Implement optional advisory overall monthly spending limits.
- M1: Implement advisory category limits and allocation warnings.
- M1: Record positive PLN expenses with actual-date validation.
- M2: Rename and archive categories without losing expense history.
- M2: Add optional two-level categories and aggregated parent summaries.
- M2: Edit and delete owner-scoped expenses with summary recalculation.
- M2: Add income and manual opening balance as separate concepts.
- M2: Copy category spending limits to another month on explicit request.
- M3: Compare AWS hosting options and record the selected architecture in an ADR.
- M3: Provision the selected AWS infrastructure with Terraform.
- M5: Evaluate S3-backed CSV storage and justified asynchronous processing.
- M6: Model annual and seasonal obligations for future budget planning.
- M7: Suggest expense categories for imported CSV transactions using AI.

## What the roadmap prefixes mean

- **M1:** secure modular-monolith budgeting workflow.
- **M2:** richer category hierarchy, lifecycle, income, opening balance, and reusable monthly planning.
- **M3:** evidence-led AWS deployment, Terraform, operational monitoring, and cost control.
- **M4:** minimal Angular frontend.
- **M5:** CSV ingestion and justified cloud-assisted processing.
- **M6:** recurring, planned, and periodic obligations.
- **M7:** AI-assisted expense categorization.
- **M8:** explainable, owner-approved AI budget proposals.
- **M9:** optional features justified by evidence.

## GitHub Projects and repository milestone limitation

The connected repository integration can update issues and pull requests, but it does not expose direct GitHub Projects v2 board fields, project-item statuses, or creation of GitHub milestone objects. Roadmap prefixes keep each issue sortable and searchable even without project-board field access. Issues already present on a board reflect their updated repository titles and bodies.
