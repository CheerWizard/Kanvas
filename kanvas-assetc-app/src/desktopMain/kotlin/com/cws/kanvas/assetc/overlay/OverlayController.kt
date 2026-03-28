package com.cws.kanvas.assetc.overlay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.unit.DpOffset
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController

val LocalOverlayController = staticCompositionLocalOf<OverlayController> {
    error("LocalOverlayController is not initialized")
}

abstract class OverlayController : Controller() {

    var cursor by mutableStateOf(DpOffset.Zero)
    var hidePointerButton by mutableStateOf(PointerButton.Primary)

    val hoverOverlays = mutableStateMapOf<String, HoverOverlayState>()
    val clickOverlays = mutableStateMapOf<String, ClickOverlayState>()

    fun addHoverOverlay(hoverOverlayState: HoverOverlayState) {
        hoverOverlays.putIfAbsent(hoverOverlayState.id, hoverOverlayState)
    }

    fun addClickOverlay(clickOverlayState: ClickOverlayState) {
        addClickOverlay(cursor, clickOverlayState)
    }

    fun addClickOverlay(position: DpOffset, clickOverlayState: ClickOverlayState) {
        clickOverlayState.position = position
        clickOverlays[clickOverlayState.id] = clickOverlayState
    }

    fun removeHoverOverlay(id: String) {
        if (hoverOverlays.contains(id)) {
            hoverOverlays.remove(id)
        }
    }

    fun removeClickOverlay(id: String) {
        clickOverlays.remove(id)
    }

    fun clearClickOverlays() {
        clickOverlays.clear()
    }

}

class OverlayControllerImpl : OverlayController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

class OverlayControllerPreview : OverlayController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

@Composable
fun provideOverlayController(): OverlayController = provideController(
    preview = { OverlayControllerPreview() },
    impl = { OverlayControllerImpl() },
)
