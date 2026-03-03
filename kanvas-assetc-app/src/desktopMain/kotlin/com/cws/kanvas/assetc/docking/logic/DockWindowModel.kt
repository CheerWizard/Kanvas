package com.cws.kanvas.assetc.docking.logic

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class DockWindowModel(
    val id: String,
    val title: String,
    @Contextual val x: Dp,
    @Contextual val y: Dp,
    @Contextual val width: Dp,
    @Contextual val height: Dp,
    val docked: Boolean,
    val minimized: Boolean,
    val maximized: Boolean,
    val visible: Boolean,
    val canClose: Boolean,
    val dockSpace: DockSpaceModel?,
) {

    fun toState() = DockWindowState(
        id = id,
        title = title,
        docked = docked,
        minimized = minimized,
        maximized = maximized,
        visible = visible,
        canClose = canClose,
        dockSpace = dockSpace?.toState(),
    ).apply {
        position = DpOffset(x, y)
        size = DpSize(width, height)
    }

}
