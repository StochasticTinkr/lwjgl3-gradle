package com.stochastictinkr.gradle.lwjgl

import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.logging.*

internal class DefaultLwjglDependencyHandler : LwjglDependencyHandler {

    override var group: String = "org.lwjgl"

    override var version: String = "3.3.6"

    override var implementationConfiguration: String = "implementation"

    override var runtimeConfiguration: String = "runtimeOnly"

    private var nativePlatforms: List<Platform>? = null

    override var modules: MutableSet<Module> = mutableSetOf()

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

    internal fun addDependencies(
        dependencyFactory: DependencyFactory,
        logger: Logger,
        dependencies: DependencyHandler,
    ) {
        if (modules.isEmpty() && defaultPresets.isEmpty) {
            logger.warnNoModules()
            return
        }
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

        val implementationDependencies = selectedModules.map { module ->
            dependencyFactory.create(
                /* group = */ group,
                /* name = */ module.artifact,
                /* version = */ version,
                /* classifier = */ null,
                /* extension = */ null,
            )
        }

        val nativePlatforms = nativePlatforms ?: run {
            Platforms.runningPlatform?.let(::listOf)
                ?: error("Unrecognized or unsupported Operating system. Please set `nativePlatforms` manually")
        }

        val runtimeOnlyDependencies = selectedModules.filter { it.hasNatives }
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

        dependencies.addAll(implementationConfiguration, implementationDependencies)
        dependencies.addAll(runtimeConfiguration, runtimeOnlyDependencies)
    }

}

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
    dependencies: Collection<ExternalModuleDependency>,
) = dependencies.forEach { add(configurationName, it) }

