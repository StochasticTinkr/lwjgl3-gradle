plugins {
    embeddedKotlin("jvm")

    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "1.2.2"

    `maven-publish`

}

group = "com.stochastictinkr.lwjgl"
version = "1.0-SNAPSHOT"

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
    plugins {
        create("myPlugin") {
            id = "com.stochastictinkr.lwjgl"
            implementationClass = "com.stochastictinkr.LwjglPlugin"
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
            name = "localBuildRepo"
            url = uri("${layout.buildDirectory.get().asFile.path}/repo")
        }
    }
}
