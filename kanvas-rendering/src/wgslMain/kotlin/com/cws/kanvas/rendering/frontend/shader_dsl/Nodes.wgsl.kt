package com.cws.kanvas.rendering.frontend.shader_dsl

actual class ParamNode actual constructor(
    val name: String,
    val type: Type
) : DefaultNode() {
    actual override fun toString() = "$name: $type"
}

actual class ForInitNode actual constructor(
    val type: Type,
    val name: String,
    val value: String
) : DefaultNode() {
    actual override fun toString() = "var $name: $type = $value"
}

actual class ForStepNode actual constructor(
    val name: String,
    val value: String
) : DefaultNode() {
    actual override fun toString() = "$name "
}

actual class FunctionDeclareNode actual constructor(
    val name: String,
    vararg val params: ParamNode,
    val returnType: Type,
    val block: BlockNode,
) : DefaultNode() {

    actual override fun toString(): String = if (returnType is Type.Void) {
        "fn $name(${params.value}) $block"
    } else {
        "fn $name(${params.value}) -> $returnType $block"
    }

}

actual class LocalVarNode actual constructor(
    val name: String,
    val type: Type,
    val initializer: Node
) : DefaultNode() {
    actual override fun toString() = "var $name: $type = $initializer;"
}

actual class GlobalVarNode actual constructor(
    val name: String,
    val type: Type
) : DefaultNode() {
    actual override fun toString() = "var<private> $name: $type;"
}

actual class SharedVarNode actual constructor(
    val name: String,
    val type: Type
) : DefaultNode() {
    actual override fun toString() = "var<workgroup> $name: $type;"
}

actual class FieldNode actual constructor(
    val name: String,
    val type: Type
) : DefaultNode() {
    actual override fun toString() = "$name: $type,"
}

actual val StructNode.ending: Char get() = ' '

internal actual fun BindLayoutOp.layout(alignment: String): String = "@group($group) @binding($binding)"

actual class UboNode actual constructor(
    val name: String,
    val type: String,
    actual override val group: Int,
    actual override val binding: Int,
    vararg val fields: FieldNode
) : DefaultNode(), BindLayoutOp {
    actual override fun toString(): String {
        val struct = StructNode(type, *fields)
        val ubo = "${layout()} var<uniform> $name: $type;"
        return "$struct\n\n$ubo"
    }
}

actual class BufferNode actual constructor(
    val name: String,
    val type: String,
    actual override val group: Int,
    actual override val binding: Int,
    val access: Access,
    vararg val fields: FieldNode
) : DefaultNode(), BindLayoutOp {
    actual override fun toString(): String {
        val struct = StructNode(type, *fields)
        val ubo = "@group($group) @binding($binding) var<storage, $access> $name: $type;"
        return "$struct\n\n$ubo"
    }

    actual enum class Access(val value: String) {
        READ("read"),
        WRITE("read_write"),
        READ_WRITE("read_write");
        actual override fun toString(): String = value
    }
}

actual class SamplerNode actual constructor(
    val name: String,
    actual override val group: Int,
    actual override val binding: Int
) : DefaultNode(), BindLayoutOp {
    actual override fun toString() = "${layout()} var $name: sampler;"
}

actual class ShadowSamplerNode actual constructor(
    val name: String,
    actual override val group: Int,
    actual override val binding: Int
) : DefaultNode(), BindLayoutOp {
    actual override fun toString() = "${layout()} var $name: sampler_comparison;"
}