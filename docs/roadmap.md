# Smart Budget Delivery Roadmap

Status: **approved product-owner roadmap**.

The milestones are ordered by user value and learning dependency. Development should proceed through small issues that fit approximately one focused hour. A milestone is an outcome, not a promise that every later idea must be implemented.

## Milestone overview

| ID | Outcome | Main learning focus | Depends on |
| --- | --- | --- | --- |
| M0 | Approved documentation, architecture, and aligned backlog | Product discovery, decision records, domain modeling | None |
| M1 | Secure backend-first personal budgeting workflow | Kotlin, Spring Boot, Modulith, PostgreSQL, Keycloak, tests, CI | M0 |
| M2 | Complete useful budgeting foundations | Domain modeling, hierarchy, invariants, owner-scoped queries | M1 |
| M3 | Cost-aware AWS deployment described in Terraform | Cloud architecture, IaC, secrets, deployment, observability | M1 + selected M2 outcomes |
| M4 | Focused Angular frontend for the deployed API | API integration, OIDC SPA flow, usable portfolio presentation | M3 |
| M5 | Reliable CSV import and optional S3-backed processing | Ingestion, validation, idempotency, cloud integration | M3; M4 for UI |
| M6 | Recurring, planned, and periodic obligations | Time-based modeling and scheduling trade-offs | M2; M4 for UI |
| M7 | AI-supported expense categorization | LLM integration, human review, privacy, evaluation | M5 |
| M8 | Human-approved AI budget planning | Explainable AI, structured outputs, financial context | M2 + M6; M5 adds useful history |
| M9 | Optional advanced extensions | Evidence-led events, reliability, observability, extraction | Relevant earlier milestone |

## M0 — Documentation and architecture baseline

**Goal:** make the approved product, architecture, domain language, and issue backlog consistent.

Scope:

- Maintain one canonical repository and preserve rejected microservice documents in the archive.
- Record approved product behavior in the PRD, requirements, user stories, domain context, and roadmap.
- Maintain an accurate C4 model and ADRs.
- Rewrite useful legacy issues and close obsolete Gateway/independent-service tasks.
- Keep credentials out of the public repository.

Definition of done:

- Documentation consistently names `categories`, `budget`, and `expenses`.
- All first-milestone owner/security/budget/expense rules are explicit.
- Future capabilities have an identified milestone and are not mixed into M1 issues.
- PR #91 contains the complete reviewed documentation baseline.

## M1 — Secure core budgeting backend

**Goal:** demonstrate one complete owner-isolated workflow through OpenAPI.

### Product scope

- Two preconfigured Keycloak owners and OIDC token validation.
- Personal category creation and listing.
- Optional advisory overall monthly spending limit.
- Advisory category limits, one per owner/category/month.
- Budget creation from the first category limit or overall monthly limit.
- Positive PLN actual expenses with incurred date, owner-owned category, and optional description.
- Historical/current expense recording; no future-dated actual expenses.
- Planning and inspecting past, current, and future budget months.
- Paginated monthly expense list with optional category filter.
- Category-level and overall monthly summaries.
- Non-blocking warnings for category over-allocation and overspending.
- Inclusion of categories with zero spending and spending categories with no limit.

### Engineering scope

- Kotlin-first Spring Boot application on Java 21 and Gradle Kotlin DSL.
- Spring Modulith boundaries: `categories`, `budget`, `expenses`.
- One PostgreSQL database and Flyway migrations.
- Module-owned tables, identifier-based cross-module references, and explicit synchronous public APIs.
- Spring Security OAuth2 Resource Server and documented OpenAPI.
- Local Docker Compose for application, PostgreSQL, and Keycloak.
- Unit, PostgreSQL integration, cross-owner isolation, and Spring Modulith architecture tests.
- GitHub Actions checks and test results.

Definition of done:

