plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.12.0"
}

group = "com.sylvanaar"
version = "1.0-SNAPSHOT"

dependencies {
  // compile files("lib/*.jar")
  implementation(fileTree("lib"))
}

repositories {
  mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.1.4")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf("copyright", "java"))
  pluginName.set("Lua")

  // This was set to false for some reason. Why?
  // See: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html#intellij-extension-updatesinceuntilbuild
  updateSinceUntilBuild.set(false)
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }

  patchPluginXml {
    sinceBuild.set("221")
    untilBuild.set("231.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
