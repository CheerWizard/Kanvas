package com.cws.kanvas.rendering.frontend.shader_dsl

actual sealed interface Type {

    actual sealed interface Primitive : Type {

        actual data object Boolean : Primitive {
            actual override fun toString() = "bool"
        }

        actual data object Float : Primitive {
            actual override fun toString() = "float"
        }

        actual data object Int : Primitive {
            actual override fun toString() = "int"
        }

        actual data object UInt : Primitive {
            actual override fun toString() = "uint"
        }

    }

    actual sealed interface Math : Type {

        actual data object Float2 : Math {
            actual override fun toString() = "vec2"
        }

        actual data object Int2 : Math {
            actual override fun toString() = "ivec2"
        }

        actual data object UInt2 : Math {
            actual override fun toString() = "ivec2"
        }

        actual data object Float3 : Math {
            actual override fun toString() = "vec3"
        }

        actual data object Int3 : Math {
            actual override fun toString() = "ivec3"
        }

        actual data object UInt3 : Math {
            actual override fun toString() = "ivec3"
        }

        actual data object Float4 : Math {
            actual override fun toString() = "vec4"
        }

        actual data object Int4 : Math {
            actual override fun toString() = "ivec4"
        }

        actual data object UInt4 : Math {
            actual override fun toString() = "ivec4"
        }

        actual data object Float2x2 : Math {
            actual override fun toString() = "mat2"
        }

        actual data object Int2x2 : Math {
            actual override fun toString() = "mat2"
        }

        actual data object UInt2x2 : Math {
            actual override fun toString() = "mat2"
        }

        actual data object Float3x3 : Math {
            actual override fun toString() = "mat3"
        }

        actual data object Int3x3 : Math {
            actual override fun toString() = "mat3"
        }

        actual data object UInt3x3 : Math {
            actual override fun toString() = "mat3"
        }

        actual data object Float4x4 : Math {
            actual override fun toString() = "mat4"
        }

        actual data object Int4x4 : Math {
            actual override fun toString() = "mat4"
        }

        actual data object UInt4x4 : Math {
            actual override fun toString() = "mat4"
        }

    }

    actual data object Void : Type {
        actual override fun toString() = "void"
    }

    actual sealed interface Array : Type {

        actual class Sized actual constructor(
            itemType: Type,
            size: Int
        ) : Array {
            actual val itemType: Type = itemType
            actual val size: Int = size
            actual override fun toString() = "$itemType[$size]"
        }

        actual class Unsized actual constructor(
            itemType: Type,
        ) : Array {
            actual val itemType: Type = itemType
            actual override fun toString() = "$itemType[]"
        }

    }

    actual class Ref actual constructor(val type: Type) : Type {
        actual override fun toString() = "inout $type"
    }

    actual class Struct actual constructor(
        name: String,
        fields: kotlin.Array<Field>,
    ) : Type {
        actual val name: String = name
        actual val fields: kotlin.Array<Field> = fields

        actual override fun toString() = "struct $name {\n${
            fields.joinToString("\n") { it.toString() }
        }};"

        actual class Field actual constructor(
            name: String,
            type: Type,
        ) {
            actual val name: String = name
            actual val type: Type = type

            actual override fun toString(): String = "$type $name;"
        }

    }

    actual sealed interface Texture : Type {

        actual class D1 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itexture1D"
                is Primitive.UInt -> "utexture1D"
                else -> "texture1D"
            }
        }

        actual class D2 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itexture2D"
                is Primitive.UInt -> "utexture2D"
                else -> "texture2D"
            }
        }

        actual class D3 actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itexture3D"
                is Primitive.UInt -> "utexture3D"
                else -> "texture3D"
            }
        }

        actual class Cube actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itextureCube"
                is Primitive.UInt -> "utextureCube"
                else -> "textureCube"
            }
        }

        actual class D2Array actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itexture2DArray"
                else -> "texture2DArray"
            }
        }

        actual class CubeArray actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itextureCubeArray"
                else -> "textureCubeArray"
            }
        }

        actual class Multisample actual constructor(val type: Primitive) : Texture {
            actual override fun toString() = when (type) {
                is Primitive.Int -> "itexture2DMS"
                else -> "texture2DMS"
            }
        }

    }

    actual sealed interface Sampler : Type {

        actual data object Default : Texture {
            actual override fun toString() = "sampler"
        }

        actual data object Shadow : Texture {
            actual override fun toString() = "samplerShadow"
        }

    }

}