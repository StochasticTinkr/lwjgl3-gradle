package com.stochastictinkr

import org.gradle.api.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.kotlin.dsl.*
import javax.inject.*

class LwjglPlugin
@Inject constructor(val dependencyFactory: DependencyFactory) : Plugin<Project> {
    override fun apply(project: Project) {
        val ext =
            project
                .dependencies
                .extensions
                .create<LwjglExtension>(
                    "lwjgl",
                    dependencyFactory,
                    project.dependencies,
                    project.logger
                )
        project.afterEvaluate {
            ext.addDependencies()
        }
    }
}


