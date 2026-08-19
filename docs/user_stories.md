# User Stories and Acceptance Scenarios

Status: **approved baseline**. Stories describe product behavior inside one modular application, not an API Gateway, message broker, or independent service.

## M1: secure budgeting workflow

### US-001: Authenticate as a personal budget owner

**As** a budget owner, **I want** to authenticate using an existing Keycloak account, **so that** only my financial records are accessible.

Acceptance:

1. The local demo includes two preconfigured owners.
2. A validated OIDC token establishes the owner identity.
3. Missing or invalid authentication is rejected.
4. Public self-registration is not required.

### US-002: Create and list personal categories

**As** an authenticated owner, **I want** to create a named expense category, **so that** spending can be grouped.

Acceptance:

1. Category name is trimmed before storing.
2. Names are unique case-insensitively per owner.
3. Different owners may use the same name.
4. Only the authenticated owner sees their categories.

### US-003: Set an optional overall monthly plan

**As** an owner, **I want** to enter an overall monthly spending target, **so that** I can compare all expenses with my plan.

Acceptance:

1. Setting the amount creates the monthly budget if necessary.
2. The month can be historical, current, or future.
3. The overall amount is optional.
4. Lowering the amount does not alter category limits.
5. Planning discrepancies are displayed but do not block saves.

### US-004: Configure category-level monthly limits

**As** an owner, **I want** to assign a monthly amount to a category, **so that** I can plan category spending.

Acceptance:

1. Setting a first category limit creates the budget month if necessary.
2. A category has at most one limit per owner and month.
3. Setting it again updates the existing amount.
4. Owner B's category cannot be used.
5. Category allocations exceeding an overall limit are accepted and generate a warning.

### US-005: Record an actual expense

**As** an owner, **I want** to record an actual expense, **so that** my monthly spending reflects reality.

Acceptance:

1. Amount is positive and expressed in PLN.
2. Actual incurred date and owner-owned category are required.
3. Description is optional.
4. Future incurred dates are rejected.
5. Category limits are optional.
6. Actual overspending never blocks the expense.
7. The date incurred determines the selected budget month.

### US-006: Inspect a paginated monthly expense list

**As** an owner, **I want** to inspect expenses for a selected month, **so that** I can understand the transactions behind a summary.

Acceptance:

1. Results include incurred date, category, amount, and optional description.
2. Filtering by category is optional.
3. Results default to 20 rows per page and never exceed 100.
4. The latest incurred expense appears first.
5. Another owner's records cannot appear in results.

### US-007: Review category and overall monthly totals

**As** an owner, **I want** to view category-level and overall monthly spending, **so that** I understand available funds and planning inconsistencies.

Acceptance:

1. Overall spending includes all categories, including categories without limits.
2. A category with a limit and no expenses appears with zero spent.
3. A category with expenses and no limit shows an absent limit rather than zero.
4. Category and overall remaining values may be negative.
5. Overall allocation, unallocated amount, and over-allocation are shown when an overall limit exists.
6. Warnings remain informative and never reject a write.

### US-008: Prevent cross-owner data access

**As** an owner, **I want** my financial data isolated from other accounts, **so that** no other user can inspect or manipulate it.

Acceptance:

1. Owner B cannot view Owner A's categories, budgets, limits, expenses, or summaries.
2. Owner B cannot attach an expense or limit to Owner A's category.
3. No client-provided owner identifier can impersonate another owner.
4. Automated integration tests use both configured owners.

## M1 end-to-end acceptance scenario

1. Owner A authenticates and creates `Food`, `Clothing`, and `Transport`.
2. Owner A sets an August overall spending plan of 5,000 PLN.
3. Owner A sets Food to 3,000 PLN and Clothing to 3,000 PLN.
4. The response exposes a 1,000 PLN over-allocation warning without rejecting either category limit.
5. Owner A records a 150 PLN Food expense and a 200 PLN Transport expense without a category limit.
6. August overall spending equals 350 PLN and overall remaining equals 4,650 PLN.
7. Food shows 150 PLN spent and 2,850 PLN remaining.
8. Clothing shows zero spent and 3,000 PLN remaining.
9. Transport shows 200 PLN spent and an absent/null limit.
10. Owner A lists August expenses and optionally filters Food.
11. Owner B cannot view or reuse Owner A's data.

## M2 stories

- Rename an owned category without changing its identity or history.
- Archive an owned category without deleting historical expenses.
- Create two-level categories, e.g. `Car -> Fuel / Insurance / Repairs`.
- Assign an expense directly to `Car` or to one of its subcategories.
- View parent totals aggregating direct and child expenses.
- Configure parent and child advisory limits; show allocation discrepancies.
- Correct or delete an owned expense and recalculate affected summaries.
- Record monthly income and a separate manually entered opening balance.
- Explicitly copy category limits to another month.

## Stories for later milestones

- **M3:** deploy the backend to AWS using Terraform and cost-aware observability.
- **M4:** use an Angular UI for authentication, budgets, categories, expenses, and summaries.
- **M5:** upload a CSV and review successfully imported and rejected rows.
- **M6:** manage repeating obligations and periodic costs such as annual car insurance.
- **M7:** review AI-suggested categories for imported transactions.
- **M8:** review, edit, accept, or reject an explainable AI-proposed budget.
- **M9:** introduce notifications, event-driven processing, or a separate service only when evidence supports it.
