# Smart Budget

## 1. Cel produktu
Umożliwić szybkie przygotowanie miesięcznego budżetu domowego na komputerze / tablecie, a docelowo w mobilce, poprzez:
* **import plików CSV** z konta bankowego (ręcznie lub przez Make),
* inteligentne **kategoryzowanie transakcji** (słownik + interaktywny flow dla wypłat z bankomatu),
* **automatyczne – wspomagane przez AI – generowanie planu budżetu** (uwzględnia historię, sezonowość, wpływy),
* zarządzanie **wydatkami cyklicznymi** i podgląd **aktualnego salda**.

## 2. Persony
| Persona | Potrzeba | Jak aplikacja pomaga |
|---------|----------|----------------------|
| Adam (30 l.) – analityk | Chce zautomatyzować import wyciągów | Konfiguruje w Make scenariusz „bank → webhook”, CSV wpada samo |
| Ola (27 l.) – freelancerka | Ma dużo gotówki; zapomina, na co poszła | ATM-flow pyta o opis i kategorię |
| Magda (35 l.) – mama dwójki | Opłaty stałe (czynsz, Netflix, prąd) rozjeżdżają się w kalendarzu | Dodaje „Czynsz 1-go każdego miesiąca”, a apka przypomina i odznacza opłacenie |

## 3. Zakres MVP
### 3.1 Import i zapis wydatków
- **CSV upload** (ręczny) — `date,amount,description[,category]`
- **Webhook „Make”** – użytkownik generuje `https://app.smartbudget.io/api/webhooks/{userToken}`
- Parser `,` / `;`, kategoryzacja słownikowa + ATM-flow

### 3.2 Ręczne dodawanie
- **Pojedynczy wydatek** — data, kwota, opis, kategoria
- **Wydatek cykliczny**
    - Pola: kwota, opis, kategoria, **częstotliwość** (miesięczna / tygodniowa / roczna / custom), **data następnego terminu**
    - System tworzy instancję „do zapłaty” w każdym cyklu
    - Użytkownik **odznacza „Zapłacone”**; wtedy pojawia się kolejny termin

### 3.3 Generator budżetu (AI + heurystyka)
1. Średnia z ostatnich **3 miesięcy** per kategoria
2. Endpoint `POST /budget/ai-proposal` – GPT-4o dopasowuje limity do sezonu/świąt + przyszłych **wydatków cyklicznych**
3. Użytkownik może edytować i zapisać

### 3.4 Widoki
- **Lista wydatków** (filtrowanie: data, kategoria, tekst)
- **Budżet miesięczny** (progress-bar + AI-rationale)
- **Dashboard startowy**
    - **Bieżące saldo** = *saldo otwarcia miesiąca* + *wpływy* − *wydatki* (w tym zapłacone cykliczne)
    - Sekcja „Nadchodzące wydatki cykliczne” (kolejne 7 dni)

### 3.5 Dostęp i przechowywanie
- Tryb Gość (anon UUID w IndexedDB) + opcjonalne OAuth Google
- **PWA offline** – pełna funkcjonalność bez sieci (SW + IndexedDB)

## 4. Poza MVP (Backlog)
Eksport CSV/PDF, multi-currency, konto współdzielone, zaawansowane wykresy

## 5. Wymagania niefunkcjonalne
| Wymaganie                    | Metryka / cel                                   |
|------------------------------|-------------------------------------------------|
| Czas API                     | < 300 ms p95                                    |
| LCP (mobile 4G)              | < 1 s                                           |
| Pokrycie testami             | ≥ 60 %                                          |
| Dostępność                   | WCAG AA                                         |
| Uptime (prod free-tier)      | 99 %                                            |
| FE bundle                    | < 250 kB GZIP                                   |
| Webhook security             | Token ≥ 32 znaków                               |
| AI-koszt                     | ≤ 0,02 USD/budżet (wynik cache 24 h)            |

## 6. Definicja sukcesu MVP
* Aplikacja online do **31 lipca**
* Co najmniej **3 testerów**:
    - konfigurują Make **lub** wgrywają ≥ 2 CSV,
    - tworzą AI-budżet,
    - dodają ≥ 1 wydatek cykliczny i odznaczają „Zapłacone”,
    - dopisują ≥ 3 wydatki ręczne,
    - uzupełniają ATM-flow
* Średnia **≥ 8/10** w ankiecie („Czy przygotowanie budżetu jest szybsze niż dotychczas?”)
