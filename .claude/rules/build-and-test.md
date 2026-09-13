<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Build and test

- Read the repo's README, and the docs it links, before choosing any build, run, test or Docker command. Several repos need a profile or a running database; for example, springboot-multi-db fails a plain `./mvnw clean verify` without `-P h2`.
- Always use the wrapper: `./mvnw` or `./gradlew`.
- Before any push, the build with tests must pass (`./mvnw clean install` for libraries).

## Cross-repo dependency order
- When an upstream repo changes, build it first with `clean install` so downstream builds against the fresh local SNAPSHOT (`verify` installs nothing). Confirm with `./mvnw dependency:list` when it matters.
- Decide the order from the declared versions in `pom.xml` / `build.gradle.kts`: a downstream repo pinned to a released version isn't affected by an upstream SNAPSHOT.
- Known graph ("X is used by Y"; verify in the build files):
  - `hetu-util` → `hetu-validation`
  - `ishtech-i18n` → `ishtech-base-jpa`, `ishtech-springboot-jwtauth`
  - `ishtech-base-jpa` → `ishtech-springboot-jwtauth`, `springboot-books-app`
  - `ishtech-springboot-jwtauth` → `springboot-multi-port`, `springboot-oms`, `springboot-books-app`
  - `ishtech-validations` → `springboot-multi-port`, `springboot-books-app`

## Running and API testing
- Run exactly as the docs say (profile, ports, database). Check the port is free first, and prefer alternative ports: other sessions may be using the defaults.
- When stopping a background `spring-boot:run`, make sure the forked JVM has exited too; stopping Maven alone can leave it listening on the port.
- Test data: use unique, obviously-test identifiers (e.g. `apitest-<timestamp>@example.com`) and delete what you created in any shared or dev database afterwards. Tear Docker test stacks down with `docker compose down -v`.
- Report each check as expected vs actual. If something fails, establish whether it's pre-existing (e.g. reproduce on the previous commit) before calling it a regression.
