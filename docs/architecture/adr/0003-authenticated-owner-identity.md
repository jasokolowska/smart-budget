# ADR 0003: Derive Owner Identity from a Trusted OIDC Principal

- Status: **accepted**.
- Date: 2026-08-19.
- Owner: Joanna Sokołowska.

## Context

A budgeting system contains private financial data. Historical implementation ideas accepted a user identifier in a request, which can enable cross-owner access when the server trusts that value.

The first milestone must demonstrate real authentication and reproducible isolation between two owners.

## Decision

- Keycloak acts as the local OIDC identity provider.
- Smart Budget validates bearer tokens using Spring Security OAuth2 Resource Server.
- Domain owner identity is derived from trusted issuer and stable subject claims.
- Request bodies and query parameters never define the authoritative owner.
- Two preconfigured demonstration users provide repeatable positive and negative security scenarios.
- Every category, budget, limit, expense, list query, and summary is owner scoped.
- Domain modules receive an application-level owner representation rather than Keycloak implementation types.
- Public self-registration and household sharing remain out of scope for M1.

## Consequences

- The identity provider remains replaceable by another standards-compliant OIDC provider.
- Authentication is part of the first milestone, not postponed as production hardening.
- Cross-owner category references, list queries, and summary queries are covered by integration tests.
- Local Docker Compose includes Keycloak and sanitized configuration.
- No secrets, access tokens, or local `.env` files are committed.

## References

- [Spring Security JWT Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
- [Keycloak documentation](https://www.keycloak.org/documentation)
