# Dokument wymagań produktu (PRD) - Smart Budget

## 1. Przegląd produktu
Smart Budget to aplikacja webowa wspierająca rodziny w Polsce w planowaniu miesięcznego budżetu domowego. System wykorzystuje sztuczną inteligencję do analizy historii transakcji, sezonowości oraz wydatków cyklicznych, aby zaproponować realistyczne limity budżetowe. Celem jest uproszczenie procesu, który dotychczas odbywał się głównie w Excelu, bez inteligentnych wskazówek. Produkt w wersji MVP dostarcza kluczowe funkcjonalności: import wydatków z plików CSV, ręczne dodawanie transakcji, wsparcie AI w generowaniu budżetu, dashboard z przeglądem salda i płatności, a także powiadomienia o przekroczeniach limitów i terminach.

## 2. Problem użytkownika
Planowanie budżetu domowego jest często frustrujące, ponieważ:
- Zakładane kwoty w kategoriach są zbyt niskie, co prowadzi do przekroczeń i zniechęcenia.
- Użytkownicy korzystają z Excela, który nie oferuje inteligentnych wskazówek ani automatyzacji.
- Brakuje wsparcia w uwzględnianiu wydatków cyklicznych i sezonowych.
Smart Budget rozwiązuje ten problem, wspierając użytkowników w tworzeniu realistycznych budżetów i monitorowaniu ich realizacji w czasie rzeczywistym.

## 3. Wymagania funkcjonalne
1. Autoryzacja i uwierzytelnianie:
   - Logowanie przez Keycloak (lokalne konta).
   - Weryfikacja tokenów JWT w API Gateway.
2. Import i dodawanie transakcji:
   - Ręczny upload plików CSV (`date,amount,description[,category]`, separator „,”).
   - Automatyczne kategoryzowanie transakcji (85% skuteczności).
   - Obsługa wypłat z bankomatu poprzez ATM-flow (split do grosza).
   - Ręczne dodawanie transakcji jednorazowych i cyklicznych.
3. Budżet:
   - Generowanie propozycji budżetu AI dla kolejnego miesiąca (jednorazowo).
   - Kategorie dynamiczne, limit 30 (nadmiarowe grupowane przez AI).
   - Sanity-check: odchylenie >30% od mediany 3m wymaga potwierdzenia.
   - Edycja i zapis budżetu.
4. Dashboard:
   - Saldo bieżące.
   - Wykorzystanie budżetu per kategoria.
   - Nadchodzące płatności cykliczne (7 dni) z możliwością oznaczenia jako „Zapłacone”.
   - Ostatnie 5 transakcji (z ikoną dla oszczędności).
5. Wydatki cykliczne:
   - Dodawanie i zarządzanie cyklicznymi płatnościami.
   - Powiadomienia −3 dni i w dniu płatności (drugi anulowany po „Zapłacone”).
   - Możliwość korekty kwoty/opisu.
6. Powiadomienia:
   - Kanały: web push i email.
   - Globalne preferencje użytkownika z możliwością wyłączenia wszystkich.
7. Przeglądanie danych:
   - Lista transakcji z filtrowaniem (zakres dat + multi-select kategorii OR).
8. Audyt:
   - W tabeli budżetu przechowywane `lastModifiedAt` i `lastModifiedBy`.

## 4. Granice produktu
- Poza zakresem MVP: webhook import (Make.com), tryb Gość, PWA offline, zaawansowana analityka i wykresy, eksport CSV/PDF, multi-currency, współdzielone konta, dedykowane aplikacje mobilne.
- Brak KPI biznesowych – jedynie cele techniczne i jakościowe.
- AI wspiera jedynie generowanie budżetu na kolejny miesiąc (jedno wywołanie). Kolejne edycje wykonywane są ręcznie.
- System nie przechowuje szczegółowej historii audytowej (tylko ostatnia modyfikacja).

## 5. Historyjki użytkowników

### Autoryzacja
- US-001: Logowanie użytkownika  
  Jako użytkownik chcę zalogować się do aplikacji, aby móc zarządzać swoim budżetem.  
  Kryteria akceptacji: użytkownik może zalogować się przez Keycloak, otrzymuje JWT, dostęp do dashboardu.

- US-002: Weryfikacja tokenu  
  Jako system chcę weryfikować każde żądanie API, aby zapewnić bezpieczeństwo danych.  
  Kryteria akceptacji: każde żądanie jest sprawdzane, żądania z niepoprawnym tokenem są odrzucane.

### Import i transakcje
- US-003: Ręczny upload CSV  
  Jako użytkownik chcę wgrać plik CSV z transakcjami, aby szybko zaimportować dane.  
  Kryteria akceptacji: plik wczytany, transakcje zapisane, brak kategorii → „Nieprzypisana”.

- US-005: Ręczne dodanie transakcji  
  Jako użytkownik chcę dodać transakcję ręcznie, aby uzupełnić brakujące dane.  
  Kryteria akceptacji: transakcja zapisana, wyświetlona na liście.

- US-006: ATM Flow  
  Jako użytkownik chcę sklasyfikować wypłatę z bankomatu, aby dokładnie śledzić gotówkę.  
  Kryteria akceptacji: system wykrywa wypłatę, pyta o kategorię, umożliwia split do grosza.

### Budżet
- US-007: Generowanie budżetu AI  
  Jako użytkownik chcę wygenerować budżet przy pomocy AI, aby otrzymać propozycje limitów.  
  Kryteria akceptacji: AI zwraca kategorie ≤30 z limitami i krótkim uzasadnieniem.

- US-008: Edycja i zapis budżetu  
  Jako użytkownik chcę edytować zaproponowany budżet, aby dopasować go do moich potrzeb.  
  Kryteria akceptacji: zmiany zapisane w DB, widoczne w dashboardzie.

- US-009: Monitorowanie wykorzystania budżetu  
  Jako użytkownik chcę widzieć aktualne wykorzystanie budżetu, aby kontrolować wydatki.  
  Kryteria akceptacji: dashboard pokazuje saldo i procenty, przekroczenia wyzwalają alert.

### Wydatki cykliczne
- US-010: Dodanie wydatku cyklicznego  
  Jako użytkownik chcę dodać wydatek cykliczny, aby system przypominał mi o płatnościach.  
  Kryteria akceptacji: wydatek zapisany, widoczny w nadchodzących płatnościach.

- US-011: Przypomnienie o płatności cyklicznej  
  Jako system chcę przypomnieć o nadchodzącej płatności, aby użytkownik nie zapomniał.  
  Kryteria akceptacji: powiadomienie wysłane −3 i 0 dni (chyba że oznaczone „Zapłacone”).

- US-012: Oznaczenie płatności jako zapłaconej  
  Jako użytkownik chcę oznaczyć płatność jako zapłaconą, aby system zaplanował kolejny termin.  
  Kryteria akceptacji: płatność oznaczona, pojawia się następna instancja.

### Powiadomienia
- US-013: Konfiguracja preferencji powiadomień  
  Jako użytkownik chcę ustawić preferencje powiadomień, aby otrzymywać tylko interesujące mnie alerty.  
  Kryteria akceptacji: można włączyć/wyłączyć email i web push.

- US-014: Alert o przekroczeniu budżetu  
  Jako system chcę powiadomić użytkownika o przekroczeniu limitu, aby mógł zareagować.  
  Kryteria akceptacji: użytkownik otrzymuje email/web push zależnie od preferencji.

### Przeglądanie danych
- US-015: Przeglądanie listy transakcji  
  Jako użytkownik chcę przeglądać transakcje, aby analizować wydatki.  
  Kryteria akceptacji: możliwość filtrowania po dacie i kategoriach OR.

- US-016: Dashboard  
  Jako użytkownik chcę widzieć szybki przegląd budżetu, aby kontrolować finanse jednym rzutem oka.  
  Kryteria akceptacji: dashboard pokazuje saldo, nadchodzące płatności, ostatnie transakcje, wykorzystanie budżetu.

## 6. Metryki sukcesu
- Minimum 3 pełne budżety miesięczne przygotowane z pomocą AI.
- ≥ 75% limitów budżetowych zaproponowanych przez AI zostało zaakceptowanych lub minimalnie zmodyfikowanych.
- Aplikacja obsługuje pełny cykl: import CSV → analiza historii → budżet AI → monitorowanie.
- Subiektywna ocena: planowanie budżetu z aplikacją jest szybsze i mniej frustrujące.
- Techniczne DoD: poprawny import CSV, poprawne generowanie budżetu, sprawny dashboard, działające powiadomienia, obsługa płatności cyklicznych.
