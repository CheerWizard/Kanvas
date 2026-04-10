package com.cws.kanvas.editor.project

import com.cws.kanvas.core.GameModule
import com.cws.kanvas.core.GameModuleManager
import com.cws.print.Print
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class ProjectManager(
    private val gameModuleManager: GameModuleManager,
) {

    companion object {
        private const val TAG = "ProjectManager"
    }

    private val gameModuleLoader = GameModuleLoader()
    private val gameModules = mutableMapOf<String, GameModule>()
    private var projects = mutableMapOf<String, ProjectModel>()
    private var currentProject: ProjectModel? = null

    fun loadProjects() {
        try {
            val json = File("workspace.json").readText()
            projects = Json.decodeFromString(json)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load projects!", e)
        }
    }

    fun saveProjects() {
        try {
            val json = Json.encodeToString(projects)
            File("workspace.json").writeText(json)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to save projects!", e)
        }
    }

    fun openProject(projectName: String) {
        currentProject = projects[projectName]
    }

    fun closeProject() {
        currentProject = null
    }

    fun clearProject() {
        currentProject?.modules?.forEach { module ->
            removeModule(module)
        }
    }

    fun addModule(moduleName: String) {
        // TODO this will
        //  1. Generate new Gradle module
        //  2. #TModule : GameModule new class from template file
        //  3. Store new module name into config.modules
        //  4. Reload that module so it gets consumed into runtime
        val project = currentProject
        if (project == null) {
            Print.e(TAG, "No opened project!")
            return
        }

        val gameModule = gameModuleLoader.load(project, moduleName)
        if (gameModule != null) {
            project.modules.add(moduleName)
            gameModules[moduleName] = gameModule
            gameModuleManager.addModule(gameModule)
        } else {
            Print.e(project.name, "Failed to load game module $moduleName!")
        }
    }

    fun removeModule(moduleName: String) {
        // TODO this will
        //  1. Erase entire module directory
        //  2. Erase module name from config.modules
        //  3. Reload that module so it will be also removed from runtime
    }

    fun launchProject(projectModel: ProjectModel) {
        val root = File(projectModel.root)
        if (!root.exists()) {
            Print.e(TAG, "Failed to find project root ${projectModel.root}")
            return
        }

        when (projectModel.target) {
            ProjectTarget.DESKTOP -> {
                val path = "build/libs/${projectModel.name}.jar"
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
                val tasks = when (projectModel.buildType) {
                    ProjectBuildType.DEBUG -> listOf("installDebug")
                    ProjectBuildType.RELEASE -> listOf("installRelease")
                }
                buildProject(projectModel.root, tasks)
                Print.d(TAG, "Executing command -> adb shell am start -n ${projectModel.packageName}/.${projectModel.mainActivity}")
                ProcessBuilder("adb", "shell", "am", "start", "-n", "${projectModel.packageName}/.${projectModel.mainActivity}")
                    .inheritIO()
                    .start()
            }

            ProjectTarget.IOS -> {
                val appPath = root.resolve("build/bin/ios/debugFramework/${projectModel.name}.app")
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

    fun buildProject(projectModel: ProjectModel) {
        buildProject(
            projectModel.root,
            getGradleTasks(projectModel.buildType, projectModel.target)
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

    fun buildProject(projectRoot: String, tasks: List<String>) {
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