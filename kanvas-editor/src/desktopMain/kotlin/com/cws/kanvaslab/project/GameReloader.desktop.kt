package com.cws.kanvaslab.project

import com.cws.kanvas.core.Game
import java.io.File
import java.net.URLClassLoader

actual class GameReloader {

    actual fun reload(project: GameProject): Game? {
        if (build(project.root)) {
            return loadClass(project.root, "${project.packageName}.${project.name}")
        }
        return null
    }

    private fun build(projectRoot: String): Boolean {
        val status = ProcessBuilder("./gradlew", ":game:jar")
            .directory(File(projectRoot))
            .inheritIO()
            .start()
            .waitFor()
        return status == 0
    }

    private fun loadClass(projectRoot: String, className: String): Game? {
        val gameJar = File("$projectRoot/game/build/libs/game.jar")
        if (gameJar.exists()) {
            val loader = URLClassLoader(
                arrayOf(gameJar.toURI().toURL()),
                this::class.java.classLoader
            )
            val clazz = loader.loadClass(className)
            val instance = clazz.getDeclaredConstructor().newInstance() as Game
            return instance
        }
        return null
    }

}