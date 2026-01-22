package com.cws.kanvas.editor.project

import com.cws.print.Print
import java.io.File

class ProjectBuilder {

    companion object {
        private const val TAG = "ProjectBuilder"
    }

    fun build(projectConfig: ProjectConfig) {
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

    fun build(projectRoot: String, tasks: List<String>) {
        val root = File(projectRoot)
        if (!root.exists()) {
            Print.e(TAG, "Project root is absent $projectRoot")
            return
        }

        val gradlew = if (System.getProperty("os.name").lowercase().contains("win")) {
            File(root, "gradlew.bat")
        } else {
            File(root, "gradlew")
        }

        if (!gradlew.exists()) {
            Print.e(TAG, "Failed to execute gradlew, not found in $projectRoot")
            return
        }

        val command = mutableListOf(gradlew.absolutePath).apply {
            addAll(tasks)
            add("--no-daemon")
        }

        val process = ProcessBuilder(command)
            .directory(root)
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { println(it) }
        }

        val exitCode = process.waitFor()
        if (exitCode != 0) {
            Print.e(TAG, "Gradle build failed with exit code $exitCode")
            return
        }
    }

}