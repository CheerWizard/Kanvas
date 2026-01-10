package com.cws.kanvaslab.project

import java.io.File

actual class ProjectLauncher actual constructor() {

    actual fun launch(projectConfig: ProjectConfig) {
        val root = File(projectConfig.root)
        if (!root.exists()) {
            return
        }

        when (projectConfig.target) {
            ProjectTarget.DESKTOP -> {
                val jar = root.resolve("build/libs/${projectConfig.name}.jar")
                if (jar.exists()) {
                    ProcessBuilder("java", "-jar", jar.absolutePath)
                        .inheritIO()
                        .start()
                }
            }

            ProjectTarget.ANDROID -> {
                val projectBuilder = ProjectBuilder()
                val tasks = when (projectConfig.buildType) {
                    ProjectBuildType.DEBUG -> listOf("installDebug")
                    ProjectBuildType.RELEASE -> listOf("installRelease")
                }
                projectBuilder.build(projectConfig.root, tasks)

                ProcessBuilder("adb", "shell", "am", "start", "-n", "${projectConfig.packageName}/.${projectConfig.mainActivity}")
                    .inheritIO()
                    .start()
            }

            ProjectTarget.IOS -> {
                val appPath = root.resolve("build/bin/ios/debugFramework/${projectConfig.name}.app")
                ProcessBuilder("xcrun", "simctl", "launch", "booted", appPath.absolutePath)
                    .inheritIO()
                    .start()
            }

            ProjectTarget.WEB -> {
                val outputDir = root.resolve("build/distributions")
                ProcessBuilder("python3", "-m", "http.server", "--directory", outputDir.absolutePath, "8080")
                    .inheritIO()
                    .start()
            }
        }
    }

}