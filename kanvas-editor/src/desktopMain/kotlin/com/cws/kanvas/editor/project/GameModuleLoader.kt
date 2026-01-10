package com.cws.kanvaslab.project

import com.cws.kanvas.core.GameModule
import java.io.File
import java.net.URLClassLoader

class GameModuleLoader {

    fun load(projectConfig: ProjectConfig, moduleName: String): GameModule? {
        if (build(projectConfig.root, moduleName)) {
            return loadClass(projectConfig.root, "${projectConfig.packageName}.${projectConfig.name}")
        }
        return null
    }

    private fun build(projectRoot: String, moduleName: String): Boolean {
        val status = ProcessBuilder("./gradlew", ":$moduleName:jar")
            .directory(File(projectRoot))
            .inheritIO()
            .start()
            .waitFor()
        return status == 0
    }

    private fun loadClass(projectRoot: String, moduleName: String): GameModule? {
        val jar = File("$projectRoot/$moduleName/build/libs/$moduleName.jar")
        if (jar.exists()) {
            val loader = URLClassLoader(
                arrayOf(jar.toURI().toURL()),
                this::class.java.classLoader
            )
            val module = loader.loadClass(moduleName)
            val instance = module.getDeclaredConstructor().newInstance() as GameModule
            return instance
        }
        return null
    }

}