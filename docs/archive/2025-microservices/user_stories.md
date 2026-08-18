# Happy Paths - User Stories dla Ticketów

## 🔐 **Autoryzacja i Uwierzytelnianie**

### HP-001: Logowanie użytkownika
**Jako** użytkownik  
**Chcę** zalogować się do aplikacji  
**Aby** móc zarządzać swoim budżetem  

**Ścieżka:**
1. User otwiera WebApp
2. WebApp wykrywa brak tokenu JWT
3. User klika "Zaloguj się"
4. WebApp przekierowuje do Keycloak (OAuth)
5. User loguje się w Keycloak
6. Keycloak zwraca kod autoryzacyjny do WebApp
7. WebApp otrzymuje JWT token
8. WebApp zapisuje token i przekierowuje do dashboardu

### HP-002: Weryfikacja tokenu
**Jako** system  
**Chcę** weryfikować każde żądanie API  
**Aby** zapewnić bezpieczeństwo danych  

**Ścieżka:**
1. WebApp wysyła żądanie z JWT tokenem do API Gateway
2. API Gateway weryfikuje podpis tokenu w Keycloak
3. Keycloak potwierdza ważność tokenu
4. API Gateway przekazuje żądanie do odpowiedniego serwisu

---

## 📥 **Import i Dodawanie Transakcji**

### HP-003: Ręczny upload CSV
**Jako** użytkownik  
**Chcę** wgrać plik CSV z transakcjami  
**Aby** szybko zaimportować dane z banku  

**Ścieżka:**
1. User wybiera plik CSV w WebApp
2. WebApp wysyła plik do API Gateway
3. API Gateway przekazuje do Transaction Service
4. Transaction Service parsuje CSV i kategoryzuje transakcje
5. Transaction Service zapisuje dane do Transactions DB
6. Transaction Service publikuje TRANSACTION_CREATED do RabbitMQ
7. WebApp wyświetla potwierdzenie i listę zaimportowanych transakcji

### HP-004: Webhook import (automatyzacja)
**Jako** system automatyzacji  
**Chcę** wysłać dane transakcji przez webhook  
**Aby** automatycznie importować dane do budżetu  

**Ścieżka:**
1. External Integration (np. Make.com) wysyła POST z danymi CSV
2. API Gateway weryfikuje webhook signature
3. API Gateway przekazuje dane do Transaction Service
4. Transaction Service przetwarza i zapisuje transakcje
5. Transaction Service publikuje TRANSACTION_CREATED do RabbitMQ
6. System zwraca status 200 OK

### HP-005: Ręczne dodanie transakcji
**Jako** użytkownik  
**Chcę** dodać transakcję ręcznie  
**Aby** uzupełnić brakujące wydatki  

**Ścieżka:**
1. User wypełnia formularz transakcji w WebApp
2. WebApp wysyła dane do API Gateway → Transaction Service
3. Transaction Service waliduje i zapisuje transakcję
4. Transaction Service publikuje TRANSACTION_CREATED
5. WebApp wyświetla potwierdenie i odświeża listę

### HP-006: ATM Flow - kategoryzacja wypłat
**Jako** użytkownik  
**Chcę** sklasyfikować wypłatę z bankomatu  
**Aby** dokładnie śledzić na co wydałem gotówkę  

**Ścieżka:**
1. Transaction Service wykrywa wypłatę z bankomatu w CSV
2. Transaction Service publikuje ATM_WITHDRAWAL_DETECTED
3. WebApp wyświetla modal z pytaniem o kategorię
4. User wybiera kategorię i opcjonalnie dzieli kwotę
5. WebApp wysyła aktualizację do Transaction Service
6. Transaction Service aktualizuje transakcję i publikuje event

---

## 💰 **Zarządzanie Budżetem**

### HP-007: Generowanie budżetu AI
**Jako** użytkownik  
**Chcę** wygenerować budżet przy pomocy AI  
**Aby** otrzymać inteligentne propozycje limitów  

**Ścieżka:**
1. User klika "Generuj budżet AI" w WebApp
2. WebApp wysyła żądanie do API Gateway → Budget Service
3. Budget Service pobiera historię transakcji z ostatnich 3 miesięcy
4. Budget Service wysyła zapytanie przez API Gateway do OpenAI GPT-4o
5. API Gateway cache'uje odpowiedź na 24h i zwraca propozycje
6. Budget Service przetwarza odpowiedź AI i przygotowuje budżet
7. WebApp wyświetla propozycje budżetu do akceptacji

### HP-008: Edycja i zapis budżetu
**Jako** użytkownik  
**Chcę** edytować zaproponowany budżet  
**Aby** dostosować limity do moich potrzeb  

**Ścieżka:**
1. User modyfikuje limity kategorii w WebApp
2. WebApp wysyła aktualizacje do API Gateway → Budget Service
3. Budget Service waliduje i zapisuje budżet do Budgets DB
4. Budget Service publikuje BUDGET_UPDATED
5. WebApp wyświetla potwierdzenie i odświeża widok budżetu

### HP-009: Monitorowanie wykorzystania budżetu
**Jako** użytkownik  
**Chcę** widzieć aktualne wykorzystanie budżetu  
**Aby** kontrolować swoje wydatki  

**Ścieżka:**
1. Budget Service subskrybuje TRANSACTION_CREATED z RabbitMQ
2. Budget Service przelicza wykorzystanie budżetu
3. Budget Service aktualizuje procenty w Budgets DB
4. Jeśli limit przekroczony - publikuje BUDGET_LIMIT_EXCEEDED
5. WebApp odświeża progress bary w czasie rzeczywistym

---

## 🔄 **Wydatki Cykliczne**

