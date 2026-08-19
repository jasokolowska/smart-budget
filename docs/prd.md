# Product Requirements Document: Smart Budget

Status: **approved product baseline**. Supersedes microservice-era material stored under `docs/archive/`.

## Product vision

Enable an individual to plan a monthly spending budget, record actual expenses, understand overspending without hiding reality, and progressively automate routine financial work.

The product also demonstrates practical Kotlin/Spring backend engineering, explicit domain boundaries, secure owner isolation, maintainable delivery, AWS infrastructure, and later applied AI.

## Primary user

An authenticated individual managing a personal budget. The first version is not a multi-person household account.

Typical questions:

- How much can I spend this month?
- How much have I assigned to particular categories?
- What did I actually spend, and where?
- Am I over my overall plan or a category plan?
- Which regular or seasonal costs should influence future budgets?

## Milestone M1: secure budgeting vertical slice

An owner can:

1. Authenticate through Keycloak/OIDC and use the documented OpenAPI.
2. Create and list personal expense categories.
3. Plan any past or future month by setting an optional overall monthly limit and/or category limits.
4. Record an actual positive PLN expense with date incurred, mandatory owner-owned category, and optional description.
5. List a selected month's expenses, optionally filter by category, and use pagination.
6. Retrieve one monthly summary with category-level totals, overall totals, remaining values, and advisory planning warnings.
7. Observe strict isolation from a second preconfigured owner.

### Budget and expense behavior

- Budgets are created when the overall limit or first category limit is configured.
- Category limits are unique per owner, category, and month.
- Both overall and category limits are planning aids, not enforcement gates.
- Allocations may exceed an overall limit and expenses may exceed either limit.
- Excess appears as a warning or negative remainder; no write is blocked because of a limit.
- Expense month is derived from actual incurred date; future expense dates are rejected.
- Expenses can be recorded before a category limit exists.
- Summaries include categories with limits but no expenses and categories with expenses but no limit.
- Missing optional limits are represented as absent, never as an artificial zero.
- Only PLN is supported.

### Demonstration scenario

Owner A sets an overall August budget of 5,000 PLN, allocates 3,000 PLN to food and 3,000 PLN to clothing, and receives a non-blocking 1,000 PLN over-allocation warning. A 150 PLN food expense and a 200 PLN transport expense without a category limit are both recorded. The summary reports 350 PLN overall spending, appropriate per-category amounts, and 4,650 PLN remaining against the overall limit. Owner B cannot see or use Owner A's categories, limits, or expenses.

## Explicitly outside M1

- Category rename, archival, and two-level subcategories.
- Expense updates or deletion.
- Income, opening balances, and copying limits into another month.
- Public account registration and household sharing.
- Multi-currency, future-dated actual expenses, planned and recurring expenses.
- Angular or another dedicated frontend.
- AWS deployment, Terraform, CSV import, and AI features.
- API Gateway, an external message broker, mandatory asynchronous communication, and independently deployed microservices.

## Evolution after M1

- **M2:** lifecycle operations, hierarchical categories, income, opening balance, and monthly-planning reuse.
- **M3:** deliberate, Terraform-managed AWS deployment with monitoring and cost protection.
- **M4:** focused Angular interface for the deployed API.
- **M5:** CSV ingestion, validation, import reporting, and justified cloud storage.
- **M6:** recurring, future/planned, and seasonal expenses.
- **M7:** AI-supported categorization of imported transactions.
- **M8:** explainable AI budget proposals using transaction history, income, opening balances, recurring costs, and seasonal obligations. Owners explicitly accept, edit, or reject proposals.
- **M9:** optional event-driven processing, notifications, deeper observability, or a service extraction only when a concrete need is documented.

See [`roadmap.md`](roadmap.md) for dependencies and definition of done.

## Product principles

1. Actual financial history must never be blocked because a plan was exceeded.
2. Planning inconsistencies are visible, actionable, and non-destructive.
3. Owner identity and data isolation are baseline product behavior.
4. Deliver a complete, narrow workflow before adding infrastructure or automation.
5. AI proposes and explains; the owner remains responsible for decisions.
6. Choose architecture according to demonstrated needs, not a technology checklist.
7. Distinguish implemented functionality from intended future functionality.

## M1 success criteria

- Two authenticated demo users can independently complete the product flow through OpenAPI.
- Every operation derives the owner from a validated access token.
- Positive actual expenses are correctly grouped by incurred month and category.
- Overall and category summaries calculate spending, remaining values, and warning states correctly.
- Pagination and category filtering operate only on the authenticated owner's data.
- Spring Modulith verifies module boundaries and the absence of cycles.
- Automated integration tests prove cross-owner isolation.
- Local application, PostgreSQL, and Keycloak can be started reproducibly.
- GitHub Actions runs build, unit, integration, and architecture checks.

## Related documents

- [Domain vocabulary and confirmed decisions](../CONTEXT.md)
- [Functional and non-functional requirements](requirements.md)
- [User stories](user_stories.md)
- [Delivery roadmap](roadmap.md)
- [Modular monolith ADR](architecture/adr/0001-modular-monolith-first.md)
