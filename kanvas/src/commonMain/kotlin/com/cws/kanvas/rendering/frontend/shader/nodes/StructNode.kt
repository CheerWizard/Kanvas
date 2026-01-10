package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Type

abstract class StructNode(
    type: Type.Struct,
) : Node() {
    override val type: Type = type
}