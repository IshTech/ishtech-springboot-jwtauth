<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Build and test

- Read the repo's README, and the docs it links, before choosing any build, run, test or Docker command. Commands, profiles, ports and database needs differ between repos.
- Always use the wrapper: `./mvnw` or `./gradlew`.
- "Build and tests pass" means the README's build-with-tests command succeeds with no compile or test failures.

## Cross-repo builds
- When an upstream repo changes, build it first with `clean install` so downstream repos build against the fresh local SNAPSHOT (`verify` installs nothing). Confirm with `./mvnw dependency:list` when it matters.
- Decide the order from the declared versions in the build files: a downstream repo pinned to a released version isn't affected by an upstream SNAPSHOT.
- To find which of the owner's own repos use this one, search their build files (`pom.xml`, `build.gradle.kts`) when you need it; don't rely on a stored list. Repo locations are in `owner-workflow.md`. If those repos aren't available (e.g. a cloud or mobile session), say in your report that consumers weren't checked.

## Running and API testing
- Run exactly as the docs say (profile, ports, database). Check the port is free first, and prefer alternative ports: other sessions may be using the defaults.
- When stopping a background `spring-boot:run`, make sure the forked JVM has exited too; stopping Maven alone can leave it listening on the port.
- Test data: use unique, obviously-test identifiers (e.g. `apitest-<timestamp>@example.com`) and delete what you created in any shared or dev database afterwards. Tear Docker test stacks down with `docker compose down -v`.
- Report each check as expected vs actual. If something fails, establish whether it's pre-existing (e.g. reproduce on the previous commit) before calling it a regression.
