package com.cws.kanvas.assetc.dialog

import androidx.compose.runtime.Composable

@Composable
fun DialogRegistry() {
    val dialogController = LocalDialogController.current
    dialogController.dialogs.forEach { (title, state) ->
        Dialog(state = state)
    }
}
