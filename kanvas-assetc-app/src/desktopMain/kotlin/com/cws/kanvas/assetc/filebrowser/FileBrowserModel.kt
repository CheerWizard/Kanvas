package com.cws.kanvas.assetc.filebrowser

import kotlinx.serialization.Serializable

@Serializable
data class FileBrowserModel(
    val currentDir: String,
) {

    fun toState() = FileBrowserState(
        currentDir = currentDir,
    )

}
