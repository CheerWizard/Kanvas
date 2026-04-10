package com.cws.kanvas.shaderc.translation

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.nodes.*
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.print.Print

class WGSLTranslator : Translator {

    companion object {
        private val TAG = WGSLTranslator::class.simpleName.orEmpty()
    }

    override fun type(nodeType: String): String {
        return when (nodeType) {
            // void
            VoidNode::class.simpleName -> ""

            // primitives
            BooleanNode::class.simpleName -> "bool"
            IntNode::class.simpleName -> "i32"
            FloatNode::class.simpleName -> "f32"

            // vectors (int)
            Int2Node::class.simpleName -> "vec2<i32>"
            Int3Node::class.simpleName -> "vec3<i32>"
            Int4Node::class.simpleName -> "vec4<i32>"

            // vectors (float)
            Float2Node::class.simpleName -> "vec2<f32>"
            Float3Node::class.simpleName -> "vec3<f32>"
            Float4Node::class.simpleName -> "vec4<f32>"

            // matrices
            Int2x2Node::class.simpleName -> "mat2x2<i32>"
            Int3x3Node::class.simpleName -> "mat3x3<i32>"
            Int4x4Node::class.simpleName -> "mat4x4<i32>"
            Float2x2Node::class.simpleName -> "mat2x2<f32>"
            Float3x3Node::class.simpleName -> "mat3x3<f32>"
            Float4x4Node::class.simpleName -> "mat4x4<f32>"

            // texture / samplers (separate in WGSL)
            TextureNode::class.simpleName -> "texture_2d<f32>"
            SamplerNode::class.simpleName -> "sampler"
            SamplerShadowNode::class.simpleName -> "sampler_comparison"

            // struct
            else -> nodeType.removeSuffix("Node")
        }
    }

    override fun const(): String = "let"

    override fun declare(type: String, name: String, expr: String?): String {
        return if (expr == null) {
            "$name: $type;"
        } else {
            "$name: $type = $expr;"
        }
    }

    override fun function(
        type: String,
        name: String,
        args: String,
        scope: FunctionScope,
        result: String
    ): String {
        val ending = if (result.isEmpty()) "" else "\n$result"
        return if (type.isEmpty()) {
            "fn $name($args) {\n$scope$ending\n}"
        } else {
            "fn $name($args) -> $type {\n$scope$ending\n}"
        }
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