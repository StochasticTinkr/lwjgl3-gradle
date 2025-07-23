package com.stochastictinkr.gradle.lwjgl

data class Module(
    val name: String,
    val hasNatives: Boolean = true,
    val minVersion: String = "3.1.0",
    val artifact: String = "lwjgl-$name",
) {
    // Override equals and hashCode to compare by name
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Module) return false
        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}