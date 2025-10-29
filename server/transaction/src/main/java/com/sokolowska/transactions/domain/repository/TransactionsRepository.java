package com.sokolowska.transactions.domain.repository;

import com.sokolowska.transactions.infra.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionEntity, Long> {}

/*

Hexagonal Architecture

module/
    ├── application/        # Application logic, use cases, services
    ├── domain/             # Core business model (entities, aggregates, domain services)
    ├── adapters/           # Implementation of ports to external systems (DB, UI, APIs)
    │    ├── inbound/       # Adapters for incoming interactions (e.g., REST controllers)
    │    └── outbound/      # Adapters for outgoing integrations (e.g., repositories, messaging)
    └── config/             # Configuration classes and wiring (DI setup)

Layered Architecture

module/
 ├── presentation/       # UI layer (controllers, views, REST API handlers)
 ├── service/            # Business logic layer (services, application logic)
 ├── domain/             # Domain model (entities, business rules)
 ├── repository/         # Data access layer (repositories, DAOs)
 └── config/             # Configuration and wiring

Feature-Layered

 module/
 ├── featureA/
 │    ├── domain/         # Domain entities and logic for feature A
 │    ├── application/    # Use cases or services for feature A
 │    ├── adapters/       # Adapters (UI, DB) specific to feature A
 ├── featureB/
 │    ├── domain/
 │    ├── application/
 │    ├── adapters/
 └── common/               # Shared utilities, configs

Clean Architecture Project Structure

module/
 ├── domain/              # Entities, business rules, domain services (core)
 ├── usecases/            # Application-specific business rules, interactors
 ├── interface_adapters/  # Adapters converting data between layers
 │    ├── controllers/    # Web controllers, REST endpoints
 │    └── gateways/       # Repository interfaces, presenters
 ├── frameworks_drivers/  # External frameworks, DB, UI, tools
 │    ├── persistence/    # Repository implementations (e.g., Ktorm)
 │    ├── web/            # Web framework-specific code
 └── config/              # Dependency injection, app configuration


Onion Architecture Project Structure

module/
 ├── core/                # Domain entities and interfaces (domain layer)
 ├── service/             # Domain services, business logic
 ├── infrastructure/      # Implementation of external concerns (DB, messaging)
 │    ├── repository/     # Repository implementations (e.g., Ktorm)
 │    ├── entities/       # Entities coupled to infrastructure concerns
 ├── api/                 # Web controllers or API endpoints
 └── config/              # Wiring and configuration

 */
