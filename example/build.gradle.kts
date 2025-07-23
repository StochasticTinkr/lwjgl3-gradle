plugins {
    kotlin("jvm") version "2.1.21"
    id("com.stochastictinkr.lwjgl") version "1.0-SNAPSHOT"
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

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

