package com.cws.kanvas.assetc.shader

import androidx.compose.runtime.Composable
import com.cws.kanvas.assetc.ShaderCompiler
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController

abstract class ShaderController() : Controller() {

}

class ShaderControllerImpl : ShaderController() {

    private val shaderCompiler: ShaderCompiler = ShaderCompiler()

    override fun onCreate() {
//        shaderCompiler.create()
    }

    override fun onDestroy() {
        shaderCompiler.destroy()
    }

}

class ShaderControllerPreview : ShaderController() {
    override fun onCreate() {}
    override fun onDestroy() {}
}

@Composable
fun provideShaderController(): ShaderController = provideController(
    preview = { ShaderControllerPreview() },
    impl = { ShaderControllerImpl() },
)
