package com.cws.kanvas.editor.docking.logic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import java.io.File

class DockWindowState(
    val id: String,
    title: String,
    dockSpace: DockSpaceState? = null,
    docked: Boolean = false,
    minimized: Boolean = false,
    maximized: Boolean = false,
    visible: Boolean = true,
    canClose: Boolean = true,
    canUndock: Boolean = true,
    enableDragAndDrop: Boolean = false,
    onClose: () -> Unit = {},
    onDragAndDrop: (files: List<File>) -> Unit = {},
    titleBar: @Composable (state: DockWindowState) -> Unit = {},
    content: @Composable () -> Unit = {},
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
    var canUndock by mutableStateOf(canUndock)
    var enableDragAndDrop by mutableStateOf(enableDragAndDrop)
    var onClose by mutableStateOf(onClose)
    var onDragAndDrop by mutableStateOf(onDragAndDrop)
    var titleBar by mutableStateOf<@Composable (state: DockWindowState) -> Unit>(titleBar)
    var content by mutableStateOf<@Composable () -> Unit>(content)

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
        canUndock = canUndock,
        dockSpace = dockSpace?.toModel(),
    )

}
