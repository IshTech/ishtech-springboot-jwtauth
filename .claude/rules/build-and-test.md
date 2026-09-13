<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Build and test

- Read the repo's README, and the docs it links, before choosing any build, run, test or Docker command. Commands, profiles, ports and database needs differ between repos.
- Always use the wrapper: `./mvnw` or `./gradlew`.

## Test levels
There are three test levels. Level 2 applies to repos whose docs describe running the app, and Level 3 to repos whose docs describe running it with Docker; a library-only repo has only Level 1. The repo's `.claude/CLAUDE.md` names the doc and section to use for each level.

When each level runs:
- Level 1 is the default: run it for every change. It must pass before any push and before any merge.
- On a feature branch, Levels 2 and 3 are on demand. Before merging a feature branch into `dev`, or a child feature branch into its parent, ask the owner whether to run Level 2, Level 3, or both.
- When the owner asks whether `dev` is ready to merge into `main`, run Levels 2 and 3 as part of the readiness check in `versions-and-releases.md`.

### Level 1: build with tests
- Run the build-with-tests command from the repo's README.
- Passes when the build succeeds with no compile errors and no test failures or errors. Report the test counts.

### Level 2: run the app with Maven/Gradle
Start the app the way the repo's docs describe (command, profile, database), on a free port. Then check, in order:
1. Start-up: the log shows the application's "Started ..." line with no startup errors, and the app is listening on the expected port.
2. Health: `GET /actuator/health` on that port returns HTTP 200 with `"status":"UP"`. Also check any other actuator endpoints the docs mention.
3. API tests: run the API/curl flows from the docs the README links (`CURL-INFO.md`, and `TEST-SUITE.md` where present) against that port, and report each call as expected vs actual status.

Afterwards, stop the app and make sure the forked JVM has exited too; stopping Maven alone can leave it listening on the port.

### Level 3: build and run with Docker
Build and start the app with docker compose as the repo's Docker doc describes. Use host ports that are free and don't clash with other running apps (the doc lists the port variables). Use the compose file's own database service when it defines one, not a local database. Then check, in order:
1. Start-up: the app container is running, and its log (`docker logs <container>`) shows the application's "Started ..." line with no startup errors.
2. Health: `GET /actuator/health` on the mapped host port returns HTTP 200 with `"status":"UP"`. Also check any other actuator endpoints the docs mention. Check from the host; don't rely on the container's Docker health status alone.
3. API tests: run the API/curl flows from the docs the README links (`CURL-INFO.md`, and `TEST-SUITE.md` where present) against the mapped host port, and report each call as expected vs actual status.

Afterwards, tear the stack down with `docker compose down -v`.

### For every level
- Check a port is free before using it, and prefer alternative ports: other sessions may be using the defaults.
- Test data: use unique, obviously-test identifiers (e.g. `apitest-<timestamp>@example.com`) and delete what you created in any shared or dev database afterwards.
- If a check fails, establish whether it's pre-existing (e.g. reproduce on the previous commit) before calling it a regression.

## Cross-repo builds
- When an upstream repo changes, build it first with `clean install` so downstream repos build against the fresh local SNAPSHOT (`verify` installs nothing). Confirm with `./mvnw dependency:list` when it matters.
- Decide the order from the declared versions in the build files: a downstream repo pinned to a released version isn't affected by an upstream SNAPSHOT.
- To find which of the owner's own repos use this one, search their build files (`pom.xml`, `build.gradle.kts`) when you need it; don't rely on a stored list. Repo locations are in `owner-workflow.md`. If those repos aren't available (e.g. a cloud or mobile session), say in your report that consumers weren't checked.
