package com.stochastictinkr.gradle.lwjgl

import org.gradle.api.*
import org.gradle.kotlin.dsl.*
import javax.inject.*

class LwjglPlugin internal constructor(
    internal val handler: DefaultLwjglDependencyHandler,
) : Plugin<Project> {
    @Suppress("unused")
    @Inject
    constructor() : this(DefaultLwjglDependencyHandler())

    override fun apply(project: Project) {
        project.createLwjglExtension(handler)
        project.afterEvaluate { addLwjglDependencies(handler) }
    }
}

internal fun Project.addLwjglDependencies(handler: DefaultLwjglDependencyHandler) {
    with(handler) { addDependencies() }
}

internal fun Project.createLwjglExtension(handler: DefaultLwjglDependencyHandler) {
    dependencies.extensions.create<LwjglExtension>("lwjgl", handler, Modules, Platforms)
}


