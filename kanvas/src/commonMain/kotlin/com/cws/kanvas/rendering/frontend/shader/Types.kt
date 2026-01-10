package com.cws.kanvas.rendering.frontend.shader

import com.cws.kanvas.rendering.frontend.shader.nodes.ArrayNode
import com.cws.kanvas.rendering.frontend.shader.nodes.BooleanNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Float2Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float2x2Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float3x3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float4x4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.FloatNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Int2Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Int2x2Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Int3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Int3x3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Int4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Int4x4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.IntNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Node
import com.cws.kanvas.rendering.frontend.shader.nodes.UInt2Node
import com.cws.kanvas.rendering.frontend.shader.nodes.UInt3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.UInt4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.UIntNode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.serialDescriptor

expect sealed interface Type {

    sealed interface Primitive : Type {

        data object Boolean : Primitive {
            override fun toString(): String
        }

        data object Float : Primitive {
            override fun toString(): String
        }

        data object Int : Primitive {
            override fun toString(): String
        }

        data object UInt : Primitive {
            override fun toString(): String
        }

    }

    sealed interface Math : Type {

        data object Float2 : Math {
            override fun toString(): String
        }

        data object Float3 : Math {
            override fun toString(): String
        }

        data object Float4 : Math {
            override fun toString(): String
        }

        data object Int2 : Math {
            override fun toString(): String
        }

        data object Int3 : Math {
            override fun toString(): String
        }

        data object Int4 : Math {
            override fun toString(): String
        }

        data object UInt2 : Math {
            override fun toString(): String
        }

        data object UInt3 : Math {
            override fun toString(): String
        }

        data object UInt4 : Math {
            override fun toString(): String
        }

        data object Float2x2 : Math {
            override fun toString(): String
        }

        data object Float3x3 : Math {
            override fun toString(): String
        }

        data object Float4x4 : Math {
            override fun toString(): String
        }

        data object Int2x2 : Math {
            override fun toString(): String
        }

        data object Int3x3 : Math {
            override fun toString(): String
        }

        data object Int4x4 : Math {
            override fun toString(): String
        }

        data object UInt2x2 : Math {
            override fun toString(): String
        }

        data object UInt3x3 : Math {
            override fun toString(): String
        }

        data object UInt4x4 : Math {
            override fun toString(): String
        }

    }

    data object Void : Type {
        override fun toString(): String
    }

    sealed interface Array : Type {

        class Sized(itemType: Type, size: Int) : Array {
            val itemType: Type
            val size: Int
            override fun toString(): String
        }

        class Unsized(itemType: Type) : Array {
            val itemType: Type
            override fun toString(): String
        }

    }

    class Ref(type: Type) : Type {
        override fun toString(): String
    }

    class Struct(
        name: String,
        fields: kotlin.Array<Field>,
    ) : Type {
        val name: String
        val fields: kotlin.Array<Field>

        override fun toString(): String

        class Field(
            name: String,
            type: Type,
        ) {
            val name: String
            val type: Type
            override fun toString(): String
        }

    }

    sealed interface Texture : Type {

        class D1(type: Primitive) : Texture {
            override fun toString(): String
        }

        class D2(type: Primitive) : Texture {
            override fun toString(): String
        }

        class D3(type: Primitive) : Texture {
            override fun toString(): String
        }

        class Cube(type: Primitive) : Texture {
            override fun toString(): String
        }

        class D2Array(type: Primitive) : Texture {
            override fun toString(): String
        }

        class CubeArray(type: Primitive) : Texture {
            override fun toString(): String
        }

        class Multisample(type: Primitive) : Texture {
            override fun toString(): String
        }

    }

    sealed interface Sampler : Type {

        data object Default : Texture {
            override fun toString(): String
        }

        data object Shadow : Texture {
            override fun toString(): String
        }

    }

}

inline fun <reified T> type(): Type = type(serialDescriptor<T>())

@OptIn(ExperimentalSerializationApi::class)
fun type(descriptor: SerialDescriptor): Type {
    val kind = descriptor.kind
    return when (kind) {
        is PrimitiveKind.FLOAT -> Type.Primitive.Float
        is PrimitiveKind.INT -> Type.Primitive.Float
        is StructureKind.LIST -> {
            val type = type(descriptor.getElementDescriptor(0))
            val size = descriptor.getElementAnnotations(0)
                .filterIsInstance<ArraySize>()
                .firstOrNull()
                ?.size
            if (size == null) Type.Array.Unsized(type) else Type.Array.Sized(type, size)
        }
        is StructureKind.CLASS -> {
            val fields = mutableListOf<Type.Struct.Field>()
            for (i in 0 until descriptor.elementsCount) {
                fields.add(Type.Struct.Field(
                    descriptor.getElementName(i),
                    type(descriptor.getElementDescriptor(i)),
                ))
            }
            Type.Struct(descriptor.serialName, fields.toTypedArray())
        }
        else -> error("Unsupported Kotlin kind $kind for shader type!")
    }
}

inline fun<reified T : Node> node(type: Type, expr: String): T {
    return when (type) {
        is Type.Primitive.Boolean -> BooleanNode(expr)
        is Type.Primitive.Float -> FloatNode(expr)
        is Type.Primitive.Int -> IntNode(expr)
        is Type.Primitive.UInt -> UIntNode(expr)

        is Type.Math.Float2 -> Float2Node(expr)
        is Type.Math.Float3 -> Float3Node(expr)
        is Type.Math.Float4 -> Float4Node(expr)
        is Type.Math.Int2 -> Int2Node(expr)
        is Type.Math.Int3 -> Int3Node(expr)
        is Type.Math.Int4 -> Int4Node(expr)
        is Type.Math.UInt2 -> UInt2Node(expr)
        is Type.Math.UInt3 -> UInt3Node(expr)
        is Type.Math.UInt4 -> UInt4Node(expr)

        is Type.Math.Float2x2 -> Float2x2Node(expr)
        is Type.Math.Float3x3 -> Float3x3Node(expr)
        is Type.Math.Float4x4 -> Float4x4Node(expr)
        is Type.Math.Int2x2 -> Int2x2Node(expr)
        is Type.Math.Int3x3 -> Int3x3Node(expr)
        is Type.Math.Int4x4 -> Int4x4Node(expr)

        is Type.Array.Unsized -> ArrayNode(expr,  type.itemType, 0)
        is Type.Array.Sized -> ArrayNode(expr, type.itemType, type.size)

        is Type.Struct -> Node.create(expr)

        else -> error("Unsupported Type $type for Node!")
    } as T
}