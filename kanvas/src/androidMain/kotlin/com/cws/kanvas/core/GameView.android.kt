package com.cws.kanvas.core

import android.content.Context
import android.graphics.SurfaceTexture
import android.view.MotionEvent
import android.view.Surface
import android.view.TextureView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.cws.printer.Printer

@Composable
fun GameView(
    modifier: Modifier = Modifier,
    gameLoop: GameLoop
) {
    Box(
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                GameView(
                    context = context,
                    gameLoop = gameLoop,
                )
            }
        )
        gameLoop.uiContent.invoke(this)
    }
}

class GameView(
    context: Context,
    private var gameLoop: GameLoop? = null
) : TextureView(context), TextureView.SurfaceTextureListener {

    companion object {
        private const val TAG = "GameView"
    }

    init {
        focusable = FOCUSABLE_AUTO
        surfaceTextureListener = this
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        Printer.d(TAG, "onSurfaceTextureSizeChanged")
        gameLoop?.onViewportChanged(width = width, height = height)
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        Printer.d(TAG, "onSurfaceTextureAvailable")
        gameLoop?.let {
            it.setSurface(Surface(surface))
            it.startLoop()
        }
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        Printer.d(TAG, "onSurfaceTextureDestroyed")
        gameLoop?.stopLoop()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        Printer.d(TAG, "onSurfaceTextureUpdated")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Printer.d(TAG, "onTouchEvent $event")
        gameLoop?.onMotionEvent(event)
        return super.onTouchEvent(event)
    }

}