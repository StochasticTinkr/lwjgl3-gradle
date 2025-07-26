package com.stochastictinkr.gradle.lwjgl

import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.logging.*
import org.gradle.api.provider.*

internal class DefaultLwjglDependencyHandler : LwjglDependencyHandler {
    override var group: String = "org.lwjgl"

    override var version: String = "3.3.6"
        get() = versionProvider?.get() ?: field
        set(value) {
            field = value
            versionProvider = null
        }

    private var versionProvider: Provider<String>? = null

    override var implementationConfiguration: String = "implementation"

    override var runtimeConfiguration: String = "runtimeOnly"

    private var nativePlatforms: List<Platform>? = null

    override var modules: MutableSet<Module> = mutableSetOf()

    override fun version(version: String) {
        this.version = version
    }

    override fun version(version: Provider<String>) {
        this.versionProvider = version
    }

    override fun nativePlatforms(vararg platforms: Platform) {
        nativePlatforms = platforms.toList()
    }

    override fun customNativePlatforms(vararg platforms: String) {
        nativePlatforms = platforms.map { Platform(it) }
    }

    override fun nativePlatforms(platforms: Iterable<Platform>) {
        nativePlatforms = platforms.toList()
    }

    override fun useAllNativePlatforms() {
        nativePlatforms = Platforms.allPlatforms
    }

    private val defaultPresets = DefaultPresets()

    override val presets: Presets = defaultPresets

    override fun preset(preset: Presets.() -> Unit) = presets.preset()

    override fun modules(vararg modules: Module) {
        this.modules.addAll(modules)
    }

    internal fun Project.addDependencies() {
        if (modules.isEmpty() && defaultPresets.isEmpty) {
            logger.warnNoModules()
            return
        }
        val version = this@DefaultLwjglDependencyHandler.version
        val group = this@DefaultLwjglDependencyHandler.group

        val selectedModules = setOf(Modules.core) + defaultPresets.forVersion(version) + modules
        selectedModules.forEach {
            if (!VersionUtils.meetsVersionRequirement(
                    minVersion = it.minVersion,
                    selectedVersion = version
                )
            ) {
                logger.warnMinVersion(it, version)
            }
        }

        val implementationDependencies =
            selectedModules.map { module ->
                createDependency(group, module.artifact, version)
            }

        val nativePlatforms = nativePlatforms ?: run {
            Platforms.runningPlatform?.let(::listOf)
                ?: error("Unrecognized or unsupported Operating system. Please set `nativePlatforms` manually")
        }

        val platformClassifiers = nativePlatforms.map { "natives-${it.name}" }

        val runtimeOnlyDependencies =
            selectedModules.filter { it.hasNatives }
                .flatMap { module ->
                    platformClassifiers.map { classifier ->
                        createDependency(group, module.artifact, version, classifier)
                    }
                }

        dependencies.addAll(implementationConfiguration, implementationDependencies)
        dependencies.addAll(runtimeConfiguration, runtimeOnlyDependencies)
    }
}

private fun Project.createDependency(
    group: String,
    name: String,
    version: String,
    classifier: String? = null,
) = dependencyFactory.create(
    /* group = */ group,
    /* name = */ name,
    /* version = */ version,
    /* classifier = */ classifier,
    /* extension = */ null,
)

internal fun Logger.warnMinVersion(
    module: Module,
    version: String,
) = warn(
    "Module '${module.name}' minimum version is '${module.minVersion}', but request version is '$version'."
)

internal fun Logger.warnNoModules() = warn(
    "No LWJGL modules selected. Please add modules with `lwjgl.modules` use a preset like `lwjgl.presets.gettingStarted()`."
)

private fun DependencyHandler.addAll(
    configurationName: String,
    dependencies: Iterable<ExternalModuleDependency>,
) = dependencies.forEach { add(configurationName, it) }

