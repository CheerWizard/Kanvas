package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type

abstract class StructNode(
    type: Type.Struct,
) : Node() {
    override val type: Type = type
}