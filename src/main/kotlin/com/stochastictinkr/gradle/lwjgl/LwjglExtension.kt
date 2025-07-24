package com.stochastictinkr.gradle.lwjgl

import org.gradle.api.artifacts.dsl.*
import org.gradle.api.logging.*
import java.util.*


@Suppress("unused")
open class LwjglExtension internal constructor(
    private val dependencyFactory: DependencyFactory,
    private val dependencies: DependencyHandler,
    private val logger: Logger,
    internal val platformDetector: PlatformDetector,
) {
    constructor(
        dependencyFactory: DependencyFactory,
        dependencies: DependencyHandler,
        logger: Logger,
    ) : this(
        dependencyFactory,
        dependencies,
        logger,
        PlatformDetector()
    )

    var group: String = "org.lwjgl"
    var version: String = "3.3.6"
    private var _nativePlatforms: List<Platform>? = null

    // Core
    val core = Module("core", artifact = "lwjgl")

    // Khronos modules
    val egl = Module("egl", hasNatives = false)
    val ktx = Module("ktx", minVersion = "3.3.2-SNAPSHOT")
    val opencl = Module("opencl", hasNatives = false)
    val opengl = Module("opengl")
    val opengles = Module("opengles")
    val openxr = Module("openxr", minVersion = "3.3.1")
    val vulkan = Module("vulkan")

    // Display and Input
    val glfw = Module("glfw")

    // val sdl = Module("sdl") // SDL support is not yet released.
    val jawt = Module("jawt", hasNatives = false)
    val nfd = Module("nfd")
    val tinyfd = Module("tinyfd")

    // Audio
    val fmod = Module("fmod", hasNatives = false, minVersion = "3.3.2-SNAPSHOT")
    val openal = Module("openal")
    val opus = Module("opus", minVersion = "3.2.1")

    // Graphics
    val assimp = Module("assimp", minVersion = "3.1.1")
    val bgfx = Module("bgfx")
    val freetype = Module("freetype", minVersion = "3.3.2-SNAPSHOT")
    val harfbuzz = Module("harfbuzz", minVersion = "3.3.2-SNAPSHOT")
    val meshoptimizer = Module("meshoptimizer", minVersion = "3.3.0")
    val msdfgen = Module("msdfgen", minVersion = "3.3.4")
    val nanovg = Module("nanovg")
    val nuklear = Module("nuklear")
    val par = Module("par")
    val shaderc = Module("shaderc", minVersion = "3.2.3")
    val spvc = Module("spvc", minVersion = "3.3.0")
    val tinyexr = Module("tinyexr", minVersion = "3.1.2")
    val tootle = Module("tootle", minVersion = "3.1.5")
    val vma = Module("vma", minVersion = "3.2.0")
    val yoga = Module("yoga", minVersion = "3.1.2")

    // AR/VR
    val openvr = Module("openvr", minVersion = "3.1.2")
    val ovr = Module("ovr", minVersion = "3.1.2")

    // stb
    val stb = Module("stb")

    // other
    val cuda = Module("cuda", hasNatives = false, minVersion = "3.2.1")
    val hwloc = Module("hwloc", minVersion = "3.3.2-SNAPSHOT")
    val jemalloc = Module("jemalloc")
    val libdivide = Module("libdivide", minVersion = "3.2.1")
    val llvm = Module("llvm", minVersion = "3.2.1")
    val lmdb = Module("lmdb")
    val lz4 = Module("lz4", minVersion = "3.1.4")
    val meow = Module("meow", minVersion = "3.2.1")
    val odbc = Module("odbc", hasNatives = false, minVersion = "3.1.4")
    val remotery = Module("remotery", minVersion = "3.1.4")
    val rpmalloc = Module("rpmalloc", minVersion = "3.1.3")
    val xxhash = Module("xxhash")
    val sse = Module("sse")
    val zstd = Module("zstd", minVersion = "3.1.4")

    private val allModules = listOf(
        core, assimp, bgfx, cuda, egl, fmod, freetype, glfw, harfbuzz,
        hwloc, jawt, jemalloc, ktx, libdivide, llvm, lmdb, lz4,
        meow, meshoptimizer, nanovg, nfd, nuklear, odbc,
        openal, opencl, opengl, opengles, openvr,
        openxr, opus, par, remotery,
        rpmalloc, shaderc, spvc, sse,
        stb, tinyexr, tinyfd,
        tootle, vma, vulkan,
        xxhash, yoga, zstd
    )

    var modules: MutableList<Module> = mutableListOf()
    private var modulesFromPresets: Set<Module> = emptySet()

    var nativePlatforms: List<Platform>
        get() {
            if (_nativePlatforms == null) {
                val detected = runningPlatform
                    ?: error("Unrecognized or unsupported Operating system. Please set `nativePlatforms` manually")
                _nativePlatforms = listOf(detected)
            }
            return _nativePlatforms!!
        }
        set(value) {
            _nativePlatforms = value
        }

    fun nativePlatforms(vararg platforms: Platform) {
        _nativePlatforms = platforms.toList()
    }

    fun customNativePlatforms(vararg platforms: String) {
        _nativePlatforms = platforms.map { Platform(it) }
    }

    fun nativePlatforms(platforms: Iterable<Platform>) {
        _nativePlatforms = platforms.toList()
    }

    fun useAllNativePlatforms() {
        _nativePlatforms = allPlatforms
    }

    val linuxArm64 = Platform.linuxArm64
    val linuxArm32 = Platform.linuxArm32
    val linux = Platform.linux
    val macosArm64 = Platform.macosArm64
    val macos = Platform.macos
    val windowsArm64 = Platform.windowsArm64
    val windows = Platform.windows
    val windowsX86 = Platform.windowsX86

    val allPlatforms = listOf(
        linuxArm64,
        linuxArm32,
        linux,
        macosArm64,
        macos,
        windowsArm64,
        windows,
        windowsX86,
    )

    inner class Presets internal constructor() {
        var filterMinVersion: Boolean = true
        fun everything() {
            modulesFromPresets += allModules
        }

        fun gettingStarted() {
            modulesFromPresets += setOf(
                core, assimp, bgfx, glfw, nanovg, nuklear,
                openal, opengl, par, stb, vulkan
            )
        }

        fun minimalOpenGL() {
            modulesFromPresets += setOf(core, assimp, glfw, openal, opengl, stb)
        }

        fun minimalOpenGLES() {
            modulesFromPresets += setOf(core, assimp, egl, glfw, openal, opengles, stb)
        }

        fun minimalVulkan() {
            modulesFromPresets += setOf(core, assimp, glfw, openal, stb, vulkan)
        }
    }

    val presets = Presets()

    fun preset(preset: Presets.() -> Unit) {
        presets.preset()
    }

    fun modules(vararg modules: Module) {
        this.modules.addAll(modules)
    }

    val runningPlatform by lazy {
        platformDetector.platformFrom(
            System.getProperty("os.arch"),
            System.getProperty("os.name").lowercase(Locale.ROOT)
        )
    }

    internal val selectedModules: Set<Module>
        get() {
            val filteredPresets =
                if (presets.filterMinVersion) modulesFromPresets.filter(::meetsVersionRequirement)
                else modulesFromPresets

            return setOf(core) +
                    filteredPresets +
                    modules
        }

    private val versionRegex = Regex("""(\d+)\.(\d+)(?:\.(\d+))?(-SNAPSHOT)?""")

    private fun meetsVersionRequirement(
        module: Module,
    ): Boolean {
        val (majorMin, minorMin, patchMin, snapshotMin) = versionRegex.matchEntire(module.minVersion)?.destructured
            ?: return false
        val (majorActual, minorActual, patchActual, snapshotActual) = versionRegex.matchEntire(version)?.destructured
            ?: return false

        val minMajor = majorMin.toInt()
        val minMinor = minorMin.toInt()
        val minPatch = patchMin.toInt()
        val actualMajor = majorActual.toInt()
        val actualMinor = minorActual.toInt()
        val actualPatch = patchActual.toInt()
        // Snapshots are considered lower than the release version
        val minSnapshot = if (snapshotMin.isNotEmpty()) 0 else 1
        val actualSnapshot = if (snapshotActual.isNotEmpty()) 0 else 1
        return when {
            actualMajor > minMajor -> true
            actualMajor < minMajor -> false
            actualMinor > minMinor -> true
            actualMinor < minMinor -> false
            actualPatch > minPatch -> true
            actualPatch < minPatch -> false
            else -> actualSnapshot >= minSnapshot
        }
    }


    internal val implementationDependencies
        get() = selectedModules.map { module ->
            dependencyFactory.create(
                /* group = */ group,
                /* name = */ module.artifact,
                /* version = */ version,
                /* classifier = */ null,
                /* extension = */ null,
            )
        }

    internal val runtimeOnlyDependencies
        get() = selectedModules.filter { it.hasNatives }
            .flatMap { module ->
                nativePlatforms.map { platform ->
                    dependencyFactory.create(
                        /* group = */ group,
                        /* name = */ module.artifact,
                        /* version = */ version,
                        /* classifier = */ "natives-${platform.name}",
                        /* extension = */ null,
                    )
                }
            }
    var runtimeConfiguration: String = "runtimeOnly"
    var implementationConfiguration: String = "implementation"

    internal fun addDependencies() {
        if (modules.isEmpty() && modulesFromPresets.isEmpty()) {
            logger.warn(
                "No LWJGL modules selected. " +
                        "Please add modules with `lwjgl.modules` use a preset like `lwjgl.presets.gettingStarted()`."
            )
            return
        }
        selectedModules.forEach {
            if (!meetsVersionRequirement(it)) {
                logger.warn(
                    "Module '${it.name}' minimum version is '${it.minVersion}', but request version is '$version'."
                )
            }
        }
        runtimeOnlyDependencies.forEach {
            dependencies.add(runtimeConfiguration, it)
        }
        implementationDependencies.forEach {
            dependencies.add(implementationConfiguration, it)
        }
    }
}

