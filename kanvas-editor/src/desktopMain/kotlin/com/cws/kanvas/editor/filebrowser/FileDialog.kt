package com.cws.kanvas.editor.filebrowser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FilenameFilter

enum class FileDialogType {
    IMPORT,
    EXPORT
}

@Composable
fun FileDialog(
    title: String,
    fileDialogType: FileDialogType? = null,
    extensions: List<String> = emptyList(),
    onExportFile: (File) -> Unit,
    onImportFile: (File) -> Unit,
) {
    LaunchedEffect(fileDialogType) {
        if (fileDialogType != null) {
            val type = when (fileDialogType) {
                FileDialogType.IMPORT -> FileDialog.LOAD
                FileDialogType.EXPORT -> FileDialog.SAVE
            }

            val fileDialog = FileDialog(null as Frame?, title, type)
            fileDialog.isVisible = true

            if (extensions.isNotEmpty()) {
                fileDialog.filenameFilter = FilenameFilter { dir, name ->
                    extensions.any { extension ->
                        name.endsWith(extension)
                    }
                }
            }

            val dir = fileDialog.directory
            val file = fileDialog.file
            if (dir != null && file != null) {
                when (fileDialogType) {
                    FileDialogType.EXPORT -> onExportFile(File(dir, file))
                    FileDialogType.IMPORT -> onImportFile(File(dir, file))
                }
            }
        }
    }
}
