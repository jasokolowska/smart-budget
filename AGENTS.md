# Smart Budget Agent Instructions

## Scope of the current branch

This branch is for **documentation and backlog analysis only**. Do not modify application source code, Gradle configuration, CI workflows, authentication, migrations, pull request #89, or issue statuses unless the repository owner explicitly requests a separate task.

## Read these files first

1. `README.md`
2. `CONTEXT.md`
3. `docs/prd.md`
4. `docs/requirements.md`
5. `docs/user_stories.md`
6. `docs/tech-stack.md`
7. `docs/architecture/adr/0001-modular-monolith-first.md`
8. `docs/documentation-review.md`
9. `docs/backlog-audit.md` when discussing existing GitHub issues.

## Facts and decisions

- There is exactly one active repository: `jasokolowska/smart-budget`.
- The current `main` contains an early-stage Java 21/Spring Boot/Gradle implementation with `app` and `transaction` Gradle subprojects.
- Kotlin-first development, PostgreSQL, Flyway, and Spring Modulith are architectural targets; do not claim they are already configured unless the code proves it.
- The selected architecture is one deployable modular monolith. API Gateway, multiple independently deployed services, two databases, RabbitMQ, Kafka, and mandatory asynchronous messaging are not part of the first milestone.
- The first product workflow is category -> monthly limit -> expense -> monthly spent/remaining summary.
- The project owner works in focused sessions of approximately one hour. Prefer small decisions, short documents, and reviewable slices.

## Documentation rules

- Distinguish confirmed decisions, assumptions awaiting confirmation, existing implementation, and future possibilities.
- Use terminology from `CONTEXT.md`; do not introduce synonyms for the same domain concept.
- Keep product requirements independent of premature infrastructure choices.
- Update all affected documents when an owner decision changes scope, terminology, module responsibilities, or acceptance criteria.
- Preserve superseded material under `docs/archive/`; never treat it as the current source of truth.
- Ask the owner about unresolved product behavior instead of inventing business rules.
- Never broaden the first milestone with CSV, AI, recurring payments, notifications, Angular, AWS, or microservices without an explicit owner decision.

## Review workflow

When asked to review documentation, interview the owner one topic at a time. Prioritize product scope, ownership/security, monthly-budget semantics, category lifecycle, expense rules, and provisional module boundaries. Update the documents only after resolving the relevant ambiguity.

If Matt Pocock's skills are installed, prefer `/grill-with-docs` for this workflow; it relies on both `grilling` and `domain-modeling`. `/grill-me` by itself does not persist the conclusions.
