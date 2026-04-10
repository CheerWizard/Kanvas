package com.cws.kanvas.shaderc.scopes.main

import com.cws.kanvas.shaderc.ScopeDsl
import com.cws.kanvas.shaderc.nodes.Var
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.std.math.Vec4

@ScopeDsl
open class MainScope : FunctionScope() {

    val gl_Position by Var(Vec4())

}