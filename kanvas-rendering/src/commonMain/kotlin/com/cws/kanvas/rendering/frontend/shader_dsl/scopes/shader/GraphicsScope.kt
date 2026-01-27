package com.cws.kanvas.rendering.frontend.shader_dsl.scopes.shader

import com.cws.kanvas.rendering.frontend.shader_dsl.ScopeDsl
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.main.MainScope

@ScopeDsl
open class GraphicsScope: ShaderScope()

inline fun GraphicsScope.main(block: MainScope.() -> Unit) {
    val mainScope = MainScope()
    mainScope.block()
    appendLine("void main() {")
    appendLine(mainScope.toString())
    appendLine("}")
}