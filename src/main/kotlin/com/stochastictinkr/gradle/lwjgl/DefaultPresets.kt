package com.stochastictinkr.gradle.lwjgl

internal class DefaultPresets : Presets, Modules by Modules {
    internal var modulesFromPresets = mutableSetOf<Module>()

    override var filterMinVersion: Boolean = true

    override fun everything() {
        modulesFromPresets += allModules
    }

    override fun gettingStarted() {
        modulesFromPresets += setOf(
            assimp, bgfx, glfw, nanovg, nuklear,
            openal, opengl, par, stb, vulkan
        )
    }

    override fun minimalOpenGL() {
        modulesFromPresets += setOf(assimp, glfw, openal, opengl, stb)
    }

    override fun minimalOpenGLES() {
        modulesFromPresets += setOf(assimp, egl, glfw, openal, opengles, stb)
    }

    override fun minimalVulkan() {
        modulesFromPresets += setOf(assimp, glfw, openal, stb, vulkan)
    }

    internal val isEmpty: Boolean
        get() = modulesFromPresets.isEmpty()

    internal fun forVersion(selectedVersion: String): Collection<Module> {
        return if (!filterMinVersion) modulesFromPresets
        else modulesFromPresets.filter {
            VersionUtils.meetsVersionRequirement(minVersion = it.minVersion, selectedVersion = selectedVersion)
        }

    }
}