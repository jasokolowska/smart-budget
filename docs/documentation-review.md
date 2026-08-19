# Documentation Review and Cursor Workflow

Status: **active checklist for the documentation-only branch**.

## Objective

Produce one consistent, owner-approved product and architecture baseline before changing application code or reorganizing live GitHub issues.

## Documents to review

1. `README.md`: honest current state and portfolio narrative.
2. `CONTEXT.md`: shared vocabulary, constraints, and unresolved decisions.
3. `docs/prd.md`: product purpose, first milestone, and exclusions.
4. `docs/requirements.md`: implementation-oriented functional and quality requirements.
5. `docs/user_stories.md`: user behavior and measurable acceptance criteria.
6. `docs/tech-stack.md`: verified current technologies versus selected targets.
7. `docs/architecture/adr/0001-modular-monolith-first.md`: architectural rationale.
8. `docs/architecture/workspace.dsl`: target C4 context, container, and provisional module views.
9. `docs/backlog-audit.md`: recommendations for reviewing old issues without changing them yet.

## Suggested interview order

### Round 1: Product boundary

- What makes the first usable version valuable to the owner?
- Confirmed: category -> monthly limit -> expense -> monthly summary is the complete first milestone.
- Confirmed: the documented OpenAPI is sufficient to demonstrate it; a dedicated frontend is not required.
- Which deferred features must remain visible as future ideas without contaminating MVP scope?

### Round 2: Ownership and security

- What identifies a budget owner?
- Which owner-isolation cases must be demonstrated and tested?
- Confirmed: real authentication is required for the first milestone; a development-only identity adapter is insufficient.
- Confirmed: Keycloak authenticates Owners through OIDC; Smart Budget validates JWT access tokens as an OAuth2 resource server.
- Confirmed: two preconfigured Owners support the MVP demonstration; public self-registration is deferred.
- Which security behavior is required before any public deployment?

### Round 3: Category lifecycle

- Confirmed: Category names are trimmed and unique per Owner using a case-insensitive comparison.
- Confirmed: renaming a used Category preserves its identity and existing associations; the new name follows the existing normalization and uniqueness rules.
- Can a category be deleted when limits or expenses reference it?

### Round 4: Monthly planning and expenses

- Does a month exist independently, or only through configured category limits?
- Can one category have more than one limit in the same month?
- Are expense amounts represented as positive values?
- What happens when an expense has no configured limit?
- Is overspending allowed, and how is it represented?
- Which date and timezone determine the budget month?
- Are expense edits or deletions required in the first milestone?

### Round 5: Module boundaries and backlog

- Is `categories` a separate module or part of `budgeting`?
- Does `reporting` need a module, or is it a public query within another module?
- What must remain internal to each logical module?
- Which existing issues are still valid, need rewriting, should be deferred, or should be replaced?

## Recommended Cursor skills

For a documentation-producing interview, prefer Matt Pocock's `grill-with-docs`, together with its required `grilling` and `domain-modeling` skills. Include `setup-matt-pocock-skills` if using that collection's documented setup workflow.

`grill-me` can help clarify an idea, but it does not persist the conclusions. It is less suitable when the deliverable is updated repository documentation.

Install external skills selectively and inspect their instructions before allowing package installers or scripts to modify the repository.

## Prompt for Cursor

```text
Read AGENTS.md, CONTEXT.md, README.md and all active files under docs/.

Work ONLY on the documentation branch. Do not modify application code, build
files, CI, issue states, GitHub Project fields, or pull request #89.

Use /grill-with-docs if it is installed; otherwise run the same structured
interview manually. Ask me questions one topic at a time, beginning with the
first usable product flow and owner isolation. Use the open questions in
CONTEXT.md and the review rounds in docs/documentation-review.md.

For every resolved decision:
1. Update the domain vocabulary and decision status in CONTEXT.md.
2. Synchronize PRD, requirements, user stories, ADR, tech stack, and C4 model
   when the decision affects them.
3. Keep current implementation distinct from target architecture.
4. Keep CSV, AI, notifications, dedicated frontend, AWS, API Gateway, message
   brokers, and microservices outside the first milestone unless I explicitly
   change that decision.
5. Flag unresolved questions instead of inventing answers.

Finish with a concise list of decisions made, remaining questions, documentation
changes, and suggested updates to the legacy issue audit. Do not implement the
next development step.
```

## Definition of done for this branch

- The owner understands and accepts the first product milestone.
- Current implementation and target architecture are clearly distinguished.
- No active document describes API Gateway, RabbitMQ, separate databases, or microservices as MVP architecture.
- Domain terminology and ownership rules are consistent across all active documents.
- User stories have concrete acceptance criteria.
- The ADR explains why a modular monolith was selected.
- The C4 model shows one application and one database.
- Open business decisions are explicitly listed or resolved with the owner.
- Existing GitHub issues have review recommendations, but no status has been changed without approval.
