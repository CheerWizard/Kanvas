package com.cws.kanvas.editor.project

import com.cws.kanvas.core.GameModule
import com.cws.print.Print
import java.io.File
import java.net.URLClassLoader

class GameModuleLoader {

    companion object {
        private const val TAG = "GameModuleLoader"
    }

    fun load(projectConfig: ProjectConfig, moduleName: String): GameModule? {
        if (build(projectConfig.root, moduleName)) {
            return loadClass(projectConfig.root, "${projectConfig.packageName}.${projectConfig.name}")
        }
        return null
    }

    private fun build(projectRoot: String, moduleName: String): Boolean {
        Print.d(TAG, "building $projectRoot $moduleName")
        val status = ProcessBuilder("./gradlew", ":$moduleName:jar")
            .directory(File(projectRoot))
            .inheritIO()
            .start()
            .waitFor()
        return status == 0
    }

    private fun loadClass(projectRoot: String, moduleName: String): GameModule? {
        val jarPath = "$projectRoot/$moduleName/build/libs/$moduleName.jar"
        Print.d(TAG, "loading class from $jarPath")
        val jar = File(jarPath)
        if (jar.exists()) {
            val loader = URLClassLoader(
                arrayOf(jar.toURI().toURL()),
                this::class.java.classLoader
            )
            val module = loader.loadClass(moduleName)
            val instance = module.getDeclaredConstructor().newInstance() as GameModule
            return instance
        } else {
            Print.e(TAG, "Failed to load class from $jarPath")
        }
        return null
    }

}