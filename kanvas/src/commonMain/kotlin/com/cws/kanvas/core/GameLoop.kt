package com.cws.kanvas.core

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import com.cws.kanvas.event.EventListener
import com.cws.kanvas.rendering.backend.RenderContextInfo
import com.cws.kanvas.rendering.backend.RenderThread
import com.cws.kanvas.utils.fps
import com.cws.print.Print

class GameLoop(context: Context) : PlatformGameLoop(name = TAG, priority = 1), EventListener {

    companion object {
        private const val TAG = "GameLoop"
    }

    private val engine = Engine(context)

    private lateinit var window: Window

    private var gameModuleManager: GameModuleManager? = null

    lateinit var onWindowCreated: (Window) -> Unit

    val uiContent: @Composable BoxScope.() -> Unit = { renderUI() }

    private var surface: Any? = null

    override fun onCreate() {
        window = Window()
        if (::onWindowCreated.isInitialized) {
            onWindowCreated(window)
        }
        window.addEventListener(this)
        engine.init()
    }

    override fun onDestroy() {
        gameModuleManager?.clearModules()
        engine.release()
        window.removeEventListener(this)
    }

    override fun onUpdate(dtMillis: Float) {
        Print.d(TAG, "dt=${dtMillis}ms FPS=${dtMillis.fps}")
        window.pollEvents()
        gameModuleManager?.onUpdate(dtMillis)
        engine.jobsManager.execute()
        render(dtMillis)
        running = !window.isClosed()
    }

    private fun render(dt: Float) {
        gameModuleManager?.onRender(dt)
    }

    @Composable
    private fun BoxScope.renderUI() {
        gameModuleManager?.onRenderUI()
    }

    fun onViewportChanged(width: Int, height: Int) {
//        RenderBridge.resize(width, height)
    }

    fun onMotionEvent(event: Any?) {
        if (event != null && ::window.isInitialized) {
            window.addEvent(event)
        }
    }

    fun setSurface(surface: Any?) {
        this.surface = surface
//        RenderBridge.setSurface(surface)
    }

}