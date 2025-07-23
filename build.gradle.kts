plugins {
    embeddedKotlin("jvm")
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.2"
    `maven-publish`
}

group = "com.stochastictinkr.lwjgl"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    testImplementation(kotlin("test"))
}

// Plugin development configuration
gradlePlugin {
    website = "https://github.com/StochasticTinkr/lwjgl3-gradle"
    vcsUrl = "https://github.com/StochasticTinkr/lwjgl3-gradle.git"

    plugins {
        create("plugin") {
            id = "com.stochastictinkr.lwjgl"
            displayName = "StochasticTinkr's LWJGL3 Plugin"
            description = "A Gradle plugin for managing LWJGL3 dependencies and configurations."
            tags.set(listOf("lwjgl", "dependencies"))
            implementationClass = "com.stochastictinkr.gradle.lwjgl.LwjglPlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {
        maven {
            // Used for testing with the `example` project.
            name = "localBuildRepo"
            url = uri("${layout.buildDirectory.get().asFile.path}/repo")
        }
    }
}
