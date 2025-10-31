package com.cws.kanvas.core

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import com.cws.kanvas.config.GameConfig
import com.cws.kanvas.event.EventListener
import com.cws.kanvas.event.KeyCode
import com.cws.kanvas.event.MouseCode
import com.cws.kanvas.gfx.bridges.RenderBridge
import com.cws.kanvas.utils.fps
import com.cws.printer.Printer

class GameLoop(
    val engine: Engine,
    val config: GameConfig,
    private var game: Game? = null,
) : PlatformGameLoop(name = TAG, priority = 1), EventListener {

    companion object {
        private const val TAG = "GameLoop"
    }

    private lateinit var window: Window

    lateinit var onWindowCreated: (Window) -> Unit

    val uiContent: @Composable BoxScope.() -> Unit = { renderUI() }

    private var surface: Any? = null

    fun setGame(game: Game?) {
        if (game == null) return
        this.game = game
        game.engine = engine
        window.addEventListener(game)
        game.onCreate()
    }

    fun removeGame() {
        this.game?.let { game ->
            game.onDestroy()
            window.removeEventListener(game)
        }
        this.game = null
    }

    override fun onCreate() {
        window = Window(config.window)
        window.setSurface(surface)
        if (::onWindowCreated.isInitialized) {
            onWindowCreated(window)
        }
        window.addEventListener(this)
        engine.init()
        setGame(game)
    }

    override fun onDestroy() {
        removeGame()
        engine.release()
        window.removeEventListener(this)
        window.release()
    }

    override fun onUpdate(dtMillis: Float) {
        Printer.d(TAG, "dt=${dtMillis}ms FPS=${dtMillis.fps}")
        window.pollEvents()
        game?.onUpdate(dtMillis)
        engine.jobsManager.execute()
        render(dtMillis)
        window.applySwapChain()
        running = !window.isClosed()
    }

    private fun render(dt: Float) {
        game?.onRender(dt)
    }

    @Composable
    private fun BoxScope.renderUI() {
        game?.onRenderUI()
    }

    fun onViewportChanged(width: Int, height: Int) {
        RenderBridge.resize(width, height)
    }

    fun onMotionEvent(event: Any?) {
        if (event != null && ::window.isInitialized) {
            window.addEvent(event)
        }
    }

    fun setSurface(surface: Any?) {
        this.surface = surface
        RenderBridge.setSurface(surface)
    }

    override fun onWindowResized(width: Int, height: Int) {
        super.onWindowResized(width, height)
        game?.onWindowResized(width, height)
    }

    override fun onWindowMoved(x: Int, y: Int) {
        super.onWindowMoved(x, y)
        game?.onWindowMoved(x, y)
    }

    override fun onKeyPressed(code: KeyCode, hold: Boolean) {
        super.onKeyPressed(code, hold)
        game?.onKeyPressed(code, hold)
    }

    override fun onKeyReleased(code: KeyCode) {
        super.onKeyReleased(code)
        game?.onKeyReleased(code)
    }

    override fun onKeyTyped(char: Char) {
        super.onKeyTyped(char)
        game?.onKeyTyped(char)
    }

    override fun onMousePressed(code: MouseCode, hold: Boolean) {
        super.onMousePressed(code, hold)
        game?.onMousePressed(code, hold)
    }

    override fun onMouseReleased(code: MouseCode) {
        super.onMouseReleased(code)
        game?.onMouseReleased(code)
    }

    override fun onMouseMove(x: Double, y: Double) {
        super.onMouseMove(x, y)
        game?.onMouseMove(x, y)
    }

    override fun onMouseScroll(x: Double, y: Double) {
        super.onMouseScroll(x, y)
        game?.onMouseScroll(x, y)
    }

    override fun onTapPressed(x: Float, y: Float) {
        super.onTapPressed(x, y)
        game?.onTapPressed(x, y)
    }

    override fun onTapReleased(x: Float, y: Float) {
        super.onTapReleased(x, y)
        game?.onTapReleased(x, y)
    }

}