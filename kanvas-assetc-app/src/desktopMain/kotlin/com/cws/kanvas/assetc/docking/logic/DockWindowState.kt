package com.cws.kanvas.assetc.docking.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

class DockWindowState(
    val id: String,
    title: String,
    dockSpace: DockSpaceState? = null,
    docked: Boolean = false,
    minimized: Boolean = false,
    maximized: Boolean = false,
    visible: Boolean = true,
    canClose: Boolean = true,
    content: @Composable () -> Unit = {},
    onClose: () -> Unit = {},
) {
    var title by mutableStateOf(title)
    var position by mutableStateOf(DpOffset(400.dp, 300.dp))
    var size by mutableStateOf(DpSize(800.dp, 600.dp))
    var minimized by mutableStateOf(minimized)
    var maximized by mutableStateOf(maximized)
    var docked by mutableStateOf(docked)
    var dockSpace by mutableStateOf(dockSpace)
    var visible by mutableStateOf(visible)
    var canClose by mutableStateOf(canClose)
    var content by mutableStateOf<@Composable () -> Unit>(content)
    var onClose by mutableStateOf(onClose)

    fun toModel() = DockWindowModel(
        id = id,
        title = title,
        x = position.x,
        y = position.y,
        width = size.width,
        height = size.height,
        docked = docked,
        minimized = minimized,
        maximized = maximized,
        visible = visible,
        canClose = canClose,
        dockSpace = dockSpace?.toModel(),
    )

}
