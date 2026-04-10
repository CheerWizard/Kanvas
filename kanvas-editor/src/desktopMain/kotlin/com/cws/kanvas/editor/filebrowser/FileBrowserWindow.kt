package com.cws.kanvas.editor.filebrowser

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
import com.cws.kanvas.editor.dialog.DialogState
import com.cws.kanvas.editor.dialog.LocalDialogController
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.docking.ui.DockWindow
import com.cws.kanvas.editor.docking.ui.WindowTitleBarContent
import com.cws.kanvas.editor.overlay.Overlay
import com.cws.kanvas.editor.overlay.clickOverlay
import com.cws.kanvas.editor.ui.components.DropMenu
import com.cws.kanvas.editor.ui.LocalAppTextStyles
import com.cws.kanvas.editor.ui.LocalTheme
import com.cws.kanvas.editor.ui.components.ColorPicker
import com.cws.kanvas.editor.window.AppWindow

private val FileBrowserWindow = object : AppWindow(
    DockWindowState(
        id = "FileBrowserWindow",
        title = "File Browser",
        canUndock = false,
        canClose = false,
        enableDragAndDrop = true,
        onDragAndDrop = {},
    )
) {

    @Composable
    override fun content() {
        val fileBrowserController = provideFileBrowserController()

        Overlay(
            modifier = Modifier.fillMaxSize(),
        ) {
            FileBrowserPanel(
                fileBrowserController = fileBrowserController,
            )
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
