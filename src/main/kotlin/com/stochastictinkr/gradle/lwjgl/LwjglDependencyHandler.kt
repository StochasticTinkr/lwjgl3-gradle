package com.stochastictinkr.gradle.lwjgl

sealed interface LwjglDependencyHandler {
    /**
     * The Maven group for LWJGL artifacts.
     */
    var group: String

    /**
     * The LWJGL version to use.
     */
    var version: String

    /**
     * The configuration used for LWJGL implementation (compile and runtime) dependencies.
     */
    var implementationConfiguration: String

    /**
     * The configuration used for LWJGL runtime-only dependencies (natives).
     */
    var runtimeConfiguration: String


    /**
     * The set of LWJGL module dependencies explicitly requested. Does not include modules added by presets.
     */
    var modules: MutableSet<Module>

    /**
     * Sets the native platforms to use for runtime dependencies.
     */
    fun nativePlatforms(vararg platforms: Platform)

    /**
     * Sets the native platforms to use by their names. Useful if the platform is not one of the predefined ones.
     */
    fun customNativePlatforms(vararg platforms: String)

    /**
     * Sets the native platforms to use for runtime dependencies.
     */
    fun nativePlatforms(platforms: Iterable<Platform>)

    /**
     * Includes natives for all supported native platforms.
     * See [Platforms.allPlatforms] for the list of supported platforms.
     */
    fun useAllNativePlatforms()

    /**
     * Presets are used to quickly add commonly used LWJGL modules.
     * See the following methods for available presets:
     * - [Presets.everything]: Adds all LWJGL modules.
     * - [Presets.gettingStarted]: Adds modules commonly used to get started with LWJGL.
     * - [Presets.minimalOpenGL]: Adds modules commonly used for minimal OpenGL usage.
     * - [Presets.minimalOpenGLES]: Adds modules commonly used for minimal OpenGL ES usage.
     * - [Presets.minimalVulkan]: Adds modules commonly used for minimal Vulkan usage.
     */
    val presets: Presets

    /**
     * Convenience method to set multiple presets at once.
     * Presets are used to quickly add commonly used LWJGL modules.
     * See the following methods for available presets:
     * - [Presets.everything]: Adds all LWJGL modules.
     * - [Presets.gettingStarted]: Adds modules commonly used to get started with LWJGL.
     * - [Presets.minimalOpenGL]: Adds modules commonly used for minimal OpenGL usage.
     * - [Presets.minimalOpenGLES]: Adds modules commonly used for minimal OpenGL ES usage.
     * - [Presets.minimalVulkan]: Adds modules commonly used for minimal Vulkan usage.
     */
    fun preset(preset: Presets.() -> Unit)

    fun modules(vararg modules: Module)
}


