# Contributing to Smart Budget

Thanks for your interest in contributing! This document explains how to raise issues, propose changes, and submit code to the Smart Budget repository.

Table of contents
- [How to report bugs or request features](#how-to-report-bugs-or-request-features)
- [Development workflow](#development-workflow)
- [Branching and naming](#branching-and-naming)
- [Commit messages](#commit-messages)
- [Pull request checklist](#pull-request-checklist)
- [Testing](#testing)
- [Code style and quality](#code-style-and-quality)
- [Code of Conduct](#code-of-conduct)

How to report bugs or request features
-------------------------------------
- Search existing issues first to avoid duplicates.
- Open a new issue with a descriptive title and steps to reproduce (for bugs), expected behavior, and relevant logs or screenshots.
- For feature requests, describe the user problem, proposed solution at a high level, and any acceptance criteria.

Development workflow
--------------------
1. Fork the repository (or create a branch if you have push access).
2. Create a feature branch from `main` or `develop`: `feature/<short-description>` or `fix/<short-description>`.
3. Make changes locally. Keep changes focused and well-tested.
4. Run tests locally and ensure they pass.
5. Push your branch and open a pull request (PR) against `main` (or `develop` if the project uses that branch).
6. Add a clear PR description, link related issues, and add reviewers.

Branching and naming
--------------------
Use short, descriptive branch names, for example:
- `feature/add-budget-summary`
- `fix/null-pointer-in-transaction-service`
- `chore/update-dependencies`

Commit messages
---------------
Write clear, concise commit messages. A suggested format:
```
<type>(<scope>): <short summary>

Optional: longer description explaining the change and reasoning.
```
Types: `feat`, `fix`, `docs`, `chore`, `test`, `refactor`.

Pull request checklist
----------------------
- [ ] The PR has a descriptive title and summary of changes.
- [ ] All tests pass locally.
- [ ] New features are covered by tests (unit/integration as appropriate).
- [ ] Documentation has been updated if needed (README, docs/).
- [ ] No sensitive data or secrets are included.
- [ ] CI checks pass (GitHub Actions runs tests automatically).

Testing
-------
- Run backend tests with the Gradle wrapper:
  - Windows: `cd server && gradlew.bat test`
  - *nix: `cd server && ./gradlew test`
- Tests should be fast and deterministic. If a test requires external resources, document how to run it (mocking, test containers, etc.).

Code style and quality
----------------------
- Follow existing project conventions (Java code style, package layout).
- Add unit tests for all new behavior.
- Keep methods small and focused; prefer clear naming over clever implementations.

Code of Conduct
---------------
Please follow a respectful and professional tone when interacting in issues and pull requests. By contributing, you agree to abide by a standard open-source code of conduct.

If you need help getting started, open an issue with the tag `help wanted` and a maintainer will assist.
