plugins {
    embeddedKotlin("jvm")
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.2"
    `maven-publish`
}

group = "com.stochastictinkr.lwjgl"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val testAgent: Configuration by configurations.creating

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.mockk:mockk:1.14.5")
    testAgent("net.bytebuddy:byte-buddy-agent:1.15.11")
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
    jvmArgs("-javaagent:${testAgent.asPath}")
}