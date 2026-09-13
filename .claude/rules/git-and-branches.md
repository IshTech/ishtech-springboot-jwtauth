<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Git: branches, commits, pushes

## Branches
- `dev` is the default working branch. `main` holds releases only.
- Creating any branch (including a worktree branch) and choosing its name needs the owner's explicit approval, every time, in every repo. Propose the name (convention `feature/<short-topic>`), wait for approval, then create it. Never rename a branch without approval.
- Feature branches merge into `dev` only after the build and tests pass.
- Never commit directly to `main`. If there is truly no other way, stop, explain why, and get explicit approval first.
- To check the current branch use `git rev-parse --abbrev-ref HEAD`; `git branch ...` triggers an approval prompt.

## Commits
- Atomic and progressive: one task per commit. Several files are fine when they belong to the same task.
- Message style: short and lowercase, matching the repo's `git log`, for example `pom - spring-boot version update to 4.0.8`, `mvn wrapper update to 3.9.16`, `known issues - <summary>`, `fix <thing>`. No explanatory body. Run `git log -15 --format=%s` before writing one.
- Commit only when the owner asks.

## Pushes
- Push only when the owner asks. After committing, always say whether it is pushed; never leave commits silently unpushed.
- Before pushing: `git fetch`, list exactly what will go out (`git log --oneline origin/<branch>..<branch>`), and confirm it is only what the owner intended. Call out any other unpushed commits, including the owner's own, before pushing.
- The build and tests must pass before any push (see `build-and-test.md`).
