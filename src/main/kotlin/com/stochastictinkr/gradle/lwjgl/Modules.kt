package com.stochastictinkr.gradle.lwjgl

sealed interface Modules {

    /**
     * The core LWJGL module, always included.
     */
    val core: Module

    // Khronos modules

    /**
     * An interface between Khronos rendering APIs such as OpenGL ES or OpenVG and the underlying native platform window system.
     *
     * [EGL](https://www.khronos.org/egl)
     */
    val egl: Module

    /**
     * A lightweight container for textures for OpenGL®, Vulkan® and other GPU APIs.
     *
     * [KTX (Khronos Texture)](https://www.khronos.org/ktx/)
     */
    val ktx: Module

    /**
     * An open, royalty-free standard for cross-platform, parallel programming of diverse processors found in personal computers, servers, mobile devices and embedded platforms.
     *
     * [OpenCL](https://www.khronos.org/opencl/)
     */
    val opencl: Module

    /**
     * The most widely adopted 2D and 3D graphics API in the industry, bringing thousands of applications to a wide variety of computer platforms.
     *
     * [OpenGL](https://www.khronos.org/opengl/)
     */
    val opengl: Module

    /**
     * A royalty-free, cross-platform API for full-function 2D and 3D graphics on embedded systems - including consoles, phones, appliances and vehicles.
     *
     * [OpenGL ES](https://www.khronos.org/opengles/)
     */
    val opengles: Module

    /**
     * A royalty-free, open standard that provides high-performance access to Augmented Reality (AR) and Virtual Reality (VR)—collectively known as XR—platforms and devices.
     *
     * [OpenXR](https://www.khronos.org/openxr/)
     */
    val openxr: Module

    /**
     * A new generation graphics and compute API that provides high-efficiency, cross-platform access to modern GPUs used in a wide variety of devices from PCs and consoles to mobile phones and embedded platforms.
     *
     * [Vulkan](https://www.khronos.org/vulkan/)
     */
    val vulkan: Module

    // Display and Input

    /**
     * Create multiple windows, handle user input (keyboard, mouse, gaming peripherals) and manage contexts. Also features multi-monitor support, clipboard access, file drag-n-drop, and much more.
     *
     * [GLFW](https://www.glfw.org/)
     */
    val glfw: Module

    // val sdl: Module

    /**
     * The AWT native interface.
     *
     * [JAWT](http://docs.oracle.com/javase/8/docs/technotes/guides/awt/AWT_Native_Interface.html)
     */
    val jawt: Module

    /**
     * A small C library that portably invokes native file open, folder select and file save dialogs.
     *
     * [Native File Dialog Extended](https://github.com/btzy/nativefiledialog-extended)
     */
    val nfd: Module

    /**
     * A native dialog library.
     *
     * [tinyfd](https://sourceforge.net/projects/tinyfiledialogs/)
     */
    val tinyfd: Module

    // Audio

    /**
     * An end-to-end solution for adding sound and music to any game.
     *
     * [FMOD](https://www.fmod.com)
     */
    val fmod: Module

    /**
     * A cross-platform 3D audio API appropriate for use with gaming applications and many other types of audio applications.
     *
     * [OpenAL](https://www.openal.org/)
     *
     * [OpenAL Soft](https://openal-soft.org/)
     */
    val openal: Module

    /**
     * A totally open, royalty-free, highly versatile audio codec.
     *
     * [Opus](https://opus-codec.org/)
     */
    val opus: Module

    // Graphics

    /**
     * A portable Open Source library to import various well-known 3D model formats in a uniform manner.
     *
     * [Assimp](https://www.assimp.org/)
     */
    val assimp: Module

    /**
     * Cross-platform, graphics API agnostic, “Bring Your Own Engine/Framework” style rendering library, licensed under permissive BSD-2 clause open source license.
     *
     * [bgfx](https://bkaradzic.github.io/bgfx/)
     */
    val bgfx: Module

    /**
     * A freely available software library to render fonts.
     *
     * [FreeType](https://freetype.org/)
     */
    val freetype: Module

    /**
     * A text shaping library that allows programs to convert a sequence of Unicode input into properly formatted and positioned glyph output — for any writing system and language.
     *
     * [HarfBuzz](https://harfbuzz.github.io/)
     */
    val harfbuzz: Module

    /**
     * A mesh optimization library that makes meshes smaller and faster to render.
     *
     * [meshoptimizer](https://github.com/zeux/meshoptimizer)
     */
    val meshoptimizer: Module

    /**
     * A multi-channel signed distance field generator.
     *
     * [msdfgen](https://github.com/Chlumsky/msdfgen)
     */
    val msdfgen: Module

    /**
     * A small antialiased vector graphics rendering library for OpenGL.
     *
     * [NanoVG](https://github.com/memononen/nanovg)
     *
     * Also includes [NanoSVG](https://github.com/memononen/nanosvg) "A simple stupid SVG parser."
     */
    val nanovg: Module

    /**
     * A minimal state immediate mode graphical user interface toolkit written in ANSI C and licensed under public domain.
     *
     * [Nuklear](https://github.com/vurtun/nuklear)
     */
    val nuklear: Module

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
    val par: Module

    /**
     * A collection of libraries for shader compilation.
     *
     * [Shaderc](https://github.com/google/shaderc)
     */
    val shaderc: Module

    /**
     * A library for performing reflection on SPIR-V and disassembling SPIR-V back to high level languages.
     *
     * [SPIRV-Cross](https://github.com/KhronosGroup/SPIRV-Cross)
     */
    val spvc: Module

    /**
     * A small, single header-only library to load and save OpenEXR(.exr) images.
     *
     * [Tiny OpenEXR](https://github.com/syoyo/tinyexr)
     */
    val tinyexr: Module

    val tootle: Module

    /**
     * An easy to integrate Vulkan memory allocation library.
     *
     * [Vulkan Memory Allocator](https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator)
     */
    val vma: Module

    /**
     * An open-source, cross-platform layout library that implements Flexbox.
     *
     * [Yoga](https://facebook.github.io/yoga/)
     */
    val yoga: Module

    // AR/VR

    val openvr: Module

    val ovr: Module

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
    val stb: Module

    // other

    val cuda: Module

    val hwloc: Module

    val jemalloc: Module

    val libdivide: Module

    val llvm: Module

    val lmdb: Module

    val lz4: Module

    val meow: Module

    val odbc: Module

    val remotery: Module

    val rpmalloc: Module

    val xxhash: Module

    val sse: Module

    val zstd: Module

    val allModules: List<Module>

    companion object : Modules {
        private var modulesList: MutableList<Module>? = mutableListOf()
        private fun module(
            name: String,
            hasNatives: Boolean = true,
            minVersion: String = "3.1.0",
            artifact: String = "lwjgl-$name",
        ) = Module(name, hasNatives, minVersion, artifact).also(modulesList!!::add)

        override val core = Module("core", artifact = "lwjgl")

        // Khronos modules
        override val egl = module("egl", hasNatives = false)
        override val ktx = module("ktx", minVersion = "3.3.2-SNAPSHOT")
        override val opencl = module("opencl", hasNatives = false)
        override val opengl = module("opengl")
        override val opengles = module("opengles")
        override val openxr = module("openxr", minVersion = "3.3.1")

        override val vulkan = module("vulkan")

        // Display and Input
        override val glfw = module("glfw")
        override val jawt = module("jawt", hasNatives = false)
        override val nfd = module("nfd")
        override val tinyfd = module("tinyfd")

        // Audio
        override val fmod = module("fmod", hasNatives = false, minVersion = "3.3.2-SNAPSHOT")
        override val openal = module("openal")
        override val opus = module("opus", minVersion = "3.2.1")

        // Graphics
        override val assimp = module("assimp", minVersion = "3.1.1")
        override val bgfx = module("bgfx")
        override val freetype = module("freetype", minVersion = "3.3.2-SNAPSHOT")
        override val harfbuzz = module("harfbuzz", minVersion = "3.3.2-SNAPSHOT")
        override val meshoptimizer = module("meshoptimizer", minVersion = "3.3.0")
        override val msdfgen = module("msdfgen", minVersion = "3.3.4")
        override val nanovg = module("nanovg")
        override val nuklear = module("nuklear")
        override val par = module("par")
        override val shaderc = module("shaderc", minVersion = "3.2.3")
        override val spvc = module("spvc", minVersion = "3.3.0")
        override val tinyexr = module("tinyexr", minVersion = "3.1.2")
        override val tootle = module("tootle", minVersion = "3.1.5")
        override val vma = module("vma", minVersion = "3.2.0")
        override val yoga = module("yoga", minVersion = "3.1.2")

        // AR/VR
        override val openvr = module("openvr", minVersion = "3.1.2")
        override val ovr = module("ovr", minVersion = "3.1.2")

        // stb
        override val stb = module("stb")

        // other
        override val cuda = module("cuda", hasNatives = false, minVersion = "3.2.1")
        override val hwloc = module("hwloc", minVersion = "3.3.2-SNAPSHOT")
        override val jemalloc = module("jemalloc")
        override val libdivide = module("libdivide", minVersion = "3.2.1")
        override val llvm = module("llvm", minVersion = "3.2.1")
        override val lmdb = module("lmdb")
        override val lz4 = module("lz4", minVersion = "3.1.4")
        override val meow = module("meow", minVersion = "3.2.1")
        override val odbc = module("odbc", hasNatives = false, minVersion = "3.1.4")
        override val remotery = module("remotery", minVersion = "3.1.4")
        override val rpmalloc = module("rpmalloc", minVersion = "3.1.3")
        override val xxhash = module("xxhash")
        override val sse = module("sse")
        override val zstd = module("zstd", minVersion = "3.1.4")

        override val allModules = modulesList!!.toList().also { modulesList = null }
    }
}