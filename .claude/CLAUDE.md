<!-- Repo-specific instructions. The shared IshTech rules live in .claude/rules/ and are identical across repos; don't put repo-specific content there. -->
# ishtech-springboot-jwtauth

The owner's standing instructions are in `.claude/rules/` (`owner-workflow.md`, `git-and-branches.md`, `versions-and-releases.md`, `build-and-test.md`, `build-tooling.md`, `documentation.md`, `repositories.md`). They apply to every task in this repo. This file adds only what is specific to this repo.

## About this repo
- It's a Maven multi-module project and both a library and an application (`README.md`, sections "Project structure" and "Usage"):
  - `ishtech-springboot-jwtauth-lib` and `ishtech-springboot-jwtauth-api` are libraries published to Maven Central.
  - `ishtech-springboot-jwtauth-web` is the runnable Spring Boot application.
- So it has test Levels 1, 2 and 3, and dependent tests apply (`rules/build-and-test.md`, section "Dependent tests"): they confirm that a change here doesn't break the projects that use `ishtech-springboot-jwtauth-api` and has the intended effect in them.
  - None of the owner's other libraries depends on it, so there is no default dependent; the owner names one when dependent tests are needed.
  - The libraries are public on Maven Central, so they may have consumers that nobody can list.
- Upstream libraries: `ishtech-base-jpa`, and `ishtech-i18n-java` through it (`rules/repositories.md`). The versions are declared in the root `pom.xml`.

## Read the doc before doing the thing
The docs are the source of truth. Don't guess commands: open the matching file and section first, and follow its links.

| Before you... | Read |
|---|---|
| work out what the project is, its tech stack and modules | `README.md`, sections "Tech stack", "Design", "Project structure" and "Usage", then the module READMEs linked under "Project structure" |
| run test Level 1 (build with tests) | `README.md`, section "Build and Run", subsection "Local Maven Build" |
| run test Level 2 (run the app with Maven) | `ishtech-springboot-jwtauth-web/README.md`, section "Build and Run", subsection "Local Maven Run". The app needs its database set up first: `ishtech-springboot-jwtauth-web/DB-SETUP.md`, section "Local" |
| run test Level 3 (run with Docker compose) | `ishtech-springboot-jwtauth-web/DOCKER-BUILD.md`, section "Run with docker compose" |
| run the API tests (part of Levels 2 and 3) | `CURL-INFO.md` (every flow) and `TEST-SUITE.md`, section "Test Cases"; endpoint list in `API-INFO.md` |
| touch the database for any other reason | `ishtech-springboot-jwtauth-web/DB-SETUP.md` |
| publish (only when the owner asks) | `README.md`, section "Build and Run", subsection "Publish to Maven Central" |
| change the version or anything release-related, or check what CI enforces | `.github/workflows/cicd.yml` |
| report or fix a bug | `KNOWN-ISSUES.md` first, it may already be recorded |

If a doc is missing, wrong or unclear, fix the doc (see `rules/documentation.md`) instead of working around it.
