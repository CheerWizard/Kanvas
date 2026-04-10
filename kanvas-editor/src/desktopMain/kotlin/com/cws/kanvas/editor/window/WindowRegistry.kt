package com.cws.kanvas.editor.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import com.cws.kanvas.editor.docking.logic.DockWindowState
import com.cws.kanvas.editor.docking.ui.DockWindow
import com.cws.kanvas.editor.docking.ui.WindowTitleBarContent

private val windowRegistry = mutableStateMapOf<String, DockWindowState>()

@Composable
fun WindowRegistry() {
    windowRegistry.forEach { (id, window) ->
        DockWindow(window)
    }
}

abstract class AppWindow(state: DockWindowState) {

    init {
        state.titleBar = { titleBar(state) }
        state.content = { content() }
        windowRegistry[state.id] = state
    }

    @Composable
    open fun titleBar(state: DockWindowState) {
        WindowTitleBarContent(state = state, showWindowButtons = false)
    }

    @Composable
    abstract fun content()

}