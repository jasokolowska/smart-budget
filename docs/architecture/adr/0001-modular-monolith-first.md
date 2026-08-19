# ADR 0001: Start with a Modular Monolith

- Status: **accepted**.
- Date: 2026-08-18.
- Owner: Joanna Sokołowska.

## Context

Earlier documents proposed an API Gateway, transaction/budget/notification microservices, multiple databases, RabbitMQ, a dedicated Angular application, and AI integration before completing a basic product flow.

Development time is limited to approximately one focused hour per session. The project should demonstrate explainable Kotlin/Spring architecture and practical delivery without introducing an operational topology that has no validated product need.

## Decision

Develop Smart Budget in the existing `jasokolowska/smart-budget` repository as **one deployable Kotlin-first Spring Boot modular monolith**.

The M1 application contains three logical Spring Modulith modules:

1. `categories` — owner-scoped expense classification.
2. `expenses` — actual spending records and spending queries.
3. `budget` — monthly plans, overall/category limits, advisory warnings, and summaries.

One PostgreSQL database stores module-owned tables and uses Flyway migrations. Modules collaborate synchronously through explicit public application APIs. References between module-owned records use identifiers rather than JPA entity relationships.

Real Keycloak/OIDC authentication and owner isolation are required in M1. OpenAPI is the first demonstration interface. Angular, AWS, events, and AI enter only at their roadmap stages.

## Alternatives considered

### Independent microservices from the start

Rejected: boundaries and scaling requirements are not yet validated. Independent deployment, service security, multiple databases, broker infrastructure, and distributed testing would delay a working product.

### Maintaining two active repositories

Rejected: competing specifications and duplicate backlogs would make the canonical implementation unclear.

### Replacing the existing public repository

Rejected: the existing repository can preserve history while supporting a clean architectural direction.

## Consequences

Positive:

- One implementation, backlog, documentation baseline, and deployable unit.
- Domain boundaries can evolve before becoming network boundaries.
- Explicit Modulith verification becomes tangible portfolio evidence.
- Limited development sessions can deliver complete vertical slices.

Trade-offs:

- One database still requires strict table ownership.
- Public module contracts must remain small and deliberate.
- Architecture tests must prevent cycles and accidental internal access.
- Future extraction is optional and requires a separate evidence-based ADR.

## Verification

- Spring Modulith verifies three approved boundaries and the absence of cycles.
- Internal persistence entities/repositories are not accessed by another module.
- The core category -> budget/limit -> expense -> summary scenario works end to end.
- Automated tests prove cross-owner isolation.
- The C4 model reflects one backend and one database.

## References

- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Martin Fowler: Monolith First](https://martinfowler.com/bliki/MonolithFirst.html)
