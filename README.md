# Smart Budget

Smart Budget is a personal-budgeting application and public engineering portfolio focused on Kotlin, Spring, modular architecture, secure ownership, and practical AWS delivery.

> **Current status:** product and architecture decisions are approved; the implementation on `main` is still an early-stage baseline. Planned capabilities must not be described as already delivered.

## Current implementation and approved target

| Area | Verified on `main` | Approved direction |
| --- | --- | --- |
| Runtime and build | Java 21, Kotlin 2.2, Gradle Kotlin DSL, and one `app` subproject | Add the M1 product capabilities to the Kotlin-first Spring Boot application |
| Architecture | One deployable application with verified `categories`, `budget`, and `expenses` Spring Modulith boundaries | Implement each module through its explicit public API |
| Persistence | Not consistently established on `main` | One PostgreSQL database, Flyway, and module-owned tables |
| Authentication | Not yet implemented on `main` | Keycloak OIDC, Spring Security OAuth2 Resource Server, and two demo users |
| Quality | Existing Gradle build and GitHub Actions workflow | Unit, integration, owner-isolation, and Spring Modulith architecture tests |
| Deployment | No approved cloud implementation | Local Docker Compose first; AWS and Terraform after the core product is useful |
| User interface | No product frontend | OpenAPI for the first milestones; a focused Angular UI after AWS deployment |

## First complete product milestone

An authenticated owner can:

1. Create a personal expense category.
2. Set an optional overall monthly budget and category-level monthly limits.
3. Record a positive PLN expense with an owner-owned category, actual expense date, and optional description.
4. List expenses for a selected month, optionally filtered by category, using pagination.
5. Retrieve a monthly summary containing category-level and overall spending, remaining amounts, and non-blocking planning warnings.
6. Demonstrate that a second authenticated owner cannot access another owner's data.

A category without a limit can still receive expenses. Categories with limits but no expenses appear in the summary. All limits are planning signals: overspending and over-allocation produce warnings or negative remaining amounts, not rejected writes.

CSV, AI, subcategories, income, recurring expenses, Angular, AWS, messaging infrastructure, and microservices are outside the first milestone.

## Roadmap

| Milestone | Deliverable |
| --- | --- |
| M0 | Approved documentation, architecture model, roadmap, and aligned issue backlog |
| M1 | Secure Kotlin/Spring modular-monolith budgeting workflow demonstrated through OpenAPI |
| M2 | Product completeness: lifecycle actions, subcategories, income, opening balance, and reusable monthly planning |
| M3 | Cost-aware AWS deployment, Terraform, basic observability, and an explicit hosting ADR |
| M4 | Focused Angular application for the deployed budgeting API |
| M5 | CSV import with reporting, owner isolation, and a justified AWS/S3 integration |
| M6 | Recurring, planned, and periodic expenses |
| M7 | AI-assisted transaction categorization |
| M8 | Human-approved, AI-assisted budget planning |
| M9 | Optional evidence-led operational or architectural experiments |

See the full [delivery roadmap](docs/roadmap.md) for scope, dependencies, acceptance criteria, and learning outcomes.

## Documentation

- [Domain context and approved product decisions](CONTEXT.md)
- [Product requirements](docs/prd.md)
- [Functional and non-functional requirements](docs/requirements.md)
- [User stories and acceptance scenarios](docs/user_stories.md)
- [Milestones and delivery roadmap](docs/roadmap.md)
- [Technology stack and decision status](docs/tech-stack.md)
- [Architecture decision: modular monolith first](docs/architecture/adr/0001-modular-monolith-first.md)
- [Architecture decision: explicit module ownership](docs/architecture/adr/0002-module-boundaries-and-data-ownership.md)
- [Architecture decision: authenticated owner identity](docs/architecture/adr/0003-authenticated-owner-identity.md)
- [Target C4 architecture model](docs/architecture/workspace.dsl)
- [Issue audit and roadmap alignment](docs/backlog-audit.md)
- [Agent instructions](AGENTS.md)
- [Historical microservice-era material](docs/archive/2025-microservices/README.md)

## Build

Run commands from `server/` with the existing wrapper:

```bash
./gradlew build
./gradlew test
./gradlew :app:bootRun
```

On Windows use `gradlew.bat`. The build verifies the three logical application modules; it does not yet claim that the approved M1 product behavior is implemented.

## Local Docker Compose environment

The local stack contains one Smart Budget application, one PostgreSQL database for
application data, and Keycloak with a preconfigured `smart-budget` realm. RabbitMQ,
Mailpit, and additional application databases are not required.

The committed defaults are demonstration values only. To override them, copy the
sanitized example and keep the resulting `.env` file local:

```bash
cp .env.example .env
docker compose up --build --detach
docker compose ps
```

On PowerShell, use `Copy-Item .env.example .env` for the first command. The services
are available at:

- application health: <http://localhost:8080/actuator/health>
- Keycloak: <http://localhost:8081>
- PostgreSQL: `localhost:5432`

Keycloak imports two enabled demonstration owners on the first startup:

| Username | Default local password |
| --- | --- |
| `owner-one` | `change-me-owner-one` |
| `owner-two` | `change-me-owner-two` |

Obtain an access token for either owner using the local-only direct grant.

On Bash:

```bash
curl --fail --request POST \
  http://localhost:8081/realms/smart-budget/protocol/openid-connect/token \
  --header "Content-Type: application/x-www-form-urlencoded" \
  --data-urlencode "client_id=smart-budget-api" \
  --data-urlencode "grant_type=password" \
  --data-urlencode "username=owner-one" \
  --data-urlencode "password=change-me-owner-one"
```

On Windows PowerShell, `curl` is commonly an alias for `Invoke-WebRequest`, so use
PowerShell's native request command:

```powershell
$tokenResponse = Invoke-RestMethod `
  -Method Post `
  -Uri "http://localhost:8081/realms/smart-budget/protocol/openid-connect/token" `
  -ContentType "application/x-www-form-urlencoded" `
  -Body @{
    client_id  = "smart-budget-api"
    grant_type = "password"
    username   = "owner-one"
    password   = "change-me-owner-one"
  }

$tokenResponse.access_token
```

Verify the complete local realm contract without printing or saving tokens:

```powershell
.\infra\keycloak\verify-realm.ps1
```

The verification checks OIDC discovery and JWKS availability, authenticates both
demo owners, and confirms the expected issuer, API audience, usernames, and distinct
stable subject identifiers. If `.env` overrides the demo passwords, export matching
`DEMO_OWNER_ONE_PASSWORD` and `DEMO_OWNER_TWO_PASSWORD` environment variables before
running the script.

If `.env` overrides a demo password, use the overridden value in the request. Realm
import is intentionally idempotent and does not replace an existing realm. To reset
all local PostgreSQL and Keycloak state and re-import the realm, run
`docker compose down --volumes`, then start the stack again. This removes local
container data.

Stop the stack without removing its data with `docker compose down`. Do not commit
the generated `.env` file, tokens, or non-demonstration credentials.

## Working agreements

- Maintain one active repository and one approved product specification.
- Deliver reviewable vertical slices suitable for focused one-hour work sessions.
- Derive ownership from the authenticated principal, never from client-supplied owner identifiers.
- Communicate between modules through explicit public contracts.
- Record consequential decisions and deployment trade-offs in ADRs.
- Never commit local `.env` files or real credentials.

## License

Distributed under the [MIT License](LICENSE).
