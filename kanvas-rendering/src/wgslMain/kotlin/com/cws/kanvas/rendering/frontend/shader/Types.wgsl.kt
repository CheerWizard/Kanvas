package com.cws.kanvas.rendering.frontend.shader

actual sealed interface Type {

    actual sealed interface Primitive : Type {

        actual data object Boolean : Primitive {
            actual override fun toString() = "bool"
        }

        actual data object Float : Primitive {
            actual override fun toString() = "f32"
        }

        actual data object Int : Primitive {
            actual override fun toString() = "i32"
        }

        actual data object UInt : Primitive {
            actual override fun toString() = "u32"
        }

    }

    actual sealed interface Math : Type {

        actual data object Float2 : Math {
            actual override fun toString() = "vec2<${Primitive.Float}>"
        }

        actual data object Int2 : Math {
            actual override fun toString() = "vec2<${Primitive.Int}>"
        }

        actual data object UInt2 : Math {
            actual override fun toString() = "vec2<${Primitive.UInt}>"
        }

        actual data object Float3 : Math {
            actual override fun toString() = "vec3<${Primitive.Float}>"
        }

        actual data object Int3 : Math {
            actual override fun toString() = "vec3<${Primitive.Int}>"
        }

        actual data object UInt3 : Math {
            actual override fun toString() = "vec3<${Primitive.UInt}>"
        }

        actual data object Float4 : Math {
            actual override fun toString() = "vec4<${Primitive.Float}>"
        }

        actual data object Int4 : Math {
            actual override fun toString() = "vec4<${Primitive.Int}>"
        }

        actual data object UInt4 : Math {
            actual override fun toString() = "vec4<${Primitive.UInt}>"
        }

        actual data object Float2x2 : Math {
            actual override fun toString() = "mat2x2<${Primitive.Float}>"
        }

        actual data object Int2x2 : Math {
            actual override fun toString() = "mat2x2<${Primitive.Int}>"
        }

        actual data object UInt2x2 : Math {
            actual override fun toString() = "mat2x2<${Primitive.UInt}>"
        }

        actual data object Float3x3 : Math {
            actual override fun toString() = "mat3x3<${Primitive.Float}>"
        }

        actual data object Int3x3 : Math {
            actual override fun toString() = "mat3x3<${Primitive.Int}>"
        }

        actual data object UInt3x3 : Math {
            actual override fun toString() = "mat3x3<${Primitive.UInt}>"
        }

        actual data object Float4x4 : Math {
            actual override fun toString() = "mat4x4<${Primitive.Float}>"
        }

        actual data object Int4x4 : Math {
            actual override fun toString() = "mat4x4<${Primitive.Int}>"
        }

        actual data object UInt4x4 : Math {
            actual override fun toString() = "mat4x4<${Primitive.UInt}>"
        }
    }

    actual data object Void : Type {
        actual override fun toString() = "void"
    }

    actual sealed interface Array : Type {

        actual class Sized actual constructor(
            itemType: Type,
            size: Int,
        ) : Array {
            actual val itemType: Type = itemType
            actual val size: Int = size
            actual override fun toString() = "array<$itemType, $size>"
        }

        actual class Unsized actual constructor(
            itemType: Type,
        ) : Array {
            actual val itemType: Type = itemType
            actual override fun toString() = "array<$itemType>"
        }

    }

    actual class Ref actual constructor(val type: Type) : Type {
        actual override fun toString() = "ptr<function, $type>"
    }

    actual class Struct actual constructor(
        name: String,
        fields: kotlin.Array<Field>,
    ) : Type {
        actual val name: String = name
        actual val fields: kotlin.Array<Field> = fields

        actual override fun toString() = "struct $name {\n${
            fields.joinToString("\n") { it.toString() }
        }}"

        actual class Field actual constructor(
            name: String,
            type: Type,
        ) {
            actual val name: String = name
            actual val type: Type = type

            actual override fun toString(): String = "$name: $type,"
        }

    }

    actual sealed interface Texture : Type {

        actual class D1 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_1d<$type>"
        }

        actual class D2 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_2d<$type>"
        }

        actual class D3 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_3d<$type>"
        }

        actual class Cube actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_cube<$type>"
        }

        actual class D2Array actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_2d_array<$type>"
        }

        actual class CubeArray actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_cube_array<$type>"
        }

        actual class Multisample actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = "texture_multisampled_2d<$type>"
        }

    }

    actual sealed interface Sampler : Type {

        actual data object Default : Texture {
            actual override fun toString(): String {
                TODO("Not yet implemented")
            }
        }

        actual data object Shadow : Texture {
            actual override fun toString(): String {
                TODO("Not yet implemented")
            }
        }

    }

}