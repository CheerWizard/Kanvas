package com.cws.kanvas.shaderc.nodes

class UboNode(
    name: String,
    type: String,
    group: Int,
    binding: Int,
    vararg fields: FieldNode,
) : Node() {
    override val expr: String
}