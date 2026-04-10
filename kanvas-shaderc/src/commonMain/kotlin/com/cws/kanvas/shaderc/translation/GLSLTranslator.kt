package com.cws.kanvas.shaderc.translation

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.print.Print

class GLSLTranslator : Translator {

    companion object {
        private val TAG = GLSLTranslator::class.simpleName.orEmpty()
    }

    override fun type(type: Type): String {
        return when (type) {
            // void
            is Type.Void -> "void"

            // primitives
            is Type.Boolean -> "bool"
            is Type.Int -> "int"
            is Type.Float-> "float"

            // vectors (int)
            is Type.Int2 -> "ivec2"
            is Type.Int3 -> "ivec3"
            is Type.Int4 -> "ivec4"

            // vectors (float)
            is Type.Float2 -> "vec2"
            is Type.Float3 -> "vec3"
            is Type.Float4 -> "vec4"

            // matrices (GLSL uses float matrices)
            is Type.Int2x2 -> "mat2"
            is Type.Int3x3 -> "mat3"
            is Type.Int4x4 -> "mat4"
            is Type.Float2x2 -> "mat2"
            is Type.Float3x3 -> "mat3"
            is Type.Float4x4 -> "mat4"

            // reference
            is Type.Ref -> "inout ${type.type.expr}"

            // textures
            is Type.Texture1 -> when (type.type) {
                is Type.Int -> "itexture1D"
                is Type.Float -> "texture1D"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.Texture2 -> when (type.type) {
                is Type.Int -> "itexture2D"
                is Type.Float -> "texture2D"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.Texture3 -> when (type.type) {
                is Type.Int -> "itexture3D"
                is Type.Float -> "texture3D"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.Texture2Array -> when (type.type) {
                is Type.Int -> "itexture2DArray"
                is Type.Float -> "texture2DArray"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.TextureCube -> when (type.type) {
                is Type.Int -> "itextureCube"
                is Type.Float -> "textureCube"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.TextureCubeArray -> when (type.type) {
                is Type.Int -> "itextureCubeArray"
                is Type.Float -> "textureCubeArray"
                else -> printUnsupportedPixel(type, type.type)
            }
            is Type.TextureMultisample -> when (type.type) {
                is Type.Int -> "itexture2DMultisample"
                is Type.Float -> "texture2DMultisample"
                else -> printUnsupportedPixel(type, type.type)
            }

            is Type.Sampler -> "sampler2D"
            is Type.SamplerShadow -> "sampler2DShadow"

            else -> printError("Unsupported type $type")
        }
    }

    override fun declare(type: Type, name: String, expr: String?): String {
        return if (expr == null) {
            "${type.expr} $name;"
        } else {
            "${type.expr} $name = $expr;"
        }
    }

    override fun function(
        type: Type,
        name: String,
        args: String,
        scope: FunctionScope,
        result: String
    ): String {
        val ending = if (result.isEmpty()) "" else "\n$result"
        return "${type.expr} $name($args) {\n$scope$ending\n}"
    }

    override fun gl_Position(): String = "gl_Position"

    private fun printUnsupportedPixel(textureType: Type, pixelType: Type): String {
        return printError("Unsupported $textureType pixel type $pixelType")
    }

    private fun printError(message: String): String {
        Print.e(TAG, "Error: $message")
        return ""
    }

}