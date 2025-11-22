package com.cws.kanvaslab

import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.core.Game
import com.cws.kanvas.core.GameApplication

class LabApplication : GameApplication() {

    override fun provideGame(): Game {
        TODO("Not yet implemented")
    }

    override fun provideGameConfig(): GameConfig {
        TODO("Not yet implemented")
    }

}

fun main() {
    val app = LabApplication()
}