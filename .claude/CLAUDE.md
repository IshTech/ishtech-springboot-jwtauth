<!-- Repo-specific instructions. The shared IshTech rules live in .claude/rules/ and are identical across repos; don't put repo-specific content there. -->
# ishtech-springboot-jwtauth

The files in `.claude/rules/` are the owner's standing instructions. They apply to every task in this repo.

## What this repo is
- Spring Boot JWT authentication, multi-module Maven project (Java 25, Spring Boot 4.0.x):
  - `ishtech-springboot-jwtauth-lib`: JWT security configuration (library)
  - `ishtech-springboot-jwtauth-api`: security plus auth/user REST APIs (library)
  - `ishtech-springboot-jwtauth-web`: standalone runnable auth server
- `-lib` and `-api` are published to Maven Central; `-web` ships as the Docker image `muneer2ishtech/ishtech-springboot-jwtauth-web`.
- The owner treats this repo as critical. It depends on `ishtech-base-jpa` and `ishtech-i18n`, and is consumed by `springboot-multi-port`, `springboot-oms` and `springboot-books-app`, so changes ripple.
- CI (`.github/workflows/cicd.yml`) validates the version on every push (non-`main` branches must be SNAPSHOT) and its deploy job publishes the Docker image. Read it before changing versions or release steps.

## Read the doc before doing the thing
The docs are the source of truth. Don't guess commands: open the matching file first and follow its links.

| Before you... | Read |
|---|---|
| work out what the project and its modules do | `README.md`, then the module READMEs it links |
| build, test, or publish (publish only when asked) | `README.md` → "Build and Run" |
| run the app locally | `ishtech-springboot-jwtauth-web/README.md` |
| touch the database | `ishtech-springboot-jwtauth-web/DB-SETUP.md` |
| use Docker or docker compose | `ishtech-springboot-jwtauth-web/DOCKER-BUILD.md` (commands run from the repo root) |
| call the APIs | `CURL-INFO.md`; endpoint summary in `API-INFO.md` |
| run the scripted API test cases | `TEST-SUITE.md` |
| report or fix a bug | `KNOWN-ISSUES.md` first, it may already be recorded |

If a doc is missing, wrong or unclear, fix the doc (see `rules/documentation.md`) instead of working around it.

## Repo-specific rules
- Before merging `dev` → `main`, API testing is mandatory in **both** modes: run via Maven (`spring-boot:run` with the `dev` profile) and via Docker compose, and exercise the CURL-INFO.md flows in each.
- The local `dev` run needs PostgreSQL with the app's DB users and schemas already created (DB-SETUP.md). Delete any test users you create there once testing is done.
