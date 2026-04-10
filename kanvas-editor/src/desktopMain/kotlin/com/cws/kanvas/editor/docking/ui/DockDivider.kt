package com.cws.kanvas.editor.docking.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.onDrag
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.cws.kanvas.editor.ui.styling.HorizontalDivider
import com.cws.kanvas.editor.ui.styling.VerticalDivider
import org.jetbrains.skiko.Cursor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RowScope.DockDivider(
    modifier: Modifier = Modifier,
    onFractionChange: (Float) -> Unit
) {
    val screenWidth = LocalWindowInfo.current.containerSize.width.toFloat()

    VerticalDivider(
        modifier = modifier
            .onDrag(
                onDrag = {
                    onFractionChange(it.x / screenWidth)
                }
            )
            .pointerHoverIcon(
                icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR))
            ),
        thickness = 1.dp,
    )
//
//    Box(
//        modifier = modifier
//            .fillMaxHeight()
//            .width(2.dp)
//            .background(LocalTheme.current.border)
//    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.DockDivider(
    modifier: Modifier = Modifier,
    onFractionChange: (Float) -> Unit
) {
    val screenHeight = LocalWindowInfo.current.containerSize.height.toFloat()

    HorizontalDivider(
        modifier = modifier
            .onDrag(
                onDrag = {
                    onFractionChange(it.y / screenHeight)
                }
            )
            .pointerHoverIcon(
                icon = PointerIcon(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR))
            ),
        thickness = 1.dp,
    )

//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(2.dp)
//            .background(LocalTheme.current.border)
//
//    )
}
