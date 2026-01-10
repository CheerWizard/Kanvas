package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Type

object VoidNode : Node() {
    override val expr: String = ""
    override val type: Type = Type.Void
}