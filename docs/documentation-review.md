# Approved Documentation and Agent Review Workflow

## Current status

The product owner has completed the structured product/architecture interview. Approved decisions are captured in `CONTEXT.md`, `docs/requirements.md`, `docs/user_stories.md`, and `docs/roadmap.md`.

Do not reopen already approved questions merely because historical documents proposed a conflicting architecture.

## Canonical reading order

1. `README.md` — honest current implementation versus target direction.
2. `CONTEXT.md` — approved terminology and product rules.
3. `docs/roadmap.md` — milestones, dependencies, and completion criteria.
4. `docs/prd.md` — product intent and milestone scope.
5. `docs/requirements.md` — verifiable functional/non-functional requirements.
6. `docs/user_stories.md` — owner-visible acceptance scenarios.
7. `docs/tech-stack.md` — current versus target technologies.
8. `docs/architecture/adr/` — accepted architecture decisions.
9. `docs/architecture/workspace.dsl` — target context/container/module model.
10. `docs/backlog-audit.md` — legacy issue alignment.

## Rules for future refinements

- Ask the owner only about genuinely new, materially relevant decisions.
- Ask one question at a time and include a concrete recommendation with reasoning.
- Do not change already-approved requirements without the owner's agreement.
- Update all affected documents when a decision changes vocabulary, scope, module boundaries, or acceptance criteria.
- Historical microservice files are archived reference material, not an implementation specification.

## Cursor / Copilot prompt

```text
Read AGENTS.md, CONTEXT.md, docs/roadmap.md, docs/requirements.md,
docs/user_stories.md, docs/tech-stack.md, and docs/architecture/adr/.

Treat them as approved product decisions. The current implementation may lag
behind the documentation, so inspect the code before claiming a feature exists.

Work on exactly one selected issue and milestone. Preserve the categories,
budget, and expenses module boundaries; owner isolation; advisory budget
warnings; PostgreSQL table ownership; and Kotlin-first direction.

Do not introduce an API Gateway, broker, separate deployable services,
frontend, AWS, CSV, AI, or recurring expenses into M1.

If a genuinely unresolved implementation choice affects accepted product
behavior, ask one focused question with your recommendation. Otherwise
implement the smallest testable slice and explain which acceptance criterion
it satisfies.
```

## Definition of done for M0

- The documented product behavior is consistent across the PRD, domain vocabulary, requirements, stories, C4, and ADRs.
- The complete M0-M9 roadmap exists and each milestone has explicit completion criteria.
- Approved issue titles and descriptions no longer require rejected microservices or unsupported first-milestone capabilities.
- Tracked secret-containing environment files are removed from the current branch and replaced with sanitized examples.
- The documentation pull request can be reviewed as one coherent baseline.
