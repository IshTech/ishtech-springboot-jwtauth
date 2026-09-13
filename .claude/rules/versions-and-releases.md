<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Versions and releases

- `dev` and feature branches use a SNAPSHOT version (`x.y.z-SNAPSHOT`; a feature branch may add a qualifier, e.g. `x.y.z-topic-SNAPSHOT`). `main` uses a release version without SNAPSHOT.
- A version bump is its own commit.
- Library repos (published to Maven Central; see the publish section of the repo's README) have consumers you cannot know. Treat public classes, configuration properties and behaviour as a contract: a breaking change needs a major version bump and the owner's approval.
- Never publish anything unless the owner explicitly asks for that specific publish: no `deploy`, no `-P central-publishing` or `-P gpg`, no Docker image push, no release or tag.

## Readiness for the owner's pull request from `dev` to `main`
When the owner asks whether `dev` is ready, run every check below and report each result:
1. No SNAPSHOT dependencies: every ishtech dependency is a release version.
2. Test Level 1 passes (`build-and-test.md`).
3. Test Levels 2 and 3 pass, for repos that have them (`build-and-test.md`).
4. Dependent tests pass against the repo's default dependent, for repos that other repos depend on. Report whether they were done and each result, as `build-and-test.md`, section "Dependent tests", describes.
