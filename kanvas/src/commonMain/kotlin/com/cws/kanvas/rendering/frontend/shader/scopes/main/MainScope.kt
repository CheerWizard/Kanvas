package com.cws.kanvas.rendering.frontend.shader.scopes.main

import com.cws.kanvas.rendering.frontend.shader.ScopeDsl
import com.cws.kanvas.rendering.frontend.shader.nodes.Float4Node
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

@ScopeDsl
open class MainScope : FunctionScope() {

    val gl_Position = Float4Node("gl_Position")

}