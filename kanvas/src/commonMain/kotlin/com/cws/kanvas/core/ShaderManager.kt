package com.cws.kanvas.core

import com.cws.kanvas.gfx.core.Kanvas
import com.cws.kanvas.loaders.ShaderLoader
import com.cws.printer.Printer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ShaderManager(
    private val shaderLoader: ShaderLoader,
    private val jobsManager: JobsManager,
) {

    companion object {
        private const val TAG = "ShaderManager"
    }

    private const val TAG = "ShaderManager"

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun load(
        vararg names: String,
        onLoaded: (List<Pair<Int, String>>) -> Unit
    ) {
        scope.launch {
            val jobs = names.map { name ->
                async {
                    val shaderType = name.toShaderType()
                    if (shaderType == Kanvas.NULL) return@async shaderType to ""
                    shaderType to shaderLoader.load(name)
                }
            }
            val sources = jobs.awaitAll()
            jobsManager.push { onLoaded(sources) }
        }
    }

    private fun String.toShaderType(): Int {
        return when {
            contains(".vert") -> Kanvas.VERTEX_SHADER
            contains(".frag") -> Kanvas.FRAGMENT_SHADER
            else -> {
                Printer.w(TAG, "Unsupported shader type $this")
                Kanvas.NULL
            }
        }
    }

}