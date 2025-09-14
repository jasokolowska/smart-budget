# 📦 Smart Budget – Tech Stack (na podstawie PRD)

## 🌐 Frontend
- Angular 18 (Standalone Components, Signals)
- Service Worker + PWA (offline mode)
- IndexedDB (lokalne przechowywanie offline)
- SwPush (obsługa Web Push)

## 🔐 Autoryzacja
- Keycloak (OpenID Connect, JWT)
- Spring Security (OAuth2 Resource Server)

## 🚪 Gateway
- Spring Cloud Gateway 4.x

## 🖥️ Backend (Modularny Monolit → Mikroserwisy)
- Kotlin 2.2.20 + JDK 21
- Spring Boot 3.4/3.5
- Spring Modulith 1.4.x (modularny monolit)

## 🗄️ Persistencja
- PostgreSQL 16
- Spring Data JPA (Hibernate 6)
- jOOQ (raporty, agregacje)
- Flyway / Liquibase (migracje)
- Jakarta Bean Validation (Hibernate Validator)

## 📥 Import danych
- Apache Commons CSV (obsługa separatora „,”)

## 🤖 AI (wsparcie budżetu)
- OpenAI Responses API (propozycje budżetu)

## 🔔 Powiadomienia
- Web Push (biblioteka webpush-java, VAPID)
- Email (Amazon SES lub lokalnie Mailpit)

## 📊 Monitoring i jakość
- Micrometer + Prometheus
- Micrometer Tracing + OpenTelemetry
- springdoc-openapi 2.x (Swagger UI)
- JUnit 5, Kotest, MockK
- Testcontainers (Postgres, Keycloak, Mailpit)

## 🛠️ DevOps / Tooling
- Gradle Kotlin DSL
- Docker / Docker Compose
- GitHub Actions
