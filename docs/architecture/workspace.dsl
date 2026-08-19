workspace "Smart Budget - Approved Modular Monolith" "Approved architecture for the owner-isolated personal budgeting workflow." {

    model {
        owner = person "Budget Owner" "Authenticated individual managing personal categories, budget plans, and actual expenses."
        identityProvider = softwareSystem "Keycloak" "OIDC identity provider authenticating demonstration owners and issuing access tokens." "External Identity Provider"

        smartBudget = softwareSystem "Smart Budget" "Personal budgeting application implemented as one modular monolith." {
            application = container "Smart Budget Backend" "One deployable backend exposing a documented budgeting API." "Kotlin, Spring Boot, Spring Modulith" {
                categories = component "Categories Module" "Owns personal category identity, naming rules, and later lifecycle/hierarchy." "Spring Modulith application module"
                expenses = component "Expenses Module" "Owns owner-scoped actual expenses, pagination, category filters, and spending queries." "Spring Modulith application module"
                budget = component "Budget Module" "Owns monthly plans, advisory overall/category limits, warnings, and monthly summaries." "Spring Modulith application module"
            }

            database = container "Application Database" "One PostgreSQL database containing separately owned module tables." "PostgreSQL" {
                tags "Database"
            }

            owner -> identityProvider "Authenticates" "OIDC / HTTPS"
            owner -> application "Manages categories, plans limits, records expenses, and reads summaries" "HTTPS / JSON / OpenAPI"
            application -> identityProvider "Discovers issuer metadata and signing keys; validates access tokens" "OIDC / HTTPS"
            application -> database "Reads and writes owner-scoped records in module-owned tables" "SQL"

            expenses -> categories "Validates owned categories through the public module API"
            budget -> categories "Validates category ownership through the public module API"
            budget -> expenses "Reads owner-scoped monthly spending through the public module API"
        }
    }

    views {
        systemContext smartBudget "system-context" {
            include *
            autolayout lr
            title "Smart Budget - Approved System Context"
        }

        container smartBudget "containers" {
            include *
            autolayout tb
            title "Smart Budget - One Backend, One Database, External Identity Provider"
        }

        component application "application-modules" {
            include *
            autolayout tb
            title "Smart Budget - Categories, Expenses, and Budget Modules"
        }

        styles {
            element "Person" {
                shape "person"
                background "#08427b"
                color "#ffffff"
            }

            element "Software System" {
                background "#1168bd"
                color "#ffffff"
            }

            element "Container" {
                shape "roundedbox"
                background "#438dd5"
                color "#ffffff"
            }

            element "Component" {
                shape "roundedbox"
                background "#85bbf0"
                color "#0b1f33"
            }

            element "Database" {
                shape "cylinder"
            }
        }
    }

    configuration {
        scope softwaresystem
    }
}
