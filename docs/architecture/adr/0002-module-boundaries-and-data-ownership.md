# ADR 0002: Explicit Module Boundaries and Data Ownership

- Status: **accepted**.
- Date: 2026-08-19.
- Owner: Joanna Sokołowska.

## Context

A modular monolith can degrade into tightly coupled code if modules share repositories, JPA entities, and unrestricted table access. Smart Budget is intended to teach and demonstrate real domain boundaries.

## Decision

The initial logical modules are:

- `categories`: owner-scoped category lifecycle and classification.
- `expenses`: actual spending records, owner/category validation, and spending queries.
- `budget`: budget months, optional overall limits, category limits, advisory warnings, and summaries.

Allowed dependency direction:

- `expenses -> categories`
- `budget -> categories`
- `budget -> expenses`

All cross-module calls go through intentionally public application contracts.

Each module owns its database tables, repositories, persistence mappings, and internal classes. Cross-module records reference each other by identifier; no JPA `@ManyToOne`, `@OneToMany`, or repository sharing crosses a module boundary.

Initial interactions are synchronous. A fourth reporting module, external broker, and separate deployment are not required.

## Consequences

- Summary composition belongs to `budget`, which reads public spending information from `expenses`.
- Foreign-category validation calls the public `categories` contract.
- Owner identity remains explicit in every cross-module call.
- Existing Java/build-subproject boundaries may need gradual alignment but must not be confused with Modulith application modules.
- Spring Modulith verification detects cycles and illegal internal dependencies.

## References

- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Spring Modulith testing](https://docs.spring.io/spring-modulith/reference/testing.html)
