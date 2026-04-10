package com.cws.kanvas.editor.filebrowser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import com.cws.kanvas.editor.core.Controller
import com.cws.kanvas.editor.core.provideController
import com.cws.kanvas.editor.ui.components.DropMenuItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

abstract class FileBrowserController : Controller() {

    protected val _restored = MutableStateFlow(false)
    val restored = _restored.asStateFlow()

    protected val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    var state = FileBrowserState()

    val contextMenu = mutableStateListOf<DropMenuItemState>()

}

class FileBrowserControllerImpl(
    private val repo: FileBrowserRepo = FileBrowserRepo(),
) : FileBrowserController() {

    override fun onCreate() {
        scope.launch {
            repo.load()?.toState()?.let { state = it }
            _restored.value = true
            state.currentPath = "projects"
            navigateToPath(state.currentPath)
        }

        repeat(5) { i ->
            contextMenu.add(
                DropMenuItemState(
                    id = "file_$i",
                    title = "File $i",
                )
            )
        }

        repeat(5) { i ->
            contextMenu[0].innerItems.add(
                DropMenuItemState(
                    id = "inner_file_$i",
                    title = "Inner File $i",
                )
            )
        }
    }

    override fun onDestroy() {
        repo.save(state.toModel())
        _saved.value = true
    }

    private fun navigateToPath(path: String) {
        val dirFile = File(path)
        if (dirFile.isDirectory) {
            val files = mutableListOf<FileState>()
            dirFile.listFiles().forEach { file ->
                file.toFileType()?.let { fileType ->
                    files.add(
                        FileState(
                            name = file.nameWithoutExtension,
                            type = fileType,
                        )
                    )
                }
            }
            state.files = files
            state.currentPath = path
        }
    }

}

class FileBrowserControllerPreview : FileBrowserController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

@Composable
fun provideFileBrowserController(): FileBrowserController = provideController(
    preview = { FileBrowserControllerPreview() },
    impl = { FileBrowserControllerImpl() },
)
