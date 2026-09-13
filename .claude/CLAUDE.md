<!-- Repo-specific instructions. The shared IshTech rules live in .claude/rules/ and are identical across repos; don't put repo-specific content there. -->
# ishtech-springboot-jwtauth

The owner's standing instructions are in `.claude/rules/` (`owner-workflow.md`, `git-and-branches.md`, `versions-and-releases.md`, `build-and-test.md`, `build-tooling.md`, `documentation.md`). They apply to every task in this repo. This file adds only what is specific to this repo.

## Read the doc before doing the thing
The docs are the source of truth. Don't guess commands: open the matching file first and follow its links.

| Before you... | Read |
|---|---|
| work out what the project is, its tech stack and modules | `README.md` → "Tech stack", "Design", "Project structure", "Usage", then the module READMEs linked under "Project structure" |
| build, test, or publish (publish only when the owner asks) | `README.md` → "Build and Run" |
| run the app locally | `ishtech-springboot-jwtauth-web/README.md` |
| touch the database | `ishtech-springboot-jwtauth-web/DB-SETUP.md` |
| use Docker or docker compose | `ishtech-springboot-jwtauth-web/DOCKER-BUILD.md` |
| call the APIs | `CURL-INFO.md`; endpoint summary in `API-INFO.md` |
| run the scripted API test cases | `TEST-SUITE.md` |
| change the version or anything release-related, or check what CI enforces | `.github/workflows/cicd.yml` |
| report or fix a bug | `KNOWN-ISSUES.md` first, it may already be recorded |

If a doc is missing, wrong or unclear, fix the doc (see `rules/documentation.md`) instead of working around it.

## Repo-specific rules
- The owner treats this repo as critical. Before `dev` is ready for the owner's pull request to `main`, API testing is required in **both** modes: run the app with Maven (`ishtech-springboot-jwtauth-web/README.md`) and with Docker compose (`ishtech-springboot-jwtauth-web/DOCKER-BUILD.md`), and exercise the `CURL-INFO.md` flows in each.
- Delete any test users you create in the local `dev` database once testing is done.