### HP-010: Dodanie wydatku cyklicznego
**Jako** użytkownik  
**Chcę** dodać wydatek cykliczny (np. czynsz)  
**Aby** system przypominał mi o płatnościach  

**Ścieżka:**
1. User wypełnia formularz wydatku cyklicznego w WebApp
2. WebApp wysyła dane do API Gateway → Budget Service
3. Budget Service zapisuje definicję cykliczną do Budgets DB
4. Budget Service planuje następny termin płatności
5. WebApp wyświetla potwierdrzenie i listę cyklicznych

### HP-011: Przypomnienie o płatności cyklicznej
**Jako** system  
**Chcę** przypomnieć o nadchodzącej płatności  
**Aby** użytkownik nie zapomniał o opłacie  

**Ścieżka:**
1. Budget Service (scheduler) wykrywa zbliżający się termin
2. Budget Service publikuje RECURRING_DUE do RabbitMQ
3. Notification Service odbiera event
4. Notification Service wysyła powiadomienie do Email Service
5. User otrzymuje email z przypomnieniem

### HP-012: Oznaczenie płatności jako zapłacona
**Jako** użytkownik  
**Chcę** oznaczyć cykliczną płatność jako zapłaconą  
**Aby** system zaplanował następny termin  

**Ścieżka:**
1. User klika "Zapłacone" przy cyklicznej płatności
2. WebApp wysyła żądanie do API Gateway → Budget Service  
3. Budget Service oznacza jako zapłacone i planuje następny termin
4. Budget Service publikuje RECURRING_PAID
5. WebApp usuwa z listy "do zapłaty" i wyświetla następny termin

---

## 🔔 **Powiadomienia**

### HP-013: Konfiguracja preferencji powiadomień
**Jako** użytkownik  
**Chcę** ustawić preferencje powiadomień  
**Aby** otrzymywać tylko interesujące mnie alerty  

**Ścieżka:**
1. User otwiera ustawienia powiadomień w WebApp
2. WebApp pobiera aktualne preferencje z API Gateway → Notification Service
3. Notification Service czyta ustawienia z Budgets DB
4. User modyfikuje preferencje (email on/off, typy alertów)
5. WebApp zapisuje zmiany przez Notification Service do Budgets DB

### HP-014: Wysyłanie alertu o przekroczeniu budżetu
**Jako** system  
**Chcę** powiadomić użytkownika o przekroczeniu limitu  
**Aby** mógł reagować na nadmierne wydatki  

**Ścieżka:**
1. Budget Service wykrywa przekroczenie limitu
2. Budget Service publikuje BUDGET_LIMIT_EXCEEDED
3. Notification Service odbiera event z RabbitMQ
4. Notification Service sprawdza preferencje użytkownika w Budgets DB
5. Jeśli email włączony - wysyła powiadomienie przez Email Service
6. User otrzymuje alert email

---

## 📊 **Przeglądanie Danych**

### HP-015: Przeglądanie listy transakcji
**Jako** użytkownik  
**Chcę** przeglądać swoje transakcje  
**Aby** analizować wydatki  

**Ścieżka:**
1. User otwiera listę transakcji w WebApp
2. WebApp wysyła żądanie do API Gateway → Transaction Service
3. Transaction Service pobiera dane z Transactions DB
4. Transaction Service zwraca przefiltrowane transakcje
5. WebApp wyświetla listę z opcjami filtrowania

### HP-016: Dashboard - przegląd stanu budżetu
**Jako** użytkownik  
**Chcę** widzieć szybki przegląd mojego budżetu  
**Aby** kontrolować finanse jednym rzutem oka  

**Ścieżka:**
1. User otwiera dashboard w WebApp
2. WebApp pobiera dane z API Gateway → Budget Service
3. Budget Service zwraca:
   - Aktualne saldo
   - Wykorzystanie budżetu per kategoria
   - Nadchodzące płatności cykliczne
4. WebApp wyświetla dashboard z progress barami i alertami

---

## 🔄 **Offline/PWA**

### HP-017: Praca offline
**Jako** użytkownik  
**Chcę** dodawać transakcje bez internetu  
**Aby** nie tracić danych gdy jestem offline  

**Ścieżka:**
1. User traci połączenie internetowe
2. WebApp wykrywa offline i przełącza na tryb lokalny
3. User dodaje transakcje - WebApp zapisuje w IndexedDB
4. Po powrocie online WebApp synchronizuje dane z API
5. Transaction Service przetwarza zbiorczo offline transakcje

---

## 📈 **Rozbudowa (Nice-to-have)**

### HP-018: Eksport danych
**Jako** użytkownik  
**Chcę** wyeksportować swoje dane  
**Aby** analizować je w zewnętrznych narzędziach  

### HP-019: Współdzielenie budżetu
**Jako** użytkownik  
**Chcę** współdzielić budżet z partnerem  
**Aby** wspólnie zarządzać finansami  

### HP-020: Analityka i wykresy
**Jako** użytkownik  
**Chcę** widzieć wykresy wydatków  
**Aby** lepiej zrozumieć swoje nawyki finansowe  

---

## 🏷️ **Tagowanie Ticketów**

### Komponenty:
- `[FE]` - Frontend Angular
- `[API-GW]` - API Gateway  
- `[TX-SVC]` - Transaction Service
- `[BUD-SVC]` - Budget Service
- `[NOT-SVC]` - Notification Service
- `[AUTH]` - Autoryzacja/Keycloak
- `[DB]` - Baza danych
- `[MSG]` - RabbitMQ/Events

### Priorytety MVP:
- **P0** - Krytyczne dla MVP (HP-001 do HP-016)
- **P1** - Ważne dla MVP (HP-017)
- **P2** - Nice-to-have (HP-018+)