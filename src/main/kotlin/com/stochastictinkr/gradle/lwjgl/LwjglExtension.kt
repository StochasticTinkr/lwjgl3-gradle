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

    /**
     * The Maven group for LWJGL artifacts.
     */
    var group: String = "org.lwjgl"

    /**
     * The LWJGL version to use.
     */
    var version: String = "3.3.6"

    /**
     * The configuration used for LWJGL implementation (compile and runtime) dependencies.
     */
    var implementationConfiguration: String = "implementation"

    /**
     * The configuration used for LWJGL runtime-only dependencies (natives).
     */
    var runtimeConfiguration: String = "runtimeOnly"

    private var _nativePlatforms: List<Platform>? = null

    /**
     * The core LWJGL module, always included.
     */
    val core = Module("core", artifact = "lwjgl")

    // Khronos modules

    /**
     * An interface between Khronos rendering APIs such as OpenGL ES or OpenVG and the underlying native platform window system.
     *
     * [EGL](https://www.khronos.org/egl)
     */
    val egl = Module("egl", hasNatives = false)

    /**
     * A lightweight container for textures for OpenGL®, Vulkan® and other GPU APIs.
     *
     * [KTX (Khronos Texture)](https://www.khronos.org/ktx/)
     */
    val ktx = Module("ktx", minVersion = "3.3.2-SNAPSHOT")

    /**
     * An open, royalty-free standard for cross-platform, parallel programming of diverse processors found in personal computers, servers, mobile devices and embedded platforms.
     *
     * [OpenCL](https://www.khronos.org/opencl/)
     */
    val opencl = Module("opencl", hasNatives = false)

    /**
     * The most widely adopted 2D and 3D graphics API in the industry, bringing thousands of applications to a wide variety of computer platforms.
     *
     * [OpenGL](https://www.khronos.org/opengl/)
     */
    val opengl = Module("opengl")

    /**
     * A royalty-free, cross-platform API for full-function 2D and 3D graphics on embedded systems - including consoles, phones, appliances and vehicles.
     *
     * [OpenGL ES](https://www.khronos.org/opengles/)
     */
    val opengles = Module("opengles")

    /**
     * A royalty-free, open standard that provides high-performance access to Augmented Reality (AR) and Virtual Reality (VR)—collectively known as XR—platforms and devices.
     *
     * [OpenXR](https://www.khronos.org/openxr/)
     */
    val openxr = Module("openxr", minVersion = "3.3.1")

    /**
     * A new generation graphics and compute API that provides high-efficiency, cross-platform access to modern GPUs used in a wide variety of devices from PCs and consoles to mobile phones and embedded platforms.
     *
     * [Vulkan](https://www.khronos.org/vulkan/)
     */
    val vulkan = Module("vulkan")

    // Display and Input

    /**
     * Create multiple windows, handle user input (keyboard, mouse, gaming peripherals) and manage contexts. Also features multi-monitor support, clipboard access, file drag-n-drop, and much more.
     *
     * [GLFW](https://www.glfw.org/)
     */
    val glfw = Module("glfw")

    // val sdl = Module("sdl") // SDL support is not yet released.

    /**
     * The AWT native interface.
     *
     * [JAWT](http://docs.oracle.com/javase/8/docs/technotes/guides/awt/AWT_Native_Interface.html)
     */
    val jawt = Module("jawt", hasNatives = false)

    /**
     * A small C library that portably invokes native file open, folder select and file save dialogs.
     *
     * [Native File Dialog Extended](https://github.com/btzy/nativefiledialog-extended)
     */
    val nfd = Module("nfd")

    /**
     * A native dialog library.
     *
     * [tinyfd](https://sourceforge.net/projects/tinyfiledialogs/)
     */
    val tinyfd = Module("tinyfd")

    // Audio

    /**
     * An end-to-end solution for adding sound and music to any game.
     *
     * [FMOD](https://www.fmod.com)
     */
    val fmod = Module("fmod", hasNatives = false, minVersion = "3.3.2-SNAPSHOT")

    /**
     * A cross-platform 3D audio API appropriate for use with gaming applications and many other types of audio applications.
     *
     * [OpenAL](https://www.openal.org/)
     *
     * [OpenAL Soft](https://openal-soft.org/)
     */
    val openal = Module("openal")

    /**
     * A totally open, royalty-free, highly versatile audio codec.
     *
     * [Opus](https://opus-codec.org/)
     */
    val opus = Module("opus", minVersion = "3.2.1")

    // Graphics

    /**
     * A portable Open Source library to import various well-known 3D model formats in a uniform manner.
     *
     * [Assimp](https://www.assimp.org/)
     */
    val assimp = Module("assimp", minVersion = "3.1.1")

    /**
     * Cross-platform, graphics API agnostic, “Bring Your Own Engine/Framework” style rendering library, licensed under permissive BSD-2 clause open source license.
     *
     * [bgfx](https://bkaradzic.github.io/bgfx/)
     */
    val bgfx = Module("bgfx")

    /**
     * A freely available software library to render fonts.
     *
     * [FreeType](https://freetype.org/)
     */
    val freetype = Module("freetype", minVersion = "3.3.2-SNAPSHOT")

    /**
     * A text shaping library that allows programs to convert a sequence of Unicode input into properly formatted and positioned glyph output — for any writing system and language.
     *
     * [HarfBuzz](https://harfbuzz.github.io/)
     */
    val harfbuzz = Module("harfbuzz", minVersion = "3.3.2-SNAPSHOT")

    /**
     * A mesh optimization library that makes meshes smaller and faster to render.
     *
     * [meshoptimizer](https://github.com/zeux/meshoptimizer)
     */
    val meshoptimizer = Module("meshoptimizer", minVersion = "3.3.0")

    /**
     * A multi-channel signed distance field generator.
     *
     * [msdfgen](https://github.com/Chlumsky/msdfgen)
     */
    val msdfgen = Module("msdfgen", minVersion = "3.3.4")

    /**
     * A small antialiased vector graphics rendering library for OpenGL.
     *
     * [NanoVG](https://github.com/memononen/nanovg)
     *
     * Also includes [NanoSVG](https://github.com/memononen/nanosvg) "A simple stupid SVG parser."
     */
    val nanovg = Module("nanovg")

    /**
     * A minimal state immediate mode graphical user interface toolkit written in ANSI C and licensed under public domain.
     *
     * [Nuklear](https://github.com/vurtun/nuklear)
     */
    val nuklear = Module("nuklear")

    /**
     * The bindings of utilities frm prideout.net:
     *
     * Generates triangle meshes for spheres, rounded boxes, and capsules.
     *
     * [par_octasphere](https://prideout.net/blog/octasphere)
     *
     * Generate parametric surfaces and other simple shapes.
     *
     * [par_shapes](https://prideout.net/shapes)
     *
     * Triangulate wide lines and curves.
     *
     * [par_streamlines](https://prideout.net/blog/par_streamlines/)
     */
    val par = Module("par")

    /**
     * A collection of libraries for shader compilation.
     *
     * [Shaderc](https://github.com/google/shaderc)
     */
    val shaderc = Module("shaderc", minVersion = "3.2.3")

    /**
     * A library for performing reflection on SPIR-V and disassembling SPIR-V back to high level languages.
     *
     * [SPIRV-Cross](https://github.com/KhronosGroup/SPIRV-Cross)
     */
    val spvc = Module("spvc", minVersion = "3.3.0")

    /**
     * A small, single header-only library to load and save OpenEXR(.exr) images.
     *
     * [Tiny OpenEXR](https://github.com/syoyo/tinyexr)
     */
    val tinyexr = Module("tinyexr", minVersion = "3.1.2")

    val tootle = Module("tootle", minVersion = "3.1.5")

    /**
     * An easy to integrate Vulkan memory allocation library.
     *
     * [Vulkan Memory Allocator](https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator)
     */
    val vma = Module("vma", minVersion = "3.2.0")

    /**
     * An open-source, cross-platform layout library that implements Flexbox.
     *
     * [Yoga](https://facebook.github.io/yoga/)
     */
    val yoga = Module("yoga", minVersion = "3.1.2")

    // AR/VR

    val openvr = Module("openvr", minVersion = "3.1.2")

    val ovr = Module("ovr", minVersion = "3.1.2")

    // stb

    /**
     * [stb](https://github.com/nothings/stb) single-file public domain libraries for C/C++.
     * Includes:
     * |                  |                                                                                 |
     * |------------------|---------------------------------------------------------------------------------|
     * | stb_easy_font    | Quick-and-dirty easy-to-deploy bitmap font for printing frame rate, etc.        |
     * | stb_image        | Image loading/decoding from file/memory: JPG, PNG, TGA, BMP, PSD, GIF, HDR, PIC |
     * | stb_image_resize | Resize images larger/smaller with good quality.                                 |
     * | stb_image_write  | Image writing to disk: PNG, TGA, BMP                                            |
     * | stb_perlin       | Revised Perlin noise (3D input, 1D output).                                     |
     * | stb_rect_pack    | Simple 2D rectangle packer with decent quality.                                 |
     * | stb_truetype     | Parse, decode, and rasterize characters from truetype fonts.                    |
     * | stb_vorbis       | Decode ogg vorbis files from file/memory to float/16-bit signed output.         |
     */
    val stb = Module("stb")

    // other
    // TODO: Add documentation links for these modules. They were missing from the original LWJGL code.

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

    /**
     * The list of LWJGL module dependencies to include in the project.
     */
    var modules: MutableList<Module> = mutableListOf()
    private var modulesFromPresets: Set<Module> = emptySet()

    /**
     * The list of native platforms to include in the project.
     * If not set, it will be automatically detected based on the current OS.
     */
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

    /**
     * Sets the native platforms to use.
     */
    fun nativePlatforms(vararg platforms: Platform) {
        _nativePlatforms = platforms.toList()
    }

    /**
     * Sets the native platforms to use by their names. Useful if the platform is not one of the predefined ones.
     */
    fun customNativePlatforms(vararg platforms: String) {
        _nativePlatforms = platforms.map { Platform(it) }
    }

    /**
     * Sets the native platforms to use.
     */
    fun nativePlatforms(platforms: Iterable<Platform>) {
        _nativePlatforms = platforms.toList()
    }

    /**
     * Includes natives for all supported native platforms.
     * See [allPlatforms] for the list of supported platforms.
     */
    fun useAllNativePlatforms() {
        _nativePlatforms = allPlatforms
    }

    /**
     * Linux ARM64 platform.
     */
    val linuxArm64 = Platform.linuxArm64

    /**
     * Linux ARM32 platform.
     */
    val linuxArm32 = Platform.linuxArm32

    /**
     * Linux platform.
     */
    val linux = Platform.linux

    /**
     * macOS ARM64 platform.
     */
    val macosArm64 = Platform.macosArm64

    /**
     * macOS platform.
     */
    val macos = Platform.macos

    /**
     * Windows ARM64 platform.
     */
    val windowsArm64 = Platform.windowsArm64

    /**
     * Windows platform.
     */
    val windows = Platform.windows

    /**
     * Windows x86 platform.
     */
    val windowsX86 = Platform.windowsX86


    /**
     * A list of all supported native platforms.
     * - [linuxArm64]: Linux ARM64 platform.
     * - [linuxArm32]: Linux ARM32 platform.
     * - [linux]: Linux platform.
     * - [macosArm64]: macOS ARM64 platform.
     * - [macos]: macOS platform.
     * - [windowsArm64]: Windows ARM64 platform.
     * - [windows]: Windows platform.
     * - [windowsX86]: Windows x86 platform.
     */
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

        /**
         * Whether to filter modules based on their minimum version.
         *
         * When true, modules that do not meet the minimum version requirement will be excluded.
         */
        var filterMinVersion: Boolean = true

        /**
         * Adds all LWJGL modules to the project.
         */
        fun everything() {
            modulesFromPresets += allModules
        }

        /**
         * Adds modules commonly used to get started with LWJGL.
         *
         * - [assimp]: Assimp module for loading 3D models.
         * - [bgfx]: bgfx module for rendering.
         * - [glfw]: GLFW module for window management and input handling.
         * - [nanovg]: NanoVG module for vector graphics rendering.
         * - [nuklear]: Nuklear module for immediate mode GUI.
         * - [openal]: OpenAL module for audio.
         * - [opengl]: OpenGL module for graphics rendering.
         * - [par]: Par module for various parametric shapes.
         * - [stb]: stb module for image loading and other utilities.
         * - [vulkan]: Vulkan module for graphics rendering.
         *
         */
        fun gettingStarted() {
            modulesFromPresets += setOf(
                assimp, bgfx, glfw, nanovg, nuklear,
                openal, opengl, par, stb, vulkan
            )
        }

        /**
         * Adds modules commonly used for minimal OpenGL usage.
         *
         * - [assimp]: Assimp module for loading 3D models.
         * - [glfw]: GLFW module for window management and input handling.
         * - [openal]: OpenAL module for audio.
         * - [opengl]: OpenGL module for graphics rendering.
         * - [stb]: stb module for image loading and other utilities.
         */
        fun minimalOpenGL() {
            modulesFromPresets += setOf(assimp, glfw, openal, opengl, stb)
        }

        /**
         * Adds modules commonly used for minimal OpenGL ES usage.
         *
         * - [assimp]: Assimp module for loading 3D models.
         * - [egl]: EGL module for OpenGL ES context management.
         * - [glfw]: GLFW module for window management and input handling.
         * - [openal]: OpenAL module for audio.
         * - [opengles]: OpenGL ES module for graphics rendering.
         * - [stb]: stb module for image loading and other utilities.
         */
        fun minimalOpenGLES() {
            modulesFromPresets += setOf(assimp, egl, glfw, openal, opengles, stb)
        }

        /**
         * Adds modules commonly used for minimal Vulkan usage.
         *
         * - [assimp]: Assimp module for loading 3D models.
         * - [glfw]: GLFW module for window management and input handling.
         * - [openal]: OpenAL module for audio.
         * - [vulkan]: Vulkan module for graphics rendering.
         * - [stb]: stb module for image loading and other utilities.
         */
        fun minimalVulkan() {
            modulesFromPresets += setOf(assimp, glfw, openal, stb, vulkan)
        }
    }

    /**
     * Presets are used to quickly add commonly used LWJGL modules.
     * See the following methods for available presets:
     * - [Presets.everything]: Adds all LWJGL modules.
     * - [Presets.gettingStarted]: Adds modules commonly used to get started with LWJGL.
     * - [Presets.minimalOpenGL]: Adds modules commonly used for minimal OpenGL usage.
     * - [Presets.minimalOpenGLES]: Adds modules commonly used for minimal OpenGL ES usage.
     * - [Presets.minimalVulkan]: Adds modules commonly used for minimal Vulkan usage.
     */
    val presets = Presets()

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
    fun preset(preset: Presets.() -> Unit) {
        presets.preset()
    }

    /**
     * Adds modules dependencies to the project.
     */
    fun modules(vararg modules: Module) {
        this.modules.addAll(modules)
    }

    /**
     * Detects the current platform based on the system properties.
     * Returns the platform if it is recognized, otherwise returns null.
     */
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
