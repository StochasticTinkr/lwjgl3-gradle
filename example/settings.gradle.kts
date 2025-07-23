pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal {
            url = java.net.URI("file://${rootProject.projectDir}/../build/repo")
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "example"

