package com.stochastictinkr.gradle.lwjgl

import io.mockk.*
import org.gradle.api.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.kotlin.dsl.*
import kotlin.test.*

class LwjglPluginTest {
    @Test
    fun `apply creates the extension and registers afterEvalute`() {
        val dependencyFactory = mockk<DependencyFactory>()
        val project = mockk<Project>()
        val extension = mockk<LwjglExtension>()
        every {
            project.dependencies.extensions.create<LwjglExtension>(
                "lwjgl",
                dependencyFactory,
                any(),
                project.logger
            )
        } returns extension

        every { extension.addDependencies() } just Runs

        // Capture the call to afterEvaluate:
        val afterEvaluateSlot = slot<Action<in Project>>()
        every { project.afterEvaluate(capture(afterEvaluateSlot)) } just Runs

        val plugin = LwjglPlugin(dependencyFactory)
        plugin.apply(project)

        verify {
            project.dependencies.extensions.create<LwjglExtension>(
                "lwjgl",
                dependencyFactory,
                project.dependencies,
                project.logger
            )
        }

        verify { project.afterEvaluate(any<Action<in Project>>()) }

        afterEvaluateSlot.captured.invoke(project)

        verify { extension.addDependencies() }

        confirmVerified(dependencyFactory, project, extension)
    }

}