package com.cws.flip.game

import com.cws.flip.FlipGame
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.config.WindowConfig
import com.cws.kanvas.core.Game
import com.cws.kanvas.core.GameApplication

class MainApplication : GameApplication() {

    override fun provideGame(): Game {
        return FlipGame()
    }

    override fun provideGameConfig(): GameConfig {
        return GameConfig(
            window = WindowConfig(
                title = "Flip",
                x = 0,
                y = 0,
                width = 800,
                height = 600,
            )
        )
    }

}