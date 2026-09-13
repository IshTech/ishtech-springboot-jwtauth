<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Versions and releases

- `dev` and feature branches use a SNAPSHOT version (`x.y.z-SNAPSHOT`; a feature branch may add a qualifier, e.g. `x.y.z-topic-SNAPSHOT`). `main` uses a release version without SNAPSHOT.
- A version bump is its own commit.
- Library repos (published to Maven Central; see the publish section of the repo's README) have consumers you cannot know. Treat public classes, configuration properties and behaviour as a contract: a breaking change needs a major version bump and the owner's approval.
- Never publish anything unless the owner explicitly asks for that specific publish: no `deploy`, no `-P central-publishing` or `-P gpg`, no Docker image push, no release or tag.

## Readiness for the owner's `dev` → `main` pull request (all must hold)
1. No SNAPSHOT dependencies: every ishtech dependency is a release version.
2. Clean build with no compile or test failures.
3. The app runs with Maven/Gradle and passes the API/curl tests from the repo docs (`CURL-INFO.md`, `TEST-SUITE.md` where present).
4. Run it in Docker and repeat the API tests. Optional, unless the repo's `.claude/CLAUDE.md` makes it required. Alternative ports are fine.
