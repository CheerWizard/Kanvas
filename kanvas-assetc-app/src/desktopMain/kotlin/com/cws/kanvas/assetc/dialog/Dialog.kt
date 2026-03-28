package com.cws.kanvas.assetc.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.rememberWindowState
import com.cws.kanvas.assetc.ui.LocalAppTextStyles
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.components.AppText
import com.cws.kanvas.assetc.ui.icons.IconX
import com.cws.kanvas.assetc.ui.styling.White
import com.cws.kanvas.assetc.ui.styling.surface

@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    state: DialogState,
) {
    val windowState = rememberWindowState(
        size = state.size,
    )

    Window(
        state = windowState,
        title = state.title,
        onCloseRequest = state.onClose,
        undecorated = true,
        resizable = false,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .surface(LocalTheme.current.elevationLow)
                .bloomBorders()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WindowDraggableArea(
                modifier = Modifier.fillMaxWidth(),
            ) {
                state.titleBar(this@Window)
            }
            state.content()
        }
    }
}

@Composable
fun WindowScope.DialogWindowTitleBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    val density = LocalDensity.current
    val dialogController = LocalDialogController.current
    val textStyle = LocalAppTextStyles.current.bodyMedium

    val iconSize = with(density) { (textStyle.style.fontSize * 1.4f).toDp() }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppText(
            text = title,
            style = textStyle,
        )
        Spacer(Modifier.weight(1f))
        Image(
            modifier = Modifier
                .size(iconSize)
                .clickable(
                    onClick = {
                        window.isVisible = false
                        dialogController.hide(title)
                    },
                ),
            imageVector = IconX,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            colorFilter = ColorFilter.tint(White)
        )
    }
}
