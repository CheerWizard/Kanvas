package com.cws.kanvas.editor.logger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.mutableIntStateOf
import com.cws.kanvas.editor.core.Controller
import com.cws.kanvas.editor.core.provideController

abstract class LoggerController : Controller() {
    abstract val revision: IntState
    abstract fun getLogSize(): Int
    abstract fun getLog(index: Int): UiLog?
}

class LoggerControllerImpl(
    private val logger: UiLogger,
) : LoggerController() {

    override val revision: IntState = logger.revision

    override fun onCreate() {
        logger.open()
    }

    override fun onDestroy() {
        logger.close()
    }

    override fun getLog(index: Int): UiLog? {
        return logger.logs[index]
    }

    override fun getLogSize(): Int {
        return logger.logs.size
    }

}

class LoggerControllerPreview : LoggerController() {
    override val revision: IntState = mutableIntStateOf(0)
    override fun onCreate() {}
    override fun onDestroy() {}
    override fun getLogSize(): Int = 0
    override fun getLog(index: Int): UiLog? = null
}

@Composable
fun provideLoggerController(): LoggerController = provideController(
    preview = { LoggerControllerPreview() },
    impl = { LoggerControllerImpl() },
)