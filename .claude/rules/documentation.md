<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Documentation

- READMEs, and the docs they link, are the source of truth for humans and AI alike. Keep project facts there, not in `.claude/`.
- Update the README and docs in the same task whenever code, features, commands, ports or setup change.
- If a doc is wrong or unclear (e.g. a port that doesn't match the compose file), fix the doc and mention it. Don't silently work around it.
- Confirmed bugs found by testing but not yet fixed go in the repo-root `KNOWN-ISSUES.md`, linked from the README under `## Known Issues`. One numbered entry per issue, with **Status**, **Impact** and **Affects**, then Description, Steps to reproduce, Likely cause and Suggested fix: enough to fix it without re-investigating.
- Before adding an issue, check whether it's already recorded in this repo or a sibling repo. Don't duplicate it.
- Keep the repo root uncluttered: Claude-specific files live only in `.claude/`.
