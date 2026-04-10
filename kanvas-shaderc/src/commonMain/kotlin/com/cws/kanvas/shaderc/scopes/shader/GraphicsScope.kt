package com.cws.kanvas.shaderc.scopes.shader

import com.cws.kanvas.shaderc.ScopeDsl
import com.cws.kanvas.shaderc.nodes.VoidNode
import com.cws.kanvas.shaderc.scopes.main.MainScope
import com.cws.kanvas.shaderc.translation.Translation

@ScopeDsl
open class GraphicsScope: ShaderScope()

inline fun GraphicsScope.main(block: MainScope.() -> Unit) {
    val mainScope = MainScope()
    mainScope.block()
    appendLine(Translation.function(
        Translation.type<VoidNode>(),
        "main",
        "",
        mainScope,
        ""
    ))
}