package com.cws.kanvas.editor.docking.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList

class DockContainerState(
    currentWindow: String? = null,
    windows: List<String> = emptyList(),
) {

    var currentWindow by mutableStateOf(currentWindow)
        internal set

    val windows = windows.toMutableStateList()

    fun toModel() = DockContainerModel(
        currentWindow = currentWindow,
        windows = windows,
    )

    fun add(windowId: String) {
        windows.add(windowId)
        currentWindow = windowId
    }

    fun remove(windowId: String) {
        windows.remove(windowId)
    }

    fun removeAt(index: Int) {
        windows.removeAt(index)
    }

}
