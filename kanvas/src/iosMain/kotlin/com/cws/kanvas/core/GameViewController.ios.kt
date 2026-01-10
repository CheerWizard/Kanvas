package com.cws.kanvas.core

import com.cws.print.Print
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.*

abstract class GameViewController : UIViewController() {

    protected lateinit var gameLoop: GameLoop

    init {
        Print.install(Unit) {
            initGameLoop()
        }
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

    private fun initGameLoop() {
        if (!::gameLoop.isInitialized) {
            gameLoop = GameLoop(Unit)
        }
    }

}