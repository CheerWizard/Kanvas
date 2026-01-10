package com.cws.flip.game

import com.cws.flip.FlipGameModule
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.core.WindowConfig
import com.cws.kanvas.core.GameModule
import com.cws.kanvas.core.GameApplication

class MainApplication : GameApplication() {

    override fun provideGame(): GameModule {
        return FlipGameModule()
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