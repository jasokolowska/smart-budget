CONTRIBUTING
============

Thank you for your interest in contributing to Smart Budget. Below are concise guidelines to make collaboration smooth.

1. Report issues and propose features
- Use GitHub issues to report bugs or request features. Provide steps to reproduce, expected behavior, logs and version information.
- For larger features, open an issue first to discuss design and avoid duplicate work.

2. Workflow
- Fork the repository → create a branch named feature/<short-desc> or fix/<issue>-<short-desc>.
- Keep your branch up-to-date with main; rebase or merge before opening a pull request.

3. Commits
- Use clear, short commit messages. Suggested prefixes:
  - feat: new feature
  - fix: bug fix
  - docs: documentation changes
  - test: tests added/updated
  - chore: maintenance/config

4. Pull Requests
- Open PRs against main. Include:
  - Reference to related issue (if any).
  - Short summary of changes.
  - How to run and test changes locally.
  - Mention if tests were added.
- Keep PRs focused and atomic.

5. Tests and quality
- Add unit and integration tests for new behavior.
- Run tests locally before PR: from server/ run ./gradlew test (or gradlew.bat test on Windows).

6. Style and formatting
- Follow Java formatting conventions (IDE defaults are acceptable). Keep code readable and documented where necessary.

7. Security
- Do not commit secrets or credentials. Use environment variables or ignored config files.

8. License
- By contributing you agree your contributions will be distributed under the project license (MIT).

9. Code of Conduct
- Follow the project's Code of Conduct (CODE_OF_CONDUCT.md).

---

PL (Polish translation)

Wkład do projektu
==================

Dziękujemy za chęć współpracy nad Smart Budget. Poniżej krótkie wskazówki dotyczące współpracy.

1. Zgłaszanie problemów i pomysłów
- Używaj issues do zgłaszania błędów i propozycji funkcji. Dodaj kroki reprodukcji, oczekiwane zachowanie, logi i wersję.
- Przy większych zmianach najpierw otwórz issue z opisem rozwiązania.

2. Workflow
- Fork → branch feature/<krótki-opis> lub fix/<issue>-<krótki-opis>.
- Aktualizuj branch z main (rebase/merge) przed otwarciem PR.

3. Commity
- Czytelne wiadomości commit. Przykładowe prefixy: feat:, fix:, docs:, test:, chore:.

4. Pull Requesty
- Otwieraj PR do main. W opisie umieść powiązane issue, podsumowanie zmian, instrukcje testowania i informację o testach.

5. Testy i jakość
- Dodawaj testy jednostkowe/integracyjne. Uruchom testy lokalnie: w katalogu server -> ./gradlew test.

6. Styl
- Stosuj konwencje formatowania Javy i dbaj o czytelność kodu.

7. Bezpieczeństwo
- Nie commituj sekretów; używaj zmiennych środowiskowych lub plików ignorowanych przez VCS.

8. Licencja
- Wysyłając PR akceptujesz, że Twój kod będzie objęty licencją projektu (MIT).

9. Zasady współpracy
- Przestrzegaj CODE_OF_CONDUCT.md i utrzymuj konstruktywną komunikację.

