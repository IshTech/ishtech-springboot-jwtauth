<!-- Shared IshTech rule file: keep identical in every repo's .claude/rules/. Repo-specific notes belong in .claude/CLAUDE.md. -->
# Working with the owner

- Do tasks in the order the owner gives. Finish one completely, including any follow-up the owner asked for, before starting the next. Don't pull a later task into the current one.
- When the owner asks a question or asks for feedback, answer it. Don't start running tools or changing files.
- Answer design questions yourself, with a clear recommendation. Don't hand them off to an agent.
- When the owner asks for a task to run "in a worktree", start it as a Claude Code worktree task so it appears as a separate session. Don't hand-roll `git worktree add` inside the current session. Branch rules still apply (see `git-and-branches.md`).
- Verify before stating something as fact: versions, build results, what resolved from where. If you didn't verify it, say so. Report failures and skipped steps plainly.
- Flag anything odd you notice, even outside the current task (a missing `.mvn/settings.xml`, mismatched coordinates, stale docs). Point it out; don't silently fix it and don't silently accept it.
- A project can be excused from a strict rule only when the owner says so, and only for that task.
- Owner's machine (Windows): library repos under `D:\Work\IshTech\code\`, practice repos under `D:\Practice\IshTech\code\`. Each subfolder is its own git repo; the parent folders are not. On another machine, or in a cloud or mobile session, only this repo may be present.
