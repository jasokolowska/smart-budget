# Functional and Non-functional Requirements

Status: **draft for owner review**. Only requirements marked `MVP` define the first implementation milestone.

## Scope conventions

- `MVP`: required for the category -> limit -> expense -> monthly summary workflow.
- `FUTURE`: a documented possibility that must not be implemented as part of the first slice.
- `OPEN`: requires an explicit product-owner decision before implementation.

## Functional requirements

### FR-001: Owner identity and isolation — MVP

The application shall associate every category, monthly limit, and expense with the authenticated owner. All reads and writes shall be restricted to that owner.

Acceptance boundaries:

- Ownership is derived from a trusted authentication/security context.
- A client-provided `userId` must not establish ownership.
- Accessing another owner's records must not expose or modify those records.
- The exact authentication mechanism is an implementation decision awaiting confirmation.

### FR-002: Create a category — MVP

An owner shall be able to create a named expense category and retrieve their categories.

Acceptance boundaries:

- A category belongs to exactly one owner.
- Invalid or blank names are rejected.
- Category-name uniqueness, renaming, and deletion policies remain `OPEN`.

### FR-003: Configure a monthly category limit — MVP

An owner shall be able to define a spending limit for one of their categories and a selected budget month.

Acceptance boundaries:

- The category must belong to the same owner.
- The limit must be represented as an exact decimal monetary amount.
- The amount must be positive.
- Whether a budget month is explicit or created implicitly remains `OPEN`.
- Duplicate-limit and update policies remain `OPEN`.

### FR-004: Record an expense — MVP

An owner shall be able to record an expense with an amount, date, description, and owner-owned category.

Acceptance boundaries:

- Amount calculations must not use floating-point types.
- The referenced category must belong to the authenticated owner.
- Missing or invalid required data is rejected.
- Amount-sign conventions, edit/delete support, and behavior without a category limit remain `OPEN`.

### FR-005: Retrieve a monthly category summary — MVP

An owner shall be able to retrieve a category summary for a selected budget month.

The summary includes:

- Category identity.
- Budget month.
- Configured monthly limit.
- Sum of matching expenses.
- Remaining amount calculated as `limit - spent`.

Acceptance boundaries:

- Only expenses from the requested owner, category, and budget month are included.
- The month-boundary timezone and overspending representation remain `OPEN`.
- The summary may be calculated synchronously; a broker or asynchronous projection is not required.

## Non-functional requirements

### NFR-001: Architecture — MVP

The target architecture is one deployable modular monolith with explicit domain boundaries. Spring Modulith will be introduced in a later implementation task and should verify module dependencies once configured.

### NFR-002: Persistence — MVP target

The intended persistent store is one PostgreSQL database managed through Flyway migrations. Logical table ownership should remain visible even though modules share a physical database.

### NFR-003: Testability — MVP

The implementation shall include automated tests for domain rules, the first end-to-end workflow, and cross-owner isolation. PostgreSQL integration tests should use Testcontainers once persistence is implemented.

### NFR-004: API contracts — MVP

The API shall expose consistent validation errors and document the first product flow. The exact endpoint shape and OpenAPI publication details will be finalized during implementation.

### NFR-005: Maintainability — MVP

New implementation work should be divided into focused, reviewable changes suitable for approximately one-hour work sessions.

### NFR-006: Portfolio honesty — MVP

README, diagrams, and product documentation shall label current implementation, chosen target architecture, and deferred capabilities separately.

## Deferred capabilities

CSV import, AI proposals, recurring expenses, notifications, a dedicated frontend, cloud deployment, asynchronous messaging, and microservice extraction are `FUTURE`. They do not justify extra infrastructure in the first milestone.

## References

- [`../CONTEXT.md`](../CONTEXT.md): domain terms and unresolved decisions.
- [`prd.md`](prd.md): product context and milestone rationale.
- [`user_stories.md`](user_stories.md): testable user-facing scenarios.