1. Two demo owners independently complete the OpenAPI scenario in `user_stories.md`.
2. Invalid/future expenses, foreign-owner references, and cross-owner reads are rejected.
3. Overall and category calculations, missing limits, zero spending, and negative remaining values are tested.
4. No cyclic or illegal module dependencies exist.
5. Pull-request checks pass.

Explicit exclusions: subcategories, income, opening balance, category rename/archive, expense edit/delete, automatic month rollover, Angular, AWS, import, AI, recurring/planned expenses, message brokers, and microservices.

### Suggested implementation slices

1. Establish Kotlin-first build and the three Modulith boundaries.
2. Add PostgreSQL, Flyway, and local Compose scaffolding.
3. Configure Keycloak, JWT validation, and owner identity.
4. Implement owner-scoped categories.
5. Implement month creation and optional overall spending limit.
6. Implement advisory category limits and allocation warnings.
7. Implement actual expense creation and validation.
8. Implement paginated month/category expense queries.
9. Implement category/overall monthly summaries.
10. Add two-owner integration and architecture tests.
11. Publish OpenAPI and stabilize CI acceptance checks.

## M2 — Useful budgeting capabilities

**Goal:** make the core product practical without changing its architecture.

Scope:

- Rename categories while preserving identifiers and relationships.
- Archive categories instead of deleting history.
- Two-level category hierarchy, with optional subcategory assignment.
- Parent/child advisory limits and warning-based allocation consistency.
- Parent totals combining directly assigned and child expenses.
- Edit and delete owner-owned actual expenses.
- Record positive monthly income.
- Enter an optional manual opening balance separately from income.
- Compare available funds, monthly plan, and actual expenses.
- Copy monthly category limits only through explicit user action.

Rules:

- Never roll unused money forward automatically.
- Never treat opening balance as income.
- Never mutate category limits when an overall limit changes.
- Keep ownership, archived history, and recalculated summaries consistent.
- Maximum hierarchy depth is two.

Definition of done:

- All M1 scenarios remain valid after introducing the hierarchy.
- Existing parent-level expenses remain compatible.
- Parent summaries and warnings are validated with focused tests.
- Income and opening balance produce a transparent available-funds calculation.
- Owners can correct expenses without leaking or corrupting another owner's history.

## M3 — AWS deployment, Terraform, and operational baseline

**Goal:** deploy the useful backend without choosing infrastructure before understanding trade-offs.

First task: produce a hosting ADR comparing appropriate options, including container-based alternatives where relevant. Evaluate cost, operational complexity, fit for a modular monolith, observability, and learning value.

Scope:

- Choose one documented deployment architecture after the comparison.
- Describe required infrastructure in Terraform.
- Store configuration and secrets using appropriate AWS facilities.
- Provide a reproducible deployment and a documented rollback/teardown path.
- Add application logs, essential service metrics, an operational alarm, and an AWS cost/budget alert.
- Keep architecture to one deployable backend and one relational data store.

Definition of done:

- The selected option and rejected alternatives are documented.
- Infrastructure can be provisioned reproducibly.
- The deployed API is reachable and owner-isolated.
- Cost exposure, monitoring, and cleanup steps are documented.
- No unnecessary queue, cluster, gateway, or extra service is introduced.

## M4 — Angular user interface

**Goal:** present and use the deployed product without requiring Swagger.

Scope:

- Angular + TypeScript application with minimal maintainable structure.
- OIDC login against the approved identity-provider setup.
- Categories and optional subcategories.
- Overall and category monthly budgets.
- Expense list, creation, and available lifecycle operations.
- Monthly summary and planning warnings.

Definition of done:

- A demo owner can authenticate and complete the product flow visually.
- The frontend uses the deployed backend and respects owner isolation.
- Initial scope excludes complex client architecture, PWA/offline mode, and notification infrastructure.

## M5 — CSV import and cloud-assisted ingestion

**Goal:** reduce repetitive data entry while creating a real cloud-integration scenario.

Scope:

- Owner-scoped CSV upload.
- Validate columns, dates, positive monetary amounts, category mapping, and duplicate/idempotency behavior.
- Return row-level success and error information.
- Evaluate S3 storage and asynchronous processing based on actual need and cost.
- Provide UI upload only after the Angular milestone exists.

Definition of done:

- A valid sample file imports correct owner-scoped expenses.
- Invalid rows produce an understandable report.
- Import does not expose another owner's data.
- Any S3, SQS, EventBridge, or Lambda use is explained by a concrete requirement.

## M6 — Recurring, planned, and periodic expenses

**Goal:** make future obligations explicit before they are used for automated planning.

Scope:

- Define recurring obligations with category, amount, frequency, and next due date.
- Model non-monthly periodic costs, e.g. annual car insurance, school expenses, or holidays.
- Distinguish future plans from actual incurred expenses.
- Define payment confirmation and subsequent-occurrence rules.
- Expose upcoming obligations to a user and later budget planner.

Definition of done:

- Future obligations do not silently appear as actual expense history.
- A yearly cost can be represented and translated into a monthly planning target when useful.
- Ownership and scheduling semantics are covered by automated tests.

## M7 — AI-assisted transaction categorization

**Goal:** make CSV import more useful through explainable, owner-controlled suggestions.

Scope:

- Suggest an owner-owned category or subcategory based on transaction description and available history.
- Show confidence or rationale where practical.
- Allow the owner to confirm or correct suggestions.
- Evaluate model quality, data privacy, operational cost, and fallback behavior.

Definition of done:

- Sample imported transactions receive reviewable suggestions.
- The system never uses another owner's categories or history.
- Model/vendor selection is documented when chosen, not assumed in advance.

## M8 — Explainable AI-assisted budget planning

**Goal:** propose a realistic future budget while keeping the owner in control.

Inputs:

- Historical category and subcategory spending.
- Previous limits and their divergence from actual spending.
- Monthly income and optional opening balance.
- Recurring obligations and non-monthly periodic expenses.
- Seasonal or planned costs explicitly recorded by the owner.

Expected behavior:

- Return proposed overall/category allocations with understandable justification.
- For example, annual insurance of 1,200 PLN can motivate reserving 100 PLN per month.
- Allow the owner to review, edit, accept, or reject the proposal.
- Never silently modify current limits or financial history.

Definition of done:

- Recommendations are traceable to approved owner data.
- Budget writes occur only after explicit owner confirmation.
- Quality, privacy, cost, and failure modes are documented.

## M9 — Optional evidence-led extensions

Candidate work only after documented need:

- Deeper observability, tracing, or Datadog integration.
- Notifications and user-controlled preferences.
- Reliable asynchronous ingestion or events.
- A separate deployable service for demonstrable independent scaling or operational isolation.
- Broader analytics, exports, multiple currencies, or shared households.

Microservice extraction is never a mandatory milestone. Each candidate requires an ADR explaining the business/operational problem, alternatives, migration cost, and expected evidence of improvement.

## One-hour delivery convention

For each work session:

1. Pick one open issue and one acceptance criterion.
2. Spend the first minutes identifying the impacted module and existing tests.
3. Implement the smallest complete change.
4. Run the relevant focused tests.
5. Record the next concrete step in the issue or pull request.

## Official learning references

- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Spring Modulith testing](https://docs.spring.io/spring-modulith/reference/testing.html)
- [Spring Security OAuth2 Resource Server JWT](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Keycloak documentation](https://www.keycloak.org/documentation)
- [Testcontainers PostgreSQL](https://java.testcontainers.org/modules/databases/postgres/)
- [Docker Compose documentation](https://docs.docker.com/compose/)
- [GitHub Actions documentation](https://docs.github.com/en/actions)
- [Terraform AWS tutorials](https://developer.hashicorp.com/terraform/tutorials/aws-get-started)
- [AWS Well-Architected Framework](https://docs.aws.amazon.com/wellarchitected/latest/framework/welcome.html)
- [Angular documentation](https://angular.dev/overview)
