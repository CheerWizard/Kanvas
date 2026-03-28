package com.cws.kanvas.assetc.filebrowser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.dialog.DialogState
import com.cws.kanvas.assetc.dialog.LocalDialogController
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.docking.ui.DockWindow
import com.cws.kanvas.assetc.docking.ui.WindowTitleBarContent
import com.cws.kanvas.assetc.overlay.Overlay
import com.cws.kanvas.assetc.overlay.clickOverlay
import com.cws.kanvas.assetc.ui.components.DropMenu
import com.cws.kanvas.assetc.ui.LocalAppTextStyles
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.components.AppIcon
import com.cws.kanvas.assetc.ui.components.ColorPicker

object FileBrowserWindow {

    val ID = FileBrowserWindow::class.simpleName.orEmpty()

    private val state = DockWindowState(
        id = ID,
        title = "File Browser",
        canUndock = false,
        canClose = false,
    )

    @Composable
    fun render() {
        val fileBrowserController = provideFileBrowserController()

        DockWindow(
            state = state,
            enableDragAndDrop = true,
            onDragAndDrop = {

            },
            titleBar = { state ->
                WindowTitleBarContent(
                    state = state,
                    showWindowButtons = false,
                )
            },
        ) {
            Overlay(
                modifier = Modifier.fillMaxSize(),
            ) {
                FileBrowserPanel(
                    fileBrowserController = fileBrowserController,
                )
            }
        }
    }

    @Composable
    fun FileBrowserPanel(fileBrowserController: FileBrowserController) {
        val state = fileBrowserController.state
        val contextMenu = fileBrowserController.contextMenu
        val dialogController = LocalDialogController.current

        var color by remember { mutableStateOf(Color.Black) }

        LaunchedEffect(Unit) {
            dialogController.show(
                DialogState(
                    title = "Color Picker",
                    size = DpSize(300.dp, 300.dp),
                    content = {
                        ColorPicker(
                            color = color,
                            onColorChanged = { color = it }
                        )
                    },
                )
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Browser navigation
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(32.dp)
//                    .bloomBorders(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//                AppIcon(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .rotate(180f)
//                        .hoverOverlay("file_browser_arrow_back") {
//                            AppText(
//                                text = "Back button overlay",
//                                style = LocalAppTextStyles.current.labelSmall,
//                            )
//                        },
//                    imageVector = IconArrow,
//                    color = LocalTheme.current.textPrimary,
//                )
//                AppIcon(
//                    modifier = Modifier
//                        .size(32.dp),
//                    imageVector = IconArrow,
//                    color = LocalTheme.current.textPrimary,
//                )
//            }

            // Browser search

            // Browser files
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .clickOverlay("file_browser_files") {
                        DropMenu(
                            modifier = Modifier
                                .width(100.dp),
                            style = LocalTheme.current.contextMenuStyle,
                            textStyle = LocalAppTextStyles.current.contextMenu,
                            items = contextMenu,
                        )
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp),
            ) {

            }
        }
    }

}
