package com.cws.kanvas.editor.filebrowser

import kotlinx.serialization.Serializable

@Serializable
data class FileBrowserModel(
    val currentDir: String,
) {

    fun toState() = FileBrowserState(
        currentDir = currentDir,
    )

}
