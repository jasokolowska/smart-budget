# Product Requirements Document: Smart Budget

Status: **draft for owner review**. This document supersedes the archived microservice-era PRD.

## Product vision

Smart Budget helps an individual or household understand whether spending within a selected category still fits the monthly plan. The first usable version focuses on a short, trustworthy budgeting workflow rather than automation, AI, or a full consumer-facing interface.

The repository also functions as a public engineering portfolio. Its implementation should demonstrate sound domain modeling, explicit architectural boundaries, secure ownership, automated verification, and decisions that can be explained during a technical interview.

## Primary user

An authenticated person who wants to define personal spending categories, plan category-level monthly limits, record expenses, and check the current state of a selected budget month.

Shared household accounts, multiple budget owners, guest mode, and organization/team accounts are not part of the first milestone.

## Problem statement

A manually maintained spreadsheet can show a plan, but keeping category limits and actual expenses synchronized is repetitive and error-prone. The first version should answer one practical question accurately:

> How much have I already spent in this category this month, and how much of my planned limit remains?

## First milestone: minimum viable vertical slice

The first milestone is complete when one authenticated owner can:

1. Create an expense category.
2. Set a spending limit for that category and a selected calendar month.
3. Record an expense assigned to that category.
4. Retrieve the category's monthly limit, total spending, and remaining amount.
5. Access only their own categories, limits, expenses, and summaries.

An API documented with OpenAPI may be sufficient for this milestone; a dedicated frontend is not automatically required.

## Explicitly outside the first milestone

- Bank integrations, webhooks, and CSV import.
- Automatic classification, ATM-specific workflows, and expense splitting.
- AI-generated budget suggestions and LLM integrations.
- Recurring expenses, scheduled jobs, reminders, email, and web push.
- Angular or another dedicated frontend, offline support, and PWA features.
- Shared budgets, multiple currencies, exports, analytics, and advanced dashboards.
- AWS deployment, Terraform, message brokers, API Gateway, and microservices.

These ideas remain possible future extensions. Their exclusion is a scope decision, not a claim that they are permanently undesirable.

## Product principles

- Deliver one complete owner-visible workflow before adding breadth.
- Favor clear domain language over infrastructure-driven naming.
- Make ownership isolation a product requirement, not an afterthought.
- Keep implementation small enough to advance in approximately one-hour sessions.
- Record why architectural choices were made and which alternatives were rejected.
- Add infrastructure only when it solves a demonstrated product or operational problem.

## Success criteria

The first milestone succeeds when:

- The complete category -> limit -> expense -> summary scenario can be demonstrated through documented API calls.
- Calculations are correct for the selected owner, category, and month.
- Automated tests prove that one owner cannot read or modify another owner's data.
- Architectural boundaries are understandable and verified once Spring Modulith is introduced.
- The repository clearly distinguishes working functionality from planned capabilities.

The legacy metrics involving AI acceptance rate, CSV imports, notifications, or a fixed historical deadline are no longer MVP acceptance criteria.

## Decisions awaiting confirmation

Business rules that remain unresolved are listed in [`../CONTEXT.md`](../CONTEXT.md). They should be resolved through a short owner interview before being promoted to acceptance criteria.

## Related documents

- [`requirements.md`](requirements.md)
- [`user_stories.md`](user_stories.md)
- [`tech-stack.md`](tech-stack.md)
- [`architecture/adr/0001-modular-monolith-first.md`](architecture/adr/0001-modular-monolith-first.md)
