<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Versions and releases

- `dev` and feature branches use a SNAPSHOT version (`x.y.z-SNAPSHOT`; a feature branch may add a qualifier, e.g. `x.y.z-topic-SNAPSHOT`). `main` uses a release version without SNAPSHOT.
- A version bump is its own commit, e.g. `pom - 3.3.0 snapshot version`, `pom - 3.2.0 release version`.
- A change that breaks library consumers (groupId or package rename, public API change) needs a major version bump. Raise it with the owner.
- Never publish anything unless the owner explicitly asks for that specific publish: no `deploy`, no `-P central-publishing` or `-P gpg`, no Docker image push, no release tag.

## dev → main merge-readiness (all must hold)
1. No SNAPSHOT dependencies: every ishtech dependency is a release version.
2. Clean build with no compile or test failures.
3. The app runs with Maven/Gradle and passes the API/curl tests from the repo docs (`CURL-INFO.md`, `TEST-SUITE.md` where present).
4. Optionally, or mandatorily where the repo's `.claude/CLAUDE.md` says so: run it in Docker and repeat the API tests. Alternative ports are fine.
