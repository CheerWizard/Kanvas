package com.cws.kanvas.assetc.ui

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cws.print.Print
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetAdapter
import java.awt.dnd.DropTargetDragEvent
import java.awt.dnd.DropTargetDropEvent
import java.awt.dnd.DropTargetEvent
import java.io.File
import javax.swing.JPanel

@Composable
fun DragAndDropPanel(
    modifier: Modifier = Modifier,
    onDragAndDrop: (List<File>) -> Unit,
) {
    var hoverDragAndDrop by remember { mutableStateOf(false) }

    SwingPanel(
        modifier = modifier
            .applyIf(hoverDragAndDrop) {
                border(width = 2.dp, color = Color(red = 0, green = 255, blue = 0, alpha = 255))
            },
        factory = {
            val panel = JPanel()

            panel.dropTarget = DropTarget(panel, object : DropTargetAdapter() {

                override fun dragEnter(event: DropTargetDragEvent?) {
                    hoverDragAndDrop = true
                }

                override fun dragExit(event: DropTargetEvent?) {
                    hoverDragAndDrop = false
                }

                override fun dragOver(event: DropTargetDragEvent?) {
                    super.dragOver(event)
                }

                override fun drop(event: DropTargetDropEvent?) {
                    event?.let { event ->
                        try {
                            event.acceptDrop(DnDConstants.ACTION_COPY)
                            val transferable = event.transferable
                            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                val files = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>
                                files.forEach {
                                    Print.i("DragAndDropPanel", "drag and drop ${it.absolutePath}")
                                }
                                onDragAndDrop(files)
                                event.dropComplete(true)
                            } else {
                                event.dropComplete(false)
                            }
                        } catch (e: Exception) {
                            Print.e("DragAndDropPanel", "Failed to drag and drop item", e)
                            event.dropComplete(false)
                        }
                    }
                }

            })

            panel
        }
    )
}