package com.cws.kanvaslab.project

import com.cws.kanvas.core.GameModule
import com.cws.kanvas.core.GameModuleManager
import com.cws.print.Print
import kotlinx.serialization.Serializable

@Serializable
data class ProjectConfig(
    val name: String,
    val root: String,
    val packageName: String,
    val mainActivity: String,
    var target: ProjectTarget,
    var buildType: ProjectBuildType,
    var launchMode: ProjectLaunchMode,
    var connectedDeviceId: String? = null,
    val modules: MutableSet<String> = mutableSetOf(),
)

enum class ProjectTarget {
    DESKTOP,
    WEB,
    ANDROID,
    IOS,
}

enum class ProjectBuildType {
    DEBUG,
    RELEASE,
}

enum class ProjectLaunchMode {
    DEBUG,
    DEFAULT
}

class Project(
    val config: ProjectConfig,
    private val gameModuleManager: GameModuleManager,
) {

    private val projectBuilder = ProjectBuilder()
    private val projectLauncher = ProjectLauncher()
    private val gameModuleLoader = GameModuleLoader()
    private val gameModules = mutableMapOf<String, GameModule>()

    fun clear() {
        config.modules.forEach { module ->
            removeModule(module)
        }
    }

    fun addModule(moduleName: String) {
        // TODO this will
        //  1. Generate new Gradle module
        //  2. #TModule : GameModule new class from template file
        //  3. Store new module name into config.modules
        //  4. Reload that module so it gets consumed into runtime
        val gameModule = gameModuleLoader.load(config, moduleName)
        if (gameModule != null) {
            config.modules.add(moduleName)
            gameModules[moduleName] = gameModule
            gameModuleManager.addModule(gameModule)
        } else {
            Print.e(config.name, "Failed to load game module $moduleName!")
        }
    }

    fun removeModule(moduleName: String) {
        // TODO this will
        //  1. Erase entire module directory
        //  2. Erase module name from config.modules
        //  3. Reload that module so it will be also removed from runtime
        gameModuleManager.removeModule()
    }

    fun build() {
        projectBuilder.build(config)
    }

    fun launch() {
        projectLauncher.launch(config)
    }

}