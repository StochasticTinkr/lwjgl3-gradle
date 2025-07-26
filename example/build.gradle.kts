plugins {
    kotlin("jvm") version "2.1.21"
    id("com.stochastictinkr.lwjgl") version "1.0.2"
    application
}

group = "com.stochastictinkr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    lwjgl {
        version = "3.3.6"
        modules(glfw)
    }
}
application {
    mainClass.set("com.stochastictinkr.lwjgl.example.ShowWindowKt")
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

