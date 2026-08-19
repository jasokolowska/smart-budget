workspace {

    name "Budget System – C4 (MVP)"
    description "Aplikacja do zarządzania budżetem domowym - wersja MVP zgodna z PRD."

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

        emailService = softwareSystem "Email Service" {
            description "Zewnętrzny serwis do wysyłania powiadomień email (Amazon SES lub Mailpit)."
            tags "External"
        }

        system = softwareSystem "Budget System" {
            description "Modularny monolit umożliwiający import transakcji, obliczanie budżetu, zarządzanie wydatkami cyklicznymi i wysyłanie powiadomień."

            webApp = container "Frontend Angular" {
                technology "Angular 18, Standalone Components, Signals"
                description "Aplikacja uruchamiana w przeglądarce. Autoryzuje się tokenem JWT, komunikuje się z backend przez API Gateway."
            }

            gateway = container "API Gateway" {
                technology "Spring Cloud Gateway 4.x"
                description "Jedyny publiczny punkt wejścia HTTP. Weryfikuje tokeny JWT, routing do modułów backend, rate limiting."
            }

            backend = container "Backend Application" {
                technology "Spring Boot 3.4, Kotlin 2.2.20, Spring Modulith 1.4.x"
                description "Modularny monolit zawierający: Transaction Module (import CSV, CRUD, ATM-flow), Budget Module (limity, AI-generowanie, wydatki cykliczne), Notification Module (preferencje, wysyłka alertów)."
            }

            database = container "PostgreSQL Database" {
                technology "PostgreSQL 16"
                description "Jedna baza danych z osobnymi schematami: transactions (wszystkie transakcje), budgets (limity, wydatki cykliczne, preferencje powiadomień)."
            }

            ########################################################
            #                RELACJE WEWNĄTRZ SYSTEMU              #
            ########################################################

            # User interactions
            user -> webApp "Korzysta przez przeglądarkę" "HTTPS"
            webApp -> keycloak "OAuth flow - redirect do logowania" "HTTPS (OpenID Connect)"

            # Frontend to Gateway
            webApp -> gateway "Wysyła żądania API (token JWT)" "HTTPS/JSON"

            # Gateway
            gateway -> keycloak "Waliduje podpis tokenu" "OIDC Discovery (HTTPS)"
            gateway -> backend "Trasuje żądania do modułów" "HTTP"

            # Backend dependencies
            backend -> database "Czyta i zapisuje transakcje, budżety, preferencje" "JDBC/SQL (5432)"
            backend -> gpt "Wysyła zapytania o propozycje budżetu (cache 24h)" "HTTPS/OpenAI API"
            backend -> emailService "Wysyła powiadomienia email" "SMTP/HTTPS"
            backend -> webApp "Wysyła powiadomienia web push" "Web Push API (VAPID)"
        }
    }

    ############################################################
    #                       VIEWS                              #
    ############################################################
    views {

        systemContext system "context" {
            include *
            autolayout lr
            title "Budget System – Kontekst (MVP)"
            description "Użytkownik autoryzuje się przez Keycloak, aplikacja importuje dane, generuje budżet z pomocą AI i wysyła powiadomienia."
        }

        container system "containers" {
            include *
            autolayout tb
            title "Budget System – Diagram kontenerów (MVP)"
            description "Modularny monolit z jedną bazą PostgreSQL, frontend Angular i API Gateway weryfikujący JWT."
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

        theme default
    }

    configuration {
        scope softwaresystem
    }

}
