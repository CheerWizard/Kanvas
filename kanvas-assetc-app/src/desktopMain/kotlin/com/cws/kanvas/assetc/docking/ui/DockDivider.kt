package com.cws.kanvas.assetc.docking.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.core.LocalTheme
import org.jetbrains.skiko.Cursor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.DockDivider(
    modifier: Modifier = Modifier,
    onFractionChange: (Float) -> Unit
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.toFloat()

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(2.dp)
            .background(LocalTheme.current.border)
            .onDrag(
                onDrag = {
                    onFractionChange(it.x / screenWidth)
                }
            )
            .pointerHoverIcon(
                icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))
            )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.DockDivider(
    modifier: Modifier = Modifier,
    onFractionChange: (Float) -> Unit
) {
    val screenHeight = LocalWindowInfo.current.containerSize.height.toFloat()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(LocalTheme.current.border)
            .onDrag(
                onDrag = {
                    onFractionChange(it.y / screenHeight)
                }
            )
            .pointerHoverIcon(
                icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR))
            )
    )
}
