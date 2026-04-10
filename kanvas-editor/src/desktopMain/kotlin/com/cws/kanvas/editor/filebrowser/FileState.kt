package com.cws.kanvas.editor.filebrowser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FileState(
    name: String,
    type: FileType,
) {
    var name by mutableStateOf(name)
    var type by mutableStateOf(type)
}
