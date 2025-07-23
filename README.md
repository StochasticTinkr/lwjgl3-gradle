# lwjgl3-gradle

A Gradle plugin to simplify adding LWJGL3 dependencies to your gradle project.

Inspired by the official [lwjgl3-gradle](https://github.com/LWJGL/lwjgl3-gradle), but with more flexibility.

# Features

- Easily add LWJGL3 dependencies with a simple DSL.
- Supports specifying LWJGL versions, modules, and native platforms.
- Allows customization of dependency configurations.
- Supports presets for common configurations (like minimal OpenGL or Vulkan setups).
- Supports version catalogs for managing LWJGL version.
- Allows overriding group IDs for LWJGL dependencies, useful for using forked versions.
- Supports adding custom LWJGL modules and platforms.
- Automatically detects the current platform for native dependencies.

# Usage

To use this plugin, add the following to your `build.gradle.kts` file:

```kotlin
plugins {
    id("com.stochastictinkr.lwjgl") version "1.0.0"
}
```

Then, you can add LWJGL3 dependencies using the `lwjgl` block inside your `dependencies` block:

```kotlin
dependencies {
    lwjgl {
        /* Specify the LWJGL version. Defaults to 3.3.6 */
        version = "3.3.6"
        /* Or you can specify the version directly from a version catalog: */
        version(libs.versions.lwjgl3)

        /* You can override the group ID of the LWJGL dependencies, if you're using a forked version: */
        group = "com.example.lwjgl" // Default is "org.lwjgl"

        /* You can specify a list of LWJGL modules you want to include: */
        modules(
            /* `core` is always included.*/
            shaderc,
            freetype,
        )

        /* You can also specify a set of modules using presets: */
        presets.minimalOpenGL()

        /* 
          You can specify multiple presets:
         */
        presets {
            minimalOpenGL()
            minimalVulkan()
        }

        /*
         By default, presets filter our modules that are not present in the selected version of LWJGL.
         You can disable this behavior by setting `presets.filterMinVersion = false`.
         */
        presets.filterMinVersion = false
        // or
        presets {
            filterMinVersion = false
        }

        /* You can override which native platforms (binaries) to include. */
        nativePlatforms(linuxArm64, windows)

        /* You can also specify all native platforms */
        useAllNativePlatforms()

        /* 
          If you don't specify any natives, it will default to auto-detecting the current platform.
          You can query the current platform from `runningPlatform`.
         */
        println("Current platform: $runningPlatform")

        /* 
          You can update which configurations will get the LWJGL dependencies.
          - Java dependencies are added to the configuration specified by `implementationConfiguration`
          - Native dependencies are added to the configuration specified by `runtimeConfiguration`.
          
          implementationConfiguration and runtimeConfiguration default to "implementation" and "runtimeOnly" respectively.
         */
        // implementationConfiguration = "implementation"
        // runtimeConfiguration = "runtimeOnly"

        /* You can use modules and platforms that are not included in this plugin. */
        modules(
            Module(
                name = "another-module",
                // hasNatives = true, // defaults to true
                // artifactId = "lwjgl-another-module" // defaults to "lwjgl-$name"
                // minVersion = "3.3.6" // Only used by presets, so not required to set.
            ),
        )
        platforms(Platform("another-platform"))
    }
}
```

## Supported modules

The plugin supports the following LWJGL modules:

| Name   |  | Description                             |
|:-------|:-|-----------------------------------------|
| `core` |  | The core LWJGL module, always included. |

#### Khronos APIs

| Name       | Library                                               | Description                                                                                                                                                                                                    |
|:-----------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `egl`      | [EGL](https://www.khronos.org/egl)                    | An interface between Khronos rendering APIs such as OpenGL ES or OpenVG and the underlying native platform window system.                                                                                      |
| `ktx`      | [KTX (Khronos Texture)](https://www.khronos.org/ktx/) | A lightweight container for textures for OpenGL®, Vulkan® and other GPU APIs.                                                                                                                                  |
| `opencl`   | [OpenCL](https://www.khronos.org/opencl/)             | An open, royalty-free standard for cross-platform, parallel programming of diverse processors found in personal computers, servers, mobile devices and embedded platforms.                                     |
| `opengl`   | [OpenGL](https://www.khronos.org/opengl/)             | The most widely adopted 2D and 3D graphics API in the industry, bringing thousands of applications to a wide variety of computer platforms.                                                                    |
| `opengles` | [OpenGL ES](https://www.khronos.org/opengles/)        | A royalty-free, cross-platform API for full-function 2D and 3D graphics on embedded systems - including consoles, phones, appliances and vehicles.                                                             |
| `openxr`   | [OpenXR](https://www.khronos.org/openxr/)             | A royalty-free, open standard that provides high-performance access to Augmented Reality (AR) and Virtual Reality (VR)—collectively known as XR—platforms and devices.                                         |
| `vulkan`   | [Vulkan](https://www.khronos.org/vulkan/)             | A new generation graphics and compute API that provides high-efficiency, cross-platform access to modern GPUs used in a wide variety of devices from PCs and consoles to mobile phones and embedded platforms. |

#### Display and Input

| Name     | Library                                                                                     | Description                                                                                                                                                                                                                                |
|:---------|---------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `glfw`   | [GLFW](https://www.glfw.org/)                                                               | Create multiple windows, handle user input (keyboard, mouse, gaming peripherals) and manage contexts. Also features multi-monitor support, clipboard access, file drag-n-drop, and [much more](http://www.glfw.org/docs/latest/news.html). |
| `jawt`   | [JAWT](http://docs.oracle.com/javase/8/docs/technotes/guides/awt/AWT_Native_Interface.html) | The AWT native interface.                                                                                                                                                                                                                  |
| `nfd`    | [Native File Dialog Extended](https://github.com/btzy/nativefiledialog-extended)            | A small C library that portably invokes native file open, folder select and file save dialogs.                                                                                                                                             |
| `tinyfd` | [tinyfd](https://sourceforge.net/projects/tinyfiledialogs/)                                 | A native dialog library.                                                                                                                                                                                                                   |

#### Audio

| Name     | Library                                 | Description                                                                                                            |
|:---------|-----------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| `fmod`   | [FMOD](https://www.fmod.com)            | An end-to-end solution for adding sound and music to any game.                                                         |
| `openal` | [OpenAL](https://www.openal.org/)       | A cross-platform 3D audio API appropriate for use with gaming applications and many other types of audio applications. |
| `openal` | [OpenAL Soft](https://openal-soft.org/) | An LGPL-licensed, cross-platform, software implementation of the OpenAL 3D audio API.                                  |
| `opus`   | [Opus](https://opus-codec.org/)         | A totally open, royalty-free, highly versatile audio codec.                                                            |

#### Graphics

| Name            | Library                                                                                      | Description                                                                                                                                                                   |
|:----------------|----------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `assimp`        | [Assimp](https://www.assimp.org/)                                                            | A portable Open Source library to import various well-known 3D model formats in a uniform manner.                                                                             |
| `bgfx`          | [bgfx](https://bkaradzic.github.io/bgfx/)                                                    | Cross-platform, graphics API agnostic, “Bring Your Own Engine/Framework” style rendering library, licensed under permissive BSD-2 clause open source license.                 |
| `freetype`      | [FreeType](https://freetype.org/)                                                            | A freely available software library to render fonts.                                                                                                                          |
| `harfbuzz`      | [HarfBuzz](https://harfbuzz.github.io/)                                                      | A text shaping library that allows programs to convert a sequence of Unicode input into properly formatted and positioned glyph output — for any writing system and language. |
| `meshoptimizer` | [meshoptimizer](https://github.com/zeux/meshoptimizer)                                       | A mesh optimization library that makes meshes smaller and faster to render.                                                                                                   |
| `msdfgen`       | [msdfgen](https://github.com/Chlumsky/msdfgen)                                               | A multi-channel signed distance field generator.                                                                                                                              |
| `nanovg`        | [NanoSVG](https://github.com/memononen/nanosvg)                                              | A simple stupid SVG parser.                                                                                                                                                   |
| `nanovg`        | [NanoVG](https://github.com/memononen/nanovg)                                                | A small antialiased vector graphics rendering library for OpenGL.                                                                                                             |
| `nuklear`       | [Nuklear](https://github.com/vurtun/nuklear)                                                 | A minimal state immediate mode graphical user interface toolkit written in ANSI C and licensed under public domain.                                                           |
| `par`           | [par_octasphere](https://prideout.net/blog/octasphere)                                       | Generates triangle meshes for spheres, rounded boxes, and capsules.                                                                                                           |
| `par`           | [par_shapes](https://prideout.net/shapes)                                                    | Generate parametric surfaces and other simple shapes.                                                                                                                         |
| `par`           | [par_streamlines](https://prideout.net/blog/par_streamlines/)                                | Triangulate wide lines and curves.                                                                                                                                            |
| `shaderc`       | [Shaderc](https://github.com/google/shaderc)                                                 | A collection of libraries for shader compilation.                                                                                                                             |
| `spvc`          | [SPIRV-Cross](https://github.com/KhronosGroup/SPIRV-Cross)                                   | A library for performing reflection on SPIR-V and disassembling SPIR-V back to high level languages.                                                                          |
| `tinyexr`       | [Tiny OpenEXR](https://github.com/syoyo/tinyexr)                                             | A small, single header-only library to load and save OpenEXR(.exr) images.                                                                                                    |
| `vma`           | [Vulkan Memory Allocator](https://github.com/GPUOpen-LibrariesAndSDKs/VulkanMemoryAllocator) | An easy to integrate Vulkan memory allocation library.                                                                                                                        |
| `yoga`          | [Yoga](https://facebook.github.io/yoga/)                                                     | An open-source, cross-platform layout library that implements Flexbox.                                                                                                        |

#### [stb](https://github.com/nothings/stb) - single-file public domain libraries for C/C++

| Name  | Library          | Description                                                                     |
|:------|------------------|---------------------------------------------------------------------------------|
| `stb` | stb_easy_font    | Quick-and-dirty easy-to-deploy bitmap font for printing frame rate, etc.        |
| `stb` | stb_image        | Image loading/decoding from file/memory: JPG, PNG, TGA, BMP, PSD, GIF, HDR, PIC |
| `stb` | stb_image_resize | Resize images larger/smaller with good quality.                                 |
| `stb` | stb_image_write  | Image writing to disk: PNG, TGA, BMP                                            |
| `stb` | stb_perlin       | Revised Perlin noise (3D input, 1D output).                                     |
| `stb` | stb_rect_pack    | Simple 2D rectangle packer with decent quality.                                 |
| `stb` | stb_truetype     | Parse, decode, and rasterize characters from truetype fonts.                    |
| `stb` | stb_vorbis       | Decode ogg vorbis files from file/memory to float/16-bit signed output.         |

#### Other

- `cuda`
- `hwloc`
- `jemalloc`
- `libdivide`
- `llvm`
- `lmdb`
- `lz4`
- `meow`
- `odbc`
- `openvr`
- `remotery`
- `sse`
- `tootle`
- `xxhash`
- `zstd`