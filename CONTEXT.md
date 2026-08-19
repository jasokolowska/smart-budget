# Smart Budget Domain Context

Status: **product-owner decisions approved**. This document is the shared domain vocabulary and decision record for implementation agents.

## Purpose and constraints

Smart Budget is a useful personal-budgeting product, a public cloud-native backend portfolio, and a practical learning project for Kotlin, Spring, modular design, testing, AWS, Terraform, and observability.

- One canonical repository and one deployable modular monolith.
- Approximately one focused hour per development session.
- Java 21 runtime; new domain code is Kotlin-first.
- One PostgreSQL database, Flyway migrations, and module-owned tables.
- Three initial Spring Modulith application modules: `categories`, `budget`, and `expenses`.
- First user interface: documented OpenAPI. Angular is introduced only after AWS deployment.
- First currency: PLN only, represented with exact decimal amounts.
- Two preconfigured demo owners authenticated by Keycloak through OIDC.
- No public registration, shared household, guest mode, or cross-user access in M1.

## Shared vocabulary

| Term | Approved meaning |
| --- | --- |
| Owner | Authenticated person identified by the trusted OIDC issuer and stable subject claim. |
| Category | Owner-specific expense classification; names are trimmed and unique case-insensitively per owner. |
| Subcategory | Optional second-level category introduced in M2; the hierarchy has exactly two levels. |
| Budget month | Calendar month identified explicitly by year and month, e.g. `2026-08`. |
| Overall monthly limit | Optional advisory amount planned for all spending in one owner/month. |
| Category limit | Optional advisory positive planned amount for one owner/category/month; setting it again replaces the previous amount. |
| Allocated amount | Sum of category-level limits for the selected budget month. |
| Unallocated amount | Overall monthly limit minus allocated category limits; can be negative. |
| Expense | Positive PLN spending entry with actual incurred date, mandatory owner-owned category, and optional description. |
| Spent amount | Sum of actual expenses for the selected owner and month, optionally restricted to a category. |
| Remaining amount | Applicable limit minus actual expenses; can be negative. |
| Planning warning | Non-blocking information about excessive category allocation, category overspending, or overall overspending. |
| Income | Positive incoming money recorded only from M2 onward. |
| Opening balance | Manually entered funds carried into a month; not an income; introduced in M2. |
| Recurring expense | Repeating obligation with amount, category, frequency, and next due date; introduced in M6. |
| Periodic expense | Future non-monthly obligation such as annual car insurance; introduced in M6. |
| Module | Logical application boundary within one deployment. |
| Public module API | Explicit operations/types intentionally exposed to another module; never repositories or persistence entities. |

## Confirmed M1 ownership and authentication

- Every category, monthly budget, limit, and expense belongs to exactly one authenticated owner.
- Ownership comes from a validated OIDC principal, using trusted issuer and stable subject.
- Request payloads must not contain an authoritative owner `userId`.
- Owner A cannot read, modify, list, or reference owner B's resources.
- Keycloak authenticates two preconfigured demonstration users.
- Domain modules depend on an application-level owner identity, not Keycloak-specific types.

## Confirmed M1 category rules

- Category names are trimmed and unique case-insensitively within an owner.
- Different owners may use the same category name.
- M1 supports category creation and listing.
- Renaming, archival, and subcategories belong to M2.
- Later renaming preserves category identity and existing associations.
- Later archival preserves expense and budget history; used categories are not hard-deleted.

## Confirmed M1 budget rules

- A monthly budget appears automatically when the owner sets the first category limit or the overall monthly limit.
- Both previous and future calendar months may be planned and inspected.
- Each owner/category/month has at most one category limit; later writes update it.
- Overall monthly limits are optional and advisory.
- A category limit can exist without an overall monthly limit.
- Category-limit allocation may exceed the overall limit; the API exposes the excess as a warning and a negative unallocated amount.
- Lowering the overall limit never changes category limits.
- Limits can be set independently of income.
- Category and overall limits do not block actual expense recording.
- Unused funds do not roll over automatically.
- M2 introduces optional manually entered opening balance, income, and reusable/copyable monthly limits.

## Confirmed M1 expense rules

- Amounts are positive, exact-decimal PLN values.
- Required fields: owner-derived identity, amount, actual incurred date, and an owner-owned category.
- Description is optional.
- Date incurred determines the budget month; a future date is rejected.
- A category can receive expenses before a category limit exists.
- Expense editing and deletion are deferred to M2.
- Month and category filtering are supported.
- Results are paginated: default 20 items, maximum 100, ordered by most recent incurred date.

## Confirmed M1 summary and warning rules

- Provide category-level spending and the overall sum of all monthly expenses.
- The overall sum includes categories without limits.
- Categories with configured limits and no expenses appear with zero spent.
- Categories containing expenses but no limit appear with an absent/null limit, not a fictional zero limit.
- Category remaining amounts and overall remaining amounts may be negative.
- Missing optional limits must remain absent/null rather than being interpreted as zero.
- Warnings are informational and never block writes.

## M2 category hierarchy

- Maximum hierarchy depth: category -> subcategory.
- Every category and subcategory belongs to the same owner.
- Expense assignment to a subcategory is optional; direct assignment to a parent category remains supported.
- Parent summaries aggregate directly assigned expenses and all child expenses.
- Optional limits may exist at the parent and child levels.
- Child allocations exceeding a parent limit generate a warning; they do not block a write or mutate other limits.
- Existing M1 category and expense records remain valid after introducing the hierarchy.

## Confirmed module responsibilities

- `categories`: category identity, owner-specific naming, and lifecycle.
- `expenses`: validate an owner-owned category, record actual expenses, list them, and expose owner/month/category spending queries.
- `budget`: monthly budgets, optional overall limits, category limits, advisory warnings, and owner/month summaries.

Allowed collaboration:

- `expenses` -> public `categories` API.
- `budget` -> public `categories` and `expenses` APIs.
- No cyclic dependencies, repository sharing, cross-module persistence entities, or cross-module JPA relationships.

## Confirmed delivery order

1. M0: documentation and backlog alignment.
2. M1: secure backend-first budgeting workflow.
3. M2: richer budgeting, subcategories, income, and opening balance.
4. M3: Terraform-managed AWS deployment and basic observability.
5. M4: Angular frontend.
6. M5: CSV import, potentially integrated with S3.
7. M6: recurring, planned, and periodic expenses.
8. M7: AI-assisted import categorization.
9. M8: explainable, human-approved AI budget planning.
10. M9: optional evidence-led advanced extensions.

Detailed completion criteria are maintained in [`docs/roadmap.md`](docs/roadmap.md).
