package com.stochastictinkr.gradle.lwjgl

sealed interface Presets {
    /**
     * Whether to filter modules based on their minimum version.
     *
     * When true, modules that do not meet the minimum version requirement will be excluded.
     *
     * Defaults to true.
     */
    var filterMinVersion: Boolean

    /**
     * Adds all LWJGL modules to the project.
     */
    fun everything()

    /**
     * Adds modules commonly used to get started with LWJGL.
     *
     * - [Modules.assimp]: Assimp module for loading 3D models.
     * - [Modules.bgfx]: bgfx module for rendering.
     * - [Modules.glfw]: GLFW module for window management and input handling.
     * - [Modules.nanovg]: NanoVG module for vector graphics rendering.
     * - [Modules.nuklear]: Nuklear module for immediate mode GUI.
     * - [Modules.openal]: OpenAL module for audio.
     * - [Modules.opengl]: OpenGL module for graphics rendering.
     * - [Modules.par]: Par module for various parametric shapes.
     * - [Modules.stb]: stb module for image loading and other utilities.
     * - [Modules.vulkan]: Vulkan module for graphics rendering.
     *
     */
    fun gettingStarted()

    /**
     * Adds modules commonly used for minimal OpenGL usage.
     *
     * - [Modules.assimp]: Assimp module for loading 3D models.
     * - [Modules.glfw]: GLFW module for window management and input handling.
     * - [Modules.openal]: OpenAL module for audio.
     * - [Modules.opengl]: OpenGL module for graphics rendering.
     * - [Modules.stb]: stb module for image loading and other utilities.
     */
    fun minimalOpenGL()

    /**
     * Adds modules commonly used for minimal OpenGL ES usage.
     *
     * - [Modules.assimp]: Assimp module for loading 3D models.
     * - [Modules.egl]: EGL module for OpenGL ES context management.
     * - [Modules.glfw]: GLFW module for window management and input handling.
     * - [Modules.openal]: OpenAL module for audio.
     * - [Modules.opengles]: OpenGL ES module for graphics rendering.
     * - [Modules.stb]: stb module for image loading and other utilities.
     */
    fun minimalOpenGLES()

    /**
     * Adds modules commonly used for minimal Vulkan usage.
     *
     * - [Modules.assimp]: Assimp module for loading 3D models.
     * - [Modules.glfw]: GLFW module for window management and input handling.
     * - [Modules.openal]: OpenAL module for audio.
     * - [Modules.vulkan]: Vulkan module for graphics rendering.
     * - [Modules.stb]: stb module for image loading and other utilities.
     */
    fun minimalVulkan()
}