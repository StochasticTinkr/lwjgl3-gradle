package com.stochastictinkr.gradle.lwjgl

import io.mockk.*
import org.gradle.api.*
import org.gradle.kotlin.dsl.*
import kotlin.test.*

private typealias ProjectAction = Action<in Project>

class LwjglPluginTest {
    @Test
    fun `apply creates the extension and registers afterEvalute`() {
        val handler = mockk<DefaultLwjglDependencyHandler>()

        val project = mockk<Project> {
            every { addLwjglDependencies(handler) } just Runs
            every { createLwjglExtension(handler) } just Runs
        }

        val afterEvaluateSlot = slot<ProjectAction>()
        every { project.afterEvaluate(capture(afterEvaluateSlot)) } just Runs

        LwjglPlugin(handler).apply(project)

        afterEvaluateSlot.captured.invoke(project)

        verifyAll {
            with(project) {
                createLwjglExtension(handler)
                afterEvaluate(any<ProjectAction>())
                addLwjglDependencies(handler)
            }
        }
    }

}