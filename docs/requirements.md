# Functional and Non-functional Requirements

Status: **approved product baseline**.

## Scope conventions

- `M1` is the first secure, demonstrable backend vertical slice.
- `M2`-`M9` identify explicitly later roadmap stages.
- `MUST` indicates an acceptance requirement for the stated milestone.
- `WARNING` is visible informational feedback; it never rejects a valid write.

## M1 functional requirements

### FR-001: Authenticated owner identity and isolation

- Every protected operation MUST derive owner identity from a validated OIDC access token.
- The stable owner key MUST be based on trusted issuer plus subject.
- Request-provided `userId` MUST NOT determine ownership.
- Keycloak MUST provide two preconfigured demonstration users.
- Categories, budgets, limits, expenses, list filters, and summaries MUST be scoped to the authenticated owner.
- Owner A MUST NOT read, change, list, or reference Owner B's resources.
- Authentication failure returns HTTP 401. Authorization handling MUST NOT disclose another owner's data.
- Public self-registration and household sharing are out of scope.

### FR-002: Create and list personal categories

- An owner MUST be able to create and list expense categories.
- Names MUST be trimmed and unique case-insensitively for that owner.
- Different owners MAY use the same category name.
- Category rename, archival, and hierarchy are M2 capabilities.

### FR-003: Create a monthly budget and set an overall limit

- A budget month MUST be addressable as a year and month, e.g. `2026-08`.
- The budget MUST be created when the owner sets its first category limit or optional overall limit.
- Past, current, and future months MUST be supported for budget planning and reading.
- The overall limit MUST be optional and advisory.
- Setting or lowering it MUST NOT change or invalidate category limits.
- A plan exceeding the overall amount MUST expose a visible non-blocking warning.

### FR-004: Set an advisory category limit

- An owner MAY configure a positive exact-decimal PLN limit for an owner-owned category and month.
- At most one limit MUST exist per owner, category, and month.
- Reapplying a limit MUST update the existing amount.
- Category limits MUST be allowed without an overall limit.
- The sum of category limits MAY exceed the overall limit.
- The API MUST expose allocated amount, unallocated amount where an overall limit exists, and over-allocation warnings.
- Limits MUST NOT depend on recorded income.

### FR-005: Record an actual expense

- Amount MUST be positive and represented with exact PLN decimal precision.
- Actual incurred date and owner-owned category MUST be provided.
- Description MAY be omitted.
- The date incurred MUST determine the expense's calendar month.
- Future incurred dates MUST be rejected.
- A category limit MUST NOT be required.
- Overall or category overspending MUST NOT block recording.
- Expense edit and delete are M2 capabilities.

### FR-006: List monthly expenses

- An owner MUST be able to list expenses for a selected calendar month.
- The list MUST optionally filter by one owner-owned category.
- Each result MUST include incurred date, category, amount, and optional description.
- Default page size MUST be 20.
- Maximum page size MUST be 100.
- Default order MUST be most recent incurred date first.
- Records from another owner MUST NOT appear in results or influence pagination.

### FR-007: Retrieve a monthly budget summary

- A summary MUST provide total actual owner expenses for the selected month.
- Overall spending MUST include categories without limits.
- The summary MUST provide per-category spent amounts.
- A category with a limit and zero expenses MUST appear with zero spent and the full limit remaining.
- A category with expenses but no limit MUST show the actual expense total and an absent/null limit and remainder.
- When an overall limit exists, show overall remaining = overall limit - all actual expenses.
- When category limits exist, show category remaining = category limit - category spending.
- Negative remaining values MUST be allowed.
- When an overall limit exists, show allocated category limits and unallocated amount.
- Overall over-allocation, overall overspending, and category overspending MUST be exposed as informational warnings.

## M2 functional requirements

### FR-101: Manage category lifecycle and hierarchy

- Allow owner-scoped category rename without changing identity or historical associations.
- Archive categories instead of hard-deleting historical data.
- Support one parent category and optional one-level-deep subcategories.
- Allow expense assignment either to a parent or its child.
- Aggregate parent summaries from directly assigned and child expenses.
- Allow advisory limits at parent and child levels.
- Warn when child allocations exceed a parent limit; do not block or mutate another limit.

### FR-102: Edit and delete expenses

- Allow an owner to correct or delete their own actual expense.
- Recalculate affected category and monthly summaries when amount, category, or month changes.
- Preserve owner isolation and reject future actual-expense dates.

### FR-103: Income and opening balance

- Record owner-scoped income independently of expense records.
- Support a manually entered monthly opening balance.
- Opening balance MUST NOT be classified as income.
- Available funds = opening balance + monthly income.
- Monthly spending limits remain optional, owner-defined, and independent from income.
- Unused funds MUST NOT roll into another month automatically.

### FR-104: Reuse monthly category limits

- Allow an explicit owner action to copy existing limits to another budget month.
- Do not silently copy or change limits.
- Document behavior for archived categories and existing destination limits before implementation.

## Later capabilities

- `M3`: AWS hosting option ADR, Terraform infrastructure, deployment automation, basic logs/metrics/alerts, and AWS cost protection.
- `M4`: Angular authentication, category/budget/expense views, and deployed API integration.
- `M5`: owner-scoped CSV import, validation, result reporting, and S3 integration when justified.
- `M6`: recurring, planned, and periodic obligations with amount, frequency, and due date.
- `M7`: AI-assisted imported-expense classification.
- `M8`: explainable AI budget proposals based on history, income, opening balance, and recurring/periodic obligations; owner approval is mandatory.
- `M9`: notifications, deeper observability, asynchronous processing, or optional service extraction only after documented need.

## M1 non-functional requirements

### NFR-001: Architecture

- One deployable Kotlin-first Spring Boot application and one PostgreSQL database.
- Logical Spring Modulith modules: `categories`, `budget`, and `expenses`.
- Each module MUST own its tables and persistence implementation.
- Inter-module access MUST use explicit public interfaces.
- Cross-module references MUST use identifiers, not shared JPA entity relationships.
- Synchronous calls are the default; there is no mandatory broker or API Gateway.

### NFR-002: Persistence and money

- PostgreSQL is the initial production-like relational database.
- Flyway is the schema migration tool.
- Monetary amounts MUST be exact decimals and restricted to PLN for M1.
- Queries and indexes MUST respect owner/month/category boundaries.

### NFR-003: Security

- Spring Security OAuth2 Resource Server MUST validate access tokens.
- Secrets MUST NOT be committed.
- Repository examples MUST use sanitized `.env.example` values.
- A reproducible local environment MUST contain the application, PostgreSQL, Keycloak, and two preconfigured demo users.

### NFR-004: Quality and automated verification

- Unit tests MUST cover business calculations and warning behavior.
- Integration tests MUST cover persistence and cross-owner isolation.
- Spring Modulith tests MUST verify allowed dependencies and absence of cycles.
- Testcontainers SHOULD provide reproducible PostgreSQL integration where appropriate.
- GitHub Actions MUST run build, unit, integration, and architecture tests for pull requests.

### NFR-005: API and portfolio quality

- M1 MUST be demonstrable through a documented OpenAPI.
- Validation and errors MUST be understandable without exposing private data.
- Documentation MUST distinguish verified implementation from target architecture.
- Tasks and pull requests SHOULD fit focused, reviewable work sessions.
- Angular and AWS MUST NOT be described as complete before their milestones are delivered.

## Official references

- [Spring Modulith verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Spring Security JWT Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Testcontainers PostgreSQL](https://java.testcontainers.org/modules/databases/postgres/)
- [Flyway migrations](https://documentation.red-gate.com/fd/migrations-271585107.html)
