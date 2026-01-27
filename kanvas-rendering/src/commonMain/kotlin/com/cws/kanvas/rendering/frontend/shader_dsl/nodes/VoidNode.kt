package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type

object VoidNode : Node() {
    override val expr: String = ""
    override val type: Type = Type.Void
}