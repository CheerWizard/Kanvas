package com.cws.kanvas.editor.project

import com.cws.print.Print
import java.io.File

class ProjectLauncher {

    companion object {
        private const val TAG = "ProjectLauncher"
    }

    fun launch(projectConfig: ProjectConfig) {
        val root = File(projectConfig.root)
        if (!root.exists()) {
            Print.e(TAG, "Failed to find project root ${projectConfig.root}")
            return
        }

        when (projectConfig.target) {
            ProjectTarget.DESKTOP -> {
                val path = "build/libs/${projectConfig.name}.jar"
                val jar = root.resolve(path)
                if (jar.exists()) {
                    Print.d(TAG, "Executing command -> java -jar ${jar.absolutePath}")
                    ProcessBuilder("java", "-jar", jar.absolutePath)
                        .inheritIO()
                        .start()
                } else {
                    Print.e(TAG, "Failed to find .jar $path")
                }
            }

            ProjectTarget.ANDROID -> {
                val projectBuilder = ProjectBuilder()
                val tasks = when (projectConfig.buildType) {
                    ProjectBuildType.DEBUG -> listOf("installDebug")
                    ProjectBuildType.RELEASE -> listOf("installRelease")
                }
                projectBuilder.build(projectConfig.root, tasks)

                Print.d(TAG, "Executing command -> adb shell am start -n ${projectConfig.packageName}/.${projectConfig.mainActivity}")
                ProcessBuilder("adb", "shell", "am", "start", "-n", "${projectConfig.packageName}/.${projectConfig.mainActivity}")
                    .inheritIO()
                    .start()
            }

            ProjectTarget.IOS -> {
                val appPath = root.resolve("build/bin/ios/debugFramework/${projectConfig.name}.app")
                if (appPath.exists()) {
                    Print.d(TAG, "Executing command -> xcrun simctl launch booted ${appPath.absolutePath}")
                    ProcessBuilder("xcrun", "simctl", "launch", "booted", appPath.absolutePath)
                        .inheritIO()
                        .start()
                } else {
                    Print.e(TAG, "Failed to find app path ${appPath.absolutePath}")
                }
            }

            ProjectTarget.WEB -> {
                val outputDir = root.resolve("build/distributions")
                if (outputDir.exists()) {
                    Print.d(TAG, "Executing command -> python3 -m http.server --directory ${outputDir.absolutePath} 8080")
                    ProcessBuilder("python3", "-m", "http.server", "--directory", outputDir.absolutePath, "8080")
                        .inheritIO()
                        .start()
                } else {
                    Print.e(TAG, "Failed to find output path ${outputDir.absolutePath}")
                }
            }
        }
    }

}