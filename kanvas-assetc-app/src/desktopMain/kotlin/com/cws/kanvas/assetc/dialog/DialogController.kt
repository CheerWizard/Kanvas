package com.cws.kanvas.assetc.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.staticCompositionLocalOf
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController

val LocalDialogController = staticCompositionLocalOf<DialogController> {
    error("LocalDialogController is not initialized!")
}

abstract class DialogController : Controller() {

    val dialogs = mutableStateMapOf<String, DialogState>()

    fun show(state: DialogState) {
        dialogs[state.title] = state
    }

    fun hide(title: String) {
        dialogs.remove(title)
    }

}

class DialogControllerImpl : DialogController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

class DialogControllerPreview : DialogController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

@Composable
fun provideDialogController(): DialogController = provideController(
    preview = { DialogControllerPreview() },
    impl = { DialogControllerImpl() },
)
