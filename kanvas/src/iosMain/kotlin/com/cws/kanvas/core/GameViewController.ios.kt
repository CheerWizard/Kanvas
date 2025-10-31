package com.cws.kanvas.core

import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.sensor.InputSensorManager
import com.cws.printer.Printer
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.UIViewController
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController

abstract class GameViewController : UIViewController() {

    protected lateinit var gameLoop: GameLoop

    init {
        Printer.init()
        initGameLoop()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLoad() {
        super.viewDidLoad()

        val kanvasView = GameView(gameLoop)
        kanvasView.autoresizingMask = UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
        view.addSubview(kanvasView)

        val composeViewController = ComposeUIViewController(content = gameLoop.uiContent)
        addChildViewController(composeViewController)
        composeViewController.view.setFrame(view.bounds)
        composeViewController.view.autoresizingMask = UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
        view.addSubview(composeViewController.view)

        composeViewController.didMoveToParentViewController(this)
    }

    protected abstract fun provideGame(): Game
    protected abstract fun provideGameConfig(): GameConfig

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(
                config = provideGameConfig(),
                engine = initEngine(),
                game = provideGame(),
            )
        }
    }

    private fun initEngine(): Engine {
        return Engine(
            inputSensorManager = InputSensorManager(),
        )
    }

}