package com.stochastictinkr.gradle.lwjgl

import io.mockk.*
import org.gradle.api.artifacts.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.logging.*
import kotlin.test.*

class LwjglExtensionTest {
    val dependencyFactory = mockk<DependencyFactory>()
    val dependencies = mockk<DependencyHandler>()
    val logger = mockk<Logger>()
    val extension = LwjglExtension(dependencyFactory, dependencies, logger)

    data class CreatedDependency(
        val group: String,
        val name: String,
        val version: String,
        val classifier: String?,
        val mock: ExternalModuleDependency = mockk<ExternalModuleDependency>(),
    )

    @Test
    fun `addDependencies filters dependencies in presets`() {
        extension.run {
            version = "3.1.0" // Missing Assimp
            presets.gettingStarted()
            nativePlatforms(windows)
        }
        every { logger.warn(any()) } returns Unit

        val createdDependencies = captureCreatedDependancies()
        captureConfigurations()

        extension.addDependencies()

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

    private fun captureConfigurations(): Pair<MutableList<ExternalModuleDependency>, MutableList<ExternalModuleDependency>> {
        val addedToImplementation = mutableListOf<ExternalModuleDependency>()
        val addedToRuntimeOnly = mutableListOf<ExternalModuleDependency>()
        every { dependencies.add("implementation", capture(addedToImplementation)) } returnsArgument 1
        every { dependencies.add("runtimeOnly", capture(addedToRuntimeOnly)) } returnsArgument 1
        return addedToImplementation to addedToRuntimeOnly
    }

    private fun captureCreatedDependancies(): MutableList<CreatedDependency> {
        val createdDependencies = mutableListOf<CreatedDependency>()

        every { dependencyFactory.create(any(), any(), any(), any(), any()) } answers {
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

    fun `warn when the version is lower than the minimum required version`() {
        extension.run {
            version = "3.1.0"
            modules(assimp)
        }

        every { logger.warn(any()) } returns Unit

        val createdDependencies = captureCreatedDependancies()
        captureConfigurations()

        extension.addDependencies()
        // Verify that the dependency was created with the correct version:
        assertEquals("3.1.0", createdDependencies.first { it.name == "lwjgl-assimp" }.version)

        // Verify that the warning was logged:
        verify {
            logger.warn(
                "Module 'assimp' minimum version is '3.1.1', but request version is '3.1.0'."
            )
        }
    }
}