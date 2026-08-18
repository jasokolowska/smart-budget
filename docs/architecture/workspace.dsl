workspace "Smart Budget - Target Modular Monolith" "Target architecture for the first usable budgeting workflow." {

    model {
        owner = person "Budget Owner" "An authenticated person who manages personal categories, monthly limits, and expenses."

        smartBudget = softwareSystem "Smart Budget" "A personal-budgeting application implemented as one modular monolith." {
            application = container "Smart Budget Application" "One deployable backend exposing the budgeting API." "Kotlin, Spring Boot, Spring Modulith" {
                categories = component "Categories Module" "Owns category lifecycle and owner-specific category rules." "Spring Modulith application module"
                budgeting = component "Budgeting Module" "Owns budget months and category-level monthly limits." "Spring Modulith application module"
                transactions = component "Transactions Module" "Owns recorded expenses and their retrieval." "Spring Modulith application module"
                reporting = component "Reporting Module" "Combines public module data into monthly spent/remaining summaries." "Spring Modulith application module"
            }

            database = container "Application Database" "Stores owner-scoped categories, monthly limits, and expenses." "PostgreSQL" {
                tags "Database"
            }

            owner -> application "Creates categories and limits, records expenses, and reads monthly summaries" "HTTPS / JSON"
            application -> database "Reads and writes owner-scoped application data" "SQL"

            budgeting -> categories "Validates owner-owned categories through the public module API"
            transactions -> categories "Validates owner-owned categories through the public module API"
            reporting -> budgeting "Reads monthly category limits through the public module API"
            reporting -> transactions "Reads category spending through the public module API"
        }
    }

    views {
        systemContext smartBudget "system-context" {
            include *
            autolayout lr
            title "Smart Budget - Target System Context"
        }

        container smartBudget "containers" {
            include *
            autolayout tb
            title "Smart Budget - One Application, One Database"
        }

        component application "application-modules" {
            include *
            autolayout tb
            title "Smart Budget - Provisional Application Module Boundaries"
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
