plugins { id("com.diffplug.spotless") version "8.10.0" }

repositories { mavenCentral() }

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  java {
    target("**/*.java")
    googleJavaFormat()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  yaml {
    target("**/*.yml")
    jackson()
  }
}
