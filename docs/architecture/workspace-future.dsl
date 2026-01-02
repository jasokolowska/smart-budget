workspace {

    name "Budget System – C4"
    description "Aplikacja do zarządzania budżetem domowym."

    ############################################################
    #                       MODEL                              #
    ############################################################
    model {

        user = person "User" {
            description "Osoba korzystająca z aplikacji webowej, która importuje pliki CSV z transakcjami i śledzi swoje limity budżetowe."
        }

        keycloak = softwareSystem "Keycloak" {
            description "Zewnętrzny serwer uwierzytelniania OpenID Connect, wydaje podpisane tokeny JWT."
            tags "External"
        }

        gpt = softwareSystem "OpenAI GPT-4o" {
            description "LLM generujący propozycje budżetu na podstawie historii transakcji i sezonowości."
            tags "External"
        }

        externalIntegrations = softwareSystem "External Integrations" {
            description "Zewnętrzne systemy wysyłające dane przez webhook (Make.com, banki, inne automatyzacje)."
            tags "External"
        }

        emailService = softwareSystem "Email Service" {
            description "Zewnętrzny serwis do wysyłania powiadomień email (np. SendGrid, SMTP)."
            tags "External"
        }

        system = softwareSystem "Budget System" {
            description "Zestaw mikroserwisów umożliwiających import transakcji, obliczanie budżetu, zarządzanie wydatkami cyklicznymi i wysyłanie powiadomień."

            webApp = container "Frontend Angular" {
                technology "Angular 17, Standalone Components, PWA"
                description "Aplikacja uruchamiana w przeglądarce. Autoryzuje się tokenem JWT, obsługuje offline-mode przez IndexedDB."
            }

            gateway = container "API Gateway" {
                technology "Spring Cloud Gateway"
                description "Jedyny publiczny punkt wejścia HTTP. Weryfikuje tokeny, routing, rate limiting, proxy dla OpenAI API z cache 24h."
            }

            transactionSvc = container "Transaction Service" {
                technology "Spring Boot 3.3, Kotlin, WebFlux"
                description "Import CSV, ręczny CRUD transakcji, ATM-flow dla wypłat gotówkowych, publikowanie zdarzeń."
            }

            budgetSvc = container "Budget Service" {
                technology "Ktor (lekki serwis Kotlin)"
                description "Miesięczne limity, procent wykorzystania, wydatki cykliczne, AI-generowanie budżetu."
            }

            notificationSvc = container "Notification Service" {
                technology "Spring Boot, Coroutine listener"
                description "Zarządzanie preferencji powiadomień (HTTP) + event-driven wysyłanie alertów (AMQP)."
            }

            broker = container "RabbitMQ" {
                technology "RabbitMQ (AMQP 0-9-1)"
                description "Broker zdarzeń asynchronicznych, buforuje komunikaty między mikroserwisami."
            }

            transactions = container "Transactions DB" {
                technology "PostgreSQL 16"
                description "Schemat przechowujący wszystkie transakcje użytkowników."
            }

            budgets = container "Budgets DB" {
                technology "PostgreSQL 16"
                description "Limity budżetowe, wydatki cykliczne, historia wykorzystania, preferencje użytkowników."
            }

            ########################################################
            #                RELACJE WEWNĄTRZ SYSTEMU             #
            ########################################################

            # User interactions
            user -> webApp "Korzysta przez przeglądarkę" "HTTPS"

            # Frontend to Gateway
            webApp -> gateway "Wysyła żądania API (token JWT)" "HTTPS"

            # External systems
            externalIntegrations -> gateway "Wysyłają dane przez webhook" "HTTPS POST"
            gateway -> keycloak "Waliduje podpis tokenu" "OIDC Discovery (HTTPS)"
            gateway -> gpt "Proxy dla AI requests + cache" "HTTPS/OpenAI API"
            notificationSvc -> emailService "Wysyła powiadomienia email" "SMTP/HTTPS"

            # Gateway routing
            gateway -> transactionSvc "Trasuje /api/transactions/*" "HTTP"
            gateway -> budgetSvc "Trasuje /api/budgets/*, /api/recurring/*" "HTTP"
            gateway -> notificationSvc "Trasuje /api/notifications/*" "HTTP"

            # Database connections
            transactionSvc -> transactions "Czyta i zapisuje transakcje" "SQL (5432)"
            budgetSvc -> budgets "Czyta i zapisuje limity, wydatki cykliczne, preferencje" "SQL (5432)"
            notificationSvc -> budgets "Czyta preferencje powiadomień użytkowników" "SQL (5432)"

            # Event-driven communication
            transactionSvc -> broker "Publikuje TRANSACTION_CREATED, ATM_WITHDRAWAL_DETECTED" "AMQP"
            budgetSvc -> broker "Subskrybuje transakcje + publikuje BUDGET_LIMIT_EXCEEDED, RECURRING_DUE" "AMQP"
            notificationSvc -> broker "Subskrybuje wszystkie zdarzenia powiadomień" "AMQP"
        }

        ########################################################
        #                RELACJE ZEWNĘTRZNE                    #
        ########################################################

        webApp -> keycloak "OAuth flow - redirect do logowania" "HTTPS"
    }

    ############################################################
    #                       VIEWS                              #
    ############################################################
    views {

        systemContext system "context" {
            include *
            autolayout lr
            title "Budget System – Kontext"
        }

        container system "containers" {
            include *
            autolayout tb
            title "Budget System – Diagram kontenerów"
        }

        styles {
            element "Person" {
                shape "person"
                background "#08427b"
                color "#ffffff"
            }
            element "Container" {
                shape "roundedbox"
                background "#438dd5"
                color "#ffffff"
            }
            element "SoftwareSystem" {
                shape "hexagon"
                background "#852a0f"
                color "#ffffff"
            }
            element "External" {
                background "#6b2c91"
                color "#ffffff"
            }
        }
    }

    configuration {
        scope softwaresystem
    }

}