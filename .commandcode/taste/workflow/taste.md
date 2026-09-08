# Taste

- When debugging a failing Gradle/Maven task (e.g. publish), confirm the true root cause empirically before fixing — e.g. run a probe via a Gradle `--init-script` that prints the actual resolved values (`artifactId`, `archivesName`, `project.name`) — rather than relying on docs/assumptions. Then verify the fix by re-running the exact task/build that failed. Confidence: 0.8