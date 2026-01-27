package com.cws.kanvas.rendering.frontend.shader_dsl

import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.Node

internal expect fun layout(group: Int, binding: Int, alignment: String = ""): String

expect class UboNode(
    name: String,
    type: String,
    group: Int,
    binding: Int,
    vararg fields: FieldNode,
) : Node {
    override val expr: String
}

expect class BufferNode(
    name: String,
    type: String,
    group: Int,
    binding: Int,
    access: Access,
    vararg fields: FieldNode,
) : Node {
    override val expr: String

    enum class Access {
        READ,
        WRITE,
        READ_WRITE;
        override fun toString(): String
    }
}