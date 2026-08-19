# User Stories

Status: **draft for owner review**. These stories describe product behavior, not API gateways, brokers, or independently deployed services.

The first milestone is demonstrated through the documented OpenAPI. A dedicated frontend is not part of its acceptance criteria.

Real authentication through Keycloak OIDC is part of the first milestone. All stories operate on the Owner established from a validated access token.
The demonstration uses two preconfigured Owners; public self-registration is deferred.

## US-001: Create a personal expense category

**As** an authenticated budget owner, **I want** to create an expense category, **so that** I can group related spending.

Acceptance criteria:

1. A valid category name creates a category owned by the authenticated user.
2. Blank or invalid names are rejected.
3. The new category appears only in that owner's category list.
4. Another owner cannot access or modify it.
5. Surrounding whitespace is removed from the name.
6. The same Owner cannot create two Categories whose trimmed names differ only by letter case.
7. Different Owners may use the same Category name.

Confirmed lifecycle rule: renaming preserves the Category's identity and existing associations, and the new name follows the same normalization and uniqueness rules.

Open questions: whether renaming is included in the first milestone and the Category deletion rule.

## US-002: Set a monthly spending limit

**As** an authenticated budget owner, **I want** to set a limit for one of my categories in a selected month, **so that** I know how much I plan to spend.

Acceptance criteria:

1. A positive decimal amount can be associated with an owner-owned category and budget month.
2. A category belonging to another owner cannot be used.
3. The saved limit can be retrieved for that owner and month.

Open questions: duplicate limits and whether a monthly budget must exist explicitly.

## US-003: Record an expense

**As** an authenticated budget owner, **I want** to record an expense for one of my categories, **so that** actual spending can be compared with my monthly plan.

Acceptance criteria:

1. A valid expense includes a monetary amount, date, description, and category.
2. The expense belongs to the authenticated owner.
3. An owner cannot record an expense against another owner's category.
4. Invalid request data is rejected.

Open questions: positive-versus-negative amount convention and expense edit/delete support.

## US-004: View monthly category spending

**As** an authenticated budget owner, **I want** to see a category's monthly limit, spent amount, and remaining amount, **so that** I can understand my current budget position.

Acceptance criteria:

1. The summary returns the selected category and budget month.
2. Spent amount includes only that owner's expenses in the selected category and month.
3. Remaining amount is calculated as `limit - spent`.
4. Data from other owners or other months never influences the result.

Open questions: timezone, missing-limit behavior, and overspending representation.

## US-005: Prevent cross-owner access

**As** a budget owner, **I want** my categories, limits, expenses, and summaries to remain private, **so that** other users cannot access my financial data.

Acceptance criteria:

1. Two owners can have independent categories and budgets.
2. Requests referencing another owner's category, expense, or budget do not reveal protected data.
3. The application does not trust a client-provided owner identifier.
4. Automated integration tests cover at least one cross-owner scenario for each implemented capability.
5. Requests without a valid access token are rejected with `401`.

## End-to-end acceptance scenario

Given owner A has created a category named `Groceries` and configured a monthly limit of `1000.00`, when owner A records an expense of `150.00` in that category and month, then the monthly summary returns:

- `limit = 1000.00`
- `spent = 150.00`
- `remaining = 850.00`

An expense belonging to owner B or a different budget month does not alter owner A's result.

## Deferred story themes

Future story themes may include public self-registration, CSV imports, recurring payments, AI suggestions, reminders, a dedicated frontend, cloud deployment, and more advanced reporting. They are not acceptance criteria for US-001 through US-005.
