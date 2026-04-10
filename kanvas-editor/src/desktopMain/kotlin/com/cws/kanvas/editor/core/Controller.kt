package com.cws.kanvas.editor.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class Controller {

    protected val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    abstract fun onCreate()
    protected abstract fun onDestroy()

    fun dispose() {
        onDestroy()
        scope.cancel()
    }

}

@Composable
fun <T : Controller> provideController(
    preview: () -> T,
    impl: () -> T,
): T {
    val isPreview = LocalInspectionMode.current

    val controller by remember(isPreview) {
        mutableStateOf(if (isPreview) preview() else impl())
    }

    LaunchedEffect(Unit) {
        controller.onCreate()
    }

    DisposableEffect(Unit) {
        onDispose {
            controller.dispose()
        }
    }

    return controller
}
