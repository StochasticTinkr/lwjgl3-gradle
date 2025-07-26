package com.stochastictinkr.gradle.lwjgl


open class LwjglExtension internal constructor(
    handler: LwjglDependencyHandler = DefaultLwjglDependencyHandler(),
    modules: Modules = Modules,
    platforms: Platforms = Platforms,
) : LwjglDependencyHandler by handler, Modules by modules, Platforms by platforms

