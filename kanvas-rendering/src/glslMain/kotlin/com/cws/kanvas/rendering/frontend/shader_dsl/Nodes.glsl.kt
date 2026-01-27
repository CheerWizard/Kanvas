package com.cws.kanvas.rendering.frontend.shader_dsl

actual class ParamNode actual constructor(
    val name: String,
    val type: Type
) : Node {
    actual override fun toString() = "$type $name"
}

actual class ForInitNode actual constructor(
    val type: Type,
    val name: String,
    val value: String
) : Node {
    actual override fun toString(): String = "$type $name = $value"
}

actual class ForStepNode actual constructor(name: String, value: String) : Node {
    actual override fun toString(): String {
        TODO("Not yet implemented")
    }
}

actual class FunctionDeclareNode actual constructor(
    val name: String,
    vararg val params: ParamNode,
    val returnType: Type,
    val block: BlockNode,
) : Node {
    actual override fun toString(): String = "$returnType $name(${params.value}) $block"
}

actual class LocalVarNode actual constructor(
    val name: String,
    val type: Type,
    val initializer: Node
) : Node {
    actual override fun toString() = "$type $name = $initializer;"
}

actual class GlobalVarNode actual constructor(
    val name: String,
    val type: Type
) : Node {
    actual override fun toString() = "$type $name;"
}

actual class SharedVarNode actual constructor(
    val name: String,
    val type: Type
) : Node {
    actual override fun toString() = "shared $type $name;"
}

actual class FieldNode actual constructor(
    val name: String,
    val type: Type
) : Node {
    actual override fun toString() = "$type $name;"
}

actual val StructNode.ending: Char get() = ';'

actual class UboNode actual constructor(
    val name: String,
    val type: String,
    actual override val group: Int,
    actual override val binding: Int,
    vararg val fields: FieldNode
) : Node {
    actual override fun toString() = "${layout("std140")} uniform $type {\n${fields.value}} $name;"
}

actual class BufferNode actual constructor(
    val name: String,
    val type: String,
    actual override val group: Int,
    actual override val binding: Int,
    val access: Access,
    vararg val fields: FieldNode
) : Node {
    actual override fun toString() = "${layout("std430")} $access buffer $type {\n${fields.value}} $name;"

    actual enum class Access(val value: String) {
        READ("readonly"),
        WRITE("writeonly"),
        READ_WRITE("");
        actual override fun toString(): String = value
    }
}

internal actual fun layout(group: Int, binding: Int, alignment: String): String = if (alignment.isEmpty()) {
    "layout(set = $group, binding = $binding)"
} else {
    "layout(set = $group, binding = $binding, $alignment)"
}

actual class SamplerNode actual constructor(
    val name: String,
    actual override val group: Int,
    actual override val binding: Int
) : Node {
    actual override fun toString() = "${layout()} uniform sampler $name;"
}

actual class ShadowSamplerNode actual constructor(
    val name: String,
    val group: Int,
    val binding: Int
) : Node {
    actual override fun toString() = "${layout()} uniform samplerShadow $name;"
}