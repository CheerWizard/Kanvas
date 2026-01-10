package com.cws.kanvas.core

import androidx.compose.runtime.Composable
import com.cws.print.Print

class GameModuleManager(
    private val window: Window,
    private val engine: Engine,
) {

    companion object {
        private const val TAG = "GameModuleManager"
    }

    private val gameModules = mutableSetOf<GameModule>()

    fun addModule(gameModule: GameModule) {
        gameModule.engine = engine
        window.addEventListener(gameModule)
        createModule(gameModule)
    }

    private fun createModule(gameModule: GameModule) {
        try {
            gameModule.onCreate()
            gameModules.add(gameModule)
        } catch (e: Throwable) {
            Print.e(TAG, "Error in onCreate()", e)
        }
    }

    fun removeModule(gameModule: GameModule) {
        destroyModule(gameModule)
        gameModules.remove(gameModule)
    }

    private fun destroyModule(gameModule: GameModule) {
        try {
            gameModule.onDestroy()
        } catch (e: Throwable) {
            Print.e(TAG, "Error in onDestroy()", e)
        }
        window.removeEventListener(gameModule)
    }

    fun clearModules() {
        gameModules.forEach { gameModule ->
            destroyModule(gameModule)
        }
        gameModules.clear()
    }

    fun onUpdate(dtMillis: Float) {
        gameModules.forEach { gameModule ->
            try {
                gameModule.onUpdate(dtMillis)
            } catch (e: Throwable) {
                Print.e(TAG, "Error in onUpdate()", e)
            }
        }
    }

    fun onRender(dt: Float) {
        gameModules.forEach { gameModule ->
            try {
                gameModule.onRender(dt)
            } catch (e: Throwable) {
                Print.e(TAG, "Error in onRender()", e)
            }
        }
    }

    @Composable
    fun onRenderUI() {
        gameModules.forEach { gameModule ->
            gameModule.onRenderUI()
        }
    }

}