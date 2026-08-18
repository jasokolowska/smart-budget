# Smart Budget Domain Context

Status: **working draft awaiting product-owner review**.

## Project purpose

Smart Budget serves three related goals:

1. Become a useful household-budgeting application.
2. Demonstrate thoughtful backend and architecture decisions in a public portfolio.
3. Provide a practical environment for learning Kotlin, Spring, modular boundaries, testing, and later AWS.

The project optimizes for understandable trade-offs and a completed vertical slice, not for the largest possible technology list.

## Confirmed constraints

- One active GitHub repository and one current documentation set.
- Approximately one hour of focused development time per working session.
- One deployable application, modular monolith first.
- Java 21 remains the runtime baseline; Kotlin is the target implementation language.
- The first complete workflow is category -> monthly limit -> expense -> monthly summary.
- Every persisted business object belongs to exactly one authenticated owner.
- Client-supplied identifiers must never determine who owns a record.

## Shared vocabulary

| Term | Meaning | Important distinction |
| --- | --- | --- |
| Owner | The authenticated person whose budget data is being accessed. | An owner is resolved from a trusted identity context, not from a request body. |
| Category | An owner-specific label used to group expenses. | A category is not a separately deployed service. |
| Budget month | A calendar month for which spending is planned and summarized. | Month boundaries and timezone details still require owner confirmation. |
| Monthly limit | The maximum planned spending for one category in one budget month. | A limit is a planning amount, not necessarily a rule that blocks expenses. |
| Expense | A recorded spending entry belonging to one owner and assigned to one category. | The first milestone covers expenses; income, transfers, imports, and recurring payments are future capabilities. |
| Spent amount | Sum of matching expenses for an owner, category, and budget month. | It must never include another owner's data. |
| Remaining amount | Monthly limit minus spent amount. | Whether a negative result is returned directly must be confirmed. |
| Module | A logical domain boundary inside one deployable Spring application. | A Spring Modulith application module is not automatically the same as a Gradle subproject. |
| Public module API | The small set of types or operations a module exposes to other modules. | Repositories, JPA entities, and internal implementation details must not become accidental shared APIs. |

## Provisional domain boundaries

These are design hypotheses, not implemented packages or a final bounded-context map:

- `categories`: category lifecycle and owner-specific category rules.
- `budgeting`: budget months and category-specific monthly limits.
- `transactions`: recording and retrieving expenses.
- `reporting`: monthly read models combining spending and limits.

Potential cross-module direction: `budgeting` and `transactions` consult the public category API; `reporting` reads through public budgeting and transaction APIs. The exact arrangement must be challenged during the owner review.

## Open decisions for the owner

1. Is a category unique per owner after trimming whitespace and ignoring case?
2. What happens when a category is renamed or deleted after being used?
3. Is a budget month created explicitly or when its first monthly limit is configured?
4. Can a category have at most one monthly limit per owner and month?
5. Are expense amounts always stored as positive values in the first milestone?
6. Can an expense be created when its category has no monthly limit?
7. Should overspending be allowed, rejected, or shown as a negative remaining amount?
8. Which date and timezone define a budget month?
9. Are expense edits and deletions included in the first slice?
10. What is the smallest acceptable authentication approach for an owner-isolated API?
11. Is OpenAPI/Swagger sufficient as the first demonstrable interface?
12. Should `categories` be an independent module or part of `budgeting`?

Do not silently convert any answer above into an implementation requirement before discussing it with the owner.
