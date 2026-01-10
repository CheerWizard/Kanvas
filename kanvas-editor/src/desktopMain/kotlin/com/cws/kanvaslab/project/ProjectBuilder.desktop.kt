package com.cws.kanvaslab.project

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

import java.io.File

actual class ProjectBuilder actual constructor() {

    actual fun build(projectConfig: ProjectConfig) {
        build(
            projectConfig.root,
            getGradleTasks(projectConfig.buildType, projectConfig.target)
        )
    }

    private fun getGradleTasks(buildType: ProjectBuildType, target: ProjectTarget): List<String> {
        return when (target) {
            ProjectTarget.DESKTOP -> listOf(
                if (buildType == ProjectBuildType.DEBUG) "run" else "installDist"
            )
            ProjectTarget.ANDROID -> listOf(
                if (buildType == ProjectBuildType.DEBUG) "assembleDebug" else "assembleRelease"
            )
            ProjectTarget.IOS -> listOf(
                if (buildType == ProjectBuildType.DEBUG) "linkDebugFrameworkIos" else "linkReleaseFrameworkIos"
            )
            ProjectTarget.WEB -> listOf(
                if (buildType == ProjectBuildType.DEBUG) "browserDevelopmentRun" else "browserProductionWebpack"
            )
        }
    }

    actual fun build(projectRoot: String, tasks: List<String>) {
        val root = File(projectRoot)
        if (!root.exists()) {
            return
        }

        val connector = GradleConnector.newConnector().forProjectDirectory(root)
        connector.connect().use { connection ->
            val build = connection.newBuild()
            build.forTasks(*tasks.toTypedArray())
            build.run()
        }
    }

}