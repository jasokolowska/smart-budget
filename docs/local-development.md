# Local development

This document provides the detailed local setup for the Smart Budget application baseline. Running the services does **not** mean the full budgeting API, persistence, or JWT validation has been implemented.

## Build and test

From `server/`:

```bash
./gradlew build
./gradlew test
./gradlew :app:bootRun
```

On Windows, use `gradlew.bat`. The existing tests verify the baseline, including Spring Modulith boundaries; they do not prove that the full M1 feature set works.

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

## Related documentation

- [Project README](../README.md)
- [Technology stack and implementation status](tech-stack.md)
- [Requirements](requirements.md)
