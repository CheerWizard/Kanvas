package com.cws.kanvas.shaderc.nodes

class BufferNode(
    name: String,
    type: String,
    group: Int,
    binding: Int,
    access: Access,
    vararg fields: FieldNode,
) : Node() {
    override val expr: String

    enum class Access {
        READ,
        WRITE,
        READ_WRITE;
        override fun toString(): String
    }
}