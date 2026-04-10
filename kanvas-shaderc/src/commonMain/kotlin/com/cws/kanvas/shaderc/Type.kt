package com.cws.kanvas.shaderc

import com.cws.kanvas.shaderc.translation.Translation

sealed interface Type {

    val expr: String get() = Translation.type(this)

    // Primitives
    data object Boolean : Type
    data object Float : Type
    data object Int : Type

    // Vectors
    data object Float2 : Type
    data object Int2 : Type
    data object Float3 : Type
    data object Int3 : Type
    data object Float4 : Type
    data object Int4 : Type

    // Matrices
    data object Float2x2 : Type
    data object Int2x2 : Type
    data object Float3x3 : Type
    data object Int3x3 : Type
    data object Float4x4 : Type
    data object Int4x4 : Type

    // Void
    data object Void : Type

    // Arrays
    data class ArraySized(val type: Type, val size: kotlin.Int) : Type {
        override val expr: String = "${type.expr}[$size]"
    }
    data class ArrayUnsized(val type: Type) : Type {
        override val expr: String = "${type.expr}[]"
    }

    // Reference
    data class Ref(val type: Type) : Type

    // Struct
    data class Struct(val name: String, val fields: Array<Field>) : Type {

        override val expr: String get() = "struct $name {\n${
            fields.joinToString("\n") { it.toString() }
        }};"

        data class Field(val name: String, val type: Type) {
            override fun toString(): String = Translation.declare(type, name)
        }

    }

    // Textures
    data class Texture1(val type: Type) : Type
    data class Texture2(val type: Type) : Type
    data class Texture3(val type: Type) : Type
    data class Texture2Array(val type: Type) : Type
    data class TextureCube(val type: Type) : Type
    data class TextureCubeArray(val type: Type) : Type
    data class TextureMultisample(val type: Type) : Type

    // Samplers
    data object Sampler : Type
    data object SamplerShadow : Type

}