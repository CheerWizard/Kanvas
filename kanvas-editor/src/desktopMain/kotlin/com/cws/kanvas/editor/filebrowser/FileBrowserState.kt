package com.cws.kanvas.editor.filebrowser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FileBrowserState(
    currentDir: String = "",
) {
    var currentPath by mutableStateOf(currentDir)
    var files by mutableStateOf(emptyList<FileState>())

    fun toModel() = FileBrowserModel(
        currentDir = currentPath,
    )
}
