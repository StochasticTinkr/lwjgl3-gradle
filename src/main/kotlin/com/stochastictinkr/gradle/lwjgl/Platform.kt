package com.stochastictinkr.gradle.lwjgl

/**
 * Represents a platform for LWJGL dependencies. The name is the identifier used in the classifier of the dependency
 * coordinates for the native libraries.
 *
 * For example, the platform `linux-arm64` will have the classifier `natives-linux-arm64` in the dependency coordinates.
 */
data class Platform(val name: String)

