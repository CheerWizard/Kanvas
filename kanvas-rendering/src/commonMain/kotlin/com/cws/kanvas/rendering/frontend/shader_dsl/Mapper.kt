package com.cws.kanvas.rendering.frontend.shader_dsl

import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.BooleanNode
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.Node
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.serializer
import kotlin.reflect.typeOf

interface NodeMapper<T, out N : Node> {
    fun toNode(expr: String): N
}

object BooleanNodeMapper : NodeMapper<Boolean, BooleanNode> {
    override fun toNode(expr: String) = BooleanNode(expr)
}

class StructNodeMapper<K>(val structName: String) : NodeMapper<K, Node> {
    override fun toNode(expr: String): Node {
        return NameNode(expr)
    }
}

@OptIn(ExperimentalUnsignedTypes::class, ExperimentalSerializationApi::class)
inline fun <reified T : Any, reified N : Node> mapper(): NodeMapper<T, N> {
    val clazz = T::class
    val type = typeOf<T>()
    return when (clazz) {
        Boolean::class -> BooleanNodeMapper
        Float::class -> FloatNodeMapper
        Int::class -> IntNodeMapper
        UInt::class -> UIntNodeMapper

        FloatArray::class -> FloatArrayNodeMapper
        IntArray::class -> IntArrayNodeMapper
        UIntArray::class -> UIntArrayNodeMapper

        else -> {
            val descriptor = serializer<T>().descriptor
            if (descriptor.kind == StructureKind.CLASS) {
                StructNodeMapper<T>(descriptor.serialName)
            } else {
                error("Unsupported type: $clazz")
            }
        }
    } as NodeMapper<T, N>
}