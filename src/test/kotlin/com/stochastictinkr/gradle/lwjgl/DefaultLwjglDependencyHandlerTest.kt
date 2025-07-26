package com.stochastictinkr.gradle.lwjgl

import io.mockk.*
import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.logging.*
import kotlin.test.*

internal class DefaultLwjglDependencyHandlerTest {
    val handler = DefaultLwjglDependencyHandler()
    val project = mockk<Project> {
        every { dependencyFactory } returns mockk()
        every { logger } returns mockk<Logger>()
        every { dependencies } returns mockk<DependencyHandler>()
    }

    // Simulate the gradle DSL extension
    private fun lwjgl(block: LwjglExtension.() -> Unit) {
        LwjglExtension(
            handler = handler,
            modules = Modules,
            platforms = Platforms,
        ).block()
    }

    @Test
    fun `addDependencies filters dependencies in presets`() {
        lwjgl {
            version = "3.1.0" // Missing Assimp
            presets.gettingStarted()
            nativePlatforms(windows)
        }
        val createdDependencies = captureCreatedDependencies()
        captureConfigurations()

        project.addLwjglDependencies(handler)

        // Getting started has:
        // core, assimp, bgfx, glfw, nanovg, nuklear, openal, opengl, par, stb, vulkan
        // Verify that the dependencies were created correctly:
        assertFalse { createdDependencies.any { it.name == "assimp" } }
        listOf(
            "lwjgl", // Core's name is just "lwjgl"
            "lwjgl-bgfx",
            "lwjgl-glfw",
            "lwjgl-nanovg",
            "lwjgl-nuklear",
            "lwjgl-openal",
            "lwjgl-opengl",
            "lwjgl-par",
            "lwjgl-stb",
            "lwjgl-vulkan"
        ).forEach { name ->
            assertTrue(createdDependencies.any { it.name == name }, "Expected dependency '$name' to be created")
        }

    }

    @Test
    fun `warn when the version is lower than the minimum required version`() {
        lwjgl {
            version = "3.1.0"
            modules(assimp)
        }

        every { project.logger.warnMinVersion(Modules.assimp, "3.1.0") } just Runs

        val createdDependencies = captureCreatedDependencies()
        captureConfigurations()

        project.addLwjglDependencies(handler)
        // Verify that the dependency was created with the correct version:
        assertEquals("3.1.0", createdDependencies.first { it.name == "lwjgl-assimp" }.version)

        // Verify that the warning was logged:
        verify {
            project.logger.warnMinVersion(Modules.assimp, "3.1.0")
        }
    }

    @Test
    fun `warn when no modules are selected`() {
        lwjgl {
            version = "3.3.6"
        }

        every { project.logger.warnNoModules() } just Runs

        val createdDependencies = captureCreatedDependencies()
        captureConfigurations()

        project.addLwjglDependencies(handler)

        // Verify that no dependencies were created:
        assertTrue(createdDependencies.isEmpty())

        // Verify that the warning was logged:
        verifyAll {
            project.logger.warnNoModules()
        }
    }

    data class CreatedDependency(
        val group: String,
        val name: String,
        val version: String,
        val classifier: String?,
        val mock: ExternalModuleDependency = mockk<ExternalModuleDependency>(),
    )

    private fun captureCreatedDependencies(): List<CreatedDependency> {
        val createdDependencies = mutableListOf<CreatedDependency>()

        every { project.dependencyFactory.create(any(), any(), any(), any(), any()) } answers {
            val (group, name, version, classifier) = it.invocation.args

            CreatedDependency(
                group as String,
                name as String,
                version as String,
                classifier as String?,
            ).also(createdDependencies::add).mock
        }
        return createdDependencies
    }

    private fun captureConfigurations(
        implementationConfiguration: String = "implementation",
        runtimeConfiguration: String = "runtimeOnly",
    ): Pair<List<ExternalModuleDependency>, List<ExternalModuleDependency>> {
        val addedToImplementation = mutableListOf<ExternalModuleDependency>()
        val addedToRuntimeOnly = mutableListOf<ExternalModuleDependency>()
        every { project.dependencies.add(implementationConfiguration, capture(addedToImplementation)) } returnsArgument 1
        every { project.dependencies.add(runtimeConfiguration, capture(addedToRuntimeOnly)) } returnsArgument 1
        return addedToImplementation to addedToRuntimeOnly
    }
}