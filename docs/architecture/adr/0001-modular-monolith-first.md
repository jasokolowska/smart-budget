# ADR 0001: Start with a Modular Monolith

- Status: **accepted architectural direction; detailed module boundaries pending review**.
- Date: 2026-08-18.
- Owner: Joanna Sokołowska.

## Context

Earlier Smart Budget documents proposed an API Gateway, separate transaction/budget/notification services, multiple PostgreSQL databases, RabbitMQ, a dedicated Angular frontend, AI integration, and several additional infrastructure components.

The repository does not yet contain a complete working budgeting flow. Development time is approximately one focused hour per session. The project should demonstrate deliberate backend architecture and an understanding of domain boundaries, not unnecessary operational complexity.

The existing public repository already contains useful project history, a Java 21/Spring Boot/Gradle baseline, and an incomplete category-related pull request. A second active repository would create competing documentation, duplicate backlogs, and ambiguity about the canonical implementation.

## Decision

Smart Budget will be developed as **one deployable modular monolith in the existing `jasokolowska/smart-budget` repository**.

The intended application is Kotlin-first, uses Spring Boot and Spring Modulith, and stores data in one PostgreSQL database managed through Flyway migrations. Logical domain boundaries will initially be explored around categories, budgeting, transactions, and reporting.

Modules should communicate through explicit public APIs. Straightforward synchronous collaboration is acceptable for the first milestone. Domain events may be introduced later where they provide a specific, documented benefit; an external message broker is not a prerequisite.

## Alternatives considered

### Independent microservices from the start

Rejected for the current phase because service boundaries are not validated, the user-facing workflow is incomplete, and separate deployment, databases, messaging, security, and integration testing would consume limited delivery time.

### Two simultaneously maintained repositories

Rejected because both repositories would describe the same product while splitting documentation, issues, implementation effort, and portfolio narrative.

### Completely new repository

Rejected because the existing repository can preserve project history while supporting a clean documentation and implementation restart from `main`.

## Consequences

Positive:

- One current product specification, backlog, repository, and deployment unit.
- Lower operational overhead and faster feedback.
- Domain boundaries can be adjusted before becoming network/service boundaries.
- Architectural decisions, module tests, and the eventual extraction rationale become strong portfolio material.

Trade-offs:

- Module boundaries still require active enforcement and must not degrade into a shared-internals monolith.
- One database requires clear logical data ownership and carefully reviewed cross-module dependencies.
- Existing microservice-oriented documents and GitHub issues must be reviewed and reclassified.
- A future move to microservices would require a separate, evidence-based decision; it is not an automatic roadmap obligation.

## Verification once implemented

- Spring Modulith verifies the absence of cyclic module dependencies.
- Internal module classes are not referenced across module boundaries.
- Individual modules have focused integration tests where appropriate.
- The first category -> limit -> expense -> summary flow runs end to end.
- Cross-owner access is rejected and covered by automated tests.

## References

- [Spring Modulith fundamentals](https://docs.spring.io/spring-modulith/reference/fundamentals.html)
- [Spring Modulith architecture verification](https://docs.spring.io/spring-modulith/reference/verification.html)
- [Martin Fowler: Monolith First](https://martinfowler.com/bliki/MonolithFirst.html)
