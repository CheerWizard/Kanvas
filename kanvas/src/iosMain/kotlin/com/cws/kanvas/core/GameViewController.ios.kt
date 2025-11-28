package com.cws.kanvas.core

import com.cws.kanvas.audio.AudioOutputStream
import com.cws.kanvas.audio.AudioInputStream
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.event.SensorManager
import com.cws.printer.Printer
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioEngine
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

        val composeViewController = ComposeUIViewController {
            gameLoop.uiContent()
        }
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
        val audioEngine = AVAudioEngine()
        return Engine(
            sensorManager = SensorManager(),
            audioPlayer = AudioOutputStream(audioEngine),
            audioRecorder = AudioInputStream(audioEngine),
        )
    }

}