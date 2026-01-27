package com.cws.kanvas.rendering.frontend.shader_dsl.scopes.main

import com.cws.kanvas.rendering.frontend.shader_dsl.ScopeDsl
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.Float4Node
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

@ScopeDsl
open class MainScope : FunctionScope() {

    val gl_Position = Float4Node("gl_Position")

}