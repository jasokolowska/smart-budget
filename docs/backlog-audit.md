# Legacy GitHub Issue Audit

Status: **review proposal only**. No GitHub issue or GitHub Project field has been changed by this document.

Source: open issues in `jasokolowska/smart-budget`, inspected on 2026-08-18. There are **37 open issues**, plus the separate open pull request #89. Most issues were written for an earlier distributed-system concept.

## Review categories

- `KEEP`: still useful with little or no conceptual change.
- `REWRITE`: the underlying product/engineering concern is useful, but the issue currently assumes incorrect architecture or scope.
- `DEFER`: potentially valuable after the first product milestone.
- `REPLACE`: the task is tied to infrastructure or a service boundary that no longer exists in the selected architecture; close only after the owner approves a replacement or archival decision.

## Issue-by-issue review

| Issue | Existing concern | Recommendation | Reason / proposed direction |
| --- | --- | --- | --- |
| [#46](https://github.com/jasokolowska/smart-budget/issues/46) | Setup and repository hygiene | REWRITE | Scope to one repository, Gradle, documentation consistency, and the modular-monolith baseline. |
| [#49](https://github.com/jasokolowska/smart-budget/issues/49) | Compose: two databases, RabbitMQ, Keycloak, Mailpit | REWRITE | Initial local infrastructure should contain the Smart Budget PostgreSQL database and Keycloak; remove the second application database, RabbitMQ, and Mailpit. |
| [#51](https://github.com/jasokolowska/smart-budget/issues/51) | Keycloak realm, OIDC client, SPA scopes | REWRITE | Configure the smallest reproducible realm, API client, and two demonstration Owners needed for OIDC and OpenAPI authorization. Keep public self-registration disabled and remove assumptions about a dedicated SPA. |
| [#52](https://github.com/jasokolowska/smart-budget/issues/52) | API Gateway epic | REPLACE | The first architecture has no independent API Gateway. |
| [#53](https://github.com/jasokolowska/smart-budget/issues/53) | Gateway routes to services | REPLACE | There are no separately deployed services to route to. |
| [#54](https://github.com/jasokolowska/smart-budget/issues/54) | Gateway proxy and cache for OpenAI | REPLACE | Neither a gateway nor AI integration belongs to the first milestone. |
| [#55](https://github.com/jasokolowska/smart-budget/issues/55) | Gateway JWT resource server | REWRITE | Keep owner authentication and 401/403 behavior inside the single Spring application. |
| [#56](https://github.com/jasokolowska/smart-budget/issues/56) | Transaction Service epic | REWRITE | Convert to a transaction/expense module epic focused on the first vertical slice. |
| [#57](https://github.com/jasokolowska/smart-budget/issues/57) | Transaction/category database migrations | REWRITE | Keep Flyway migrations, but model one database and only MVP-owned tables. |
| [#58](https://github.com/jasokolowska/smart-budget/issues/58) | CSV import | DEFER | Useful future capability, not required for manual-expense MVP. |
| [#59](https://github.com/jasokolowska/smart-budget/issues/59) | Advanced transaction filtering | DEFER | Basic month/category retrieval may be needed, but advanced filters are not MVP acceptance criteria. |
| [#60](https://github.com/jasokolowska/smart-budget/issues/60) | ATM flow and event publishing | DEFER | Cash-flow heuristics and a broker are outside the first milestone. |
| [#61](https://github.com/jasokolowska/smart-budget/issues/61) | Budget Service epic with AI and recurring payments | REWRITE | Convert to a budgeting-module epic containing monthly category limits only. |
| [#62](https://github.com/jasokolowska/smart-budget/issues/62) | AI-generated budget proposal | DEFER | AI can be evaluated after the core budgeting workflow works. |
| [#63](https://github.com/jasokolowska/smart-budget/issues/63) | Subscribe to transaction-created broker events | REWRITE | Compute the first monthly summary through explicit synchronous module APIs; reconsider events only if justified. |
| [#64](https://github.com/jasokolowska/smart-budget/issues/64) | Recurring payments and scheduling | DEFER | Not part of the first owner/category/limit/expense workflow. |
| [#65](https://github.com/jasokolowska/smart-budget/issues/65) | Budget, recurring, preferences, and audit schema | REWRITE | Restrict the first schema to owner-scoped categories, limits, and expenses. |
| [#66](https://github.com/jasokolowska/smart-budget/issues/66) | Notification Service epic | REPLACE | Independent notification service conflicts with the selected architecture; recreate a future feature only when needed. |
| [#67](https://github.com/jasokolowska/smart-budget/issues/67) | Notification preferences and Mailpit | DEFER | Notifications are outside the first milestone. |
| [#68](https://github.com/jasokolowska/smart-budget/issues/68) | Web push and VAPID | DEFER | Dedicated frontend/push infrastructure is out of scope. |
| [#69](https://github.com/jasokolowska/smart-budget/issues/69) | Full Angular frontend epic | DEFER | Decide on a dedicated frontend only after the backend workflow is demonstrable. |
| [#70](https://github.com/jasokolowska/smart-budget/issues/70) | Angular application skeleton | DEFER | Not needed to verify a backend-first MVP. |
| [#71](https://github.com/jasokolowska/smart-budget/issues/71) | SPA Keycloak integration | DEFER | Keycloak is selected for the MVP API, but SPA login integration depends on a future dedicated frontend. |
| [#72](https://github.com/jasokolowska/smart-budget/issues/72) | Frontend CSV upload | DEFER | Depends on both a dedicated frontend and CSV import. |
| [#73](https://github.com/jasokolowska/smart-budget/issues/73) | Frontend transaction list | DEFER | Revisit after deciding whether a dedicated frontend is required. |
| [#74](https://github.com/jasokolowska/smart-budget/issues/74) | Frontend AI budgeting view | DEFER | Both AI and a dedicated frontend are outside the first milestone. |
| [#75](https://github.com/jasokolowska/smart-budget/issues/75) | Frontend recurring payments | DEFER | Recurring payments and a dedicated frontend are deferred. |
| [#76](https://github.com/jasokolowska/smart-budget/issues/76) | Full frontend dashboard | DEFER | The MVP needs a monthly summary API, not a broad frontend dashboard. |
| [#77](https://github.com/jasokolowska/smart-budget/issues/77) | Frontend notification settings | DEFER | Notifications and frontend work are not MVP requirements. |
| [#78](https://github.com/jasokolowska/smart-budget/issues/78) | GitHub Actions build and tests | KEEP | Existing Gradle CI remains useful; review precise gaps before making a separate CI change. |
| [#79](https://github.com/jasokolowska/smart-budget/issues/79) | Build and publish Docker images | DEFER | Container publishing follows a working application, not documentation cleanup. |
| [#80](https://github.com/jasokolowska/smart-budget/issues/80) | Testcontainers for Postgres, RabbitMQ, Mailpit, Keycloak | REWRITE | Cover PostgreSQL persistence, JWT validation, and cross-owner isolation. Add a Keycloak-backed smoke test only where it proves realm/client integration; remove RabbitMQ and Mailpit. |
| [#81](https://github.com/jasokolowska/smart-budget/issues/81) | Prometheus, OpenTelemetry, and observability | DEFER | Useful after the first product flow and deployment requirements exist. |
| [#82](https://github.com/jasokolowska/smart-budget/issues/82) | OpenAPI for all services | REWRITE | Document one application API and the first budgeting workflow. |
| [#83](https://github.com/jasokolowska/smart-budget/issues/83) | Legacy HP-001 through HP-016 acceptance scenarios | REWRITE | Replace with the five current owner/category/limit/expense/summary stories. |
| [#84](https://github.com/jasokolowska/smart-budget/issues/84) | Releases, changelog, and published artifacts | DEFER | Revisit when there is a usable release candidate. |
| [#85](https://github.com/jasokolowska/smart-budget/issues/85) | Category Service | REWRITE | Scope to an owner-isolated category capability inside the modular monolith; do not merge PR #89 wholesale. |

## Suggested review sequence

1. Approve or revise the product and architecture documents.
2. Confirm whether each `REPLACE` item should be closed as obsolete.
3. Decide whether each `REWRITE` item should be edited or replaced by a smaller fresh issue.
4. Move `DEFER` items out of the immediate milestone without deleting product ideas.
5. Keep the initial implementation backlog limited to the accepted first workflow and supporting quality/security tasks.

## GitHub Project limitation

The available GitHub integration can inspect and modify repository issues, but it does not expose direct operations for GitHub Projects v2 boards, custom project fields, or project-item statuses. Issue edits may appear in a project when those issues are already included, but board-specific changes require a GitHub Projects-capable integration or manual action.
