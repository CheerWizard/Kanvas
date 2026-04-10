package com.cws.kanvas.shaderc.translation

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.nodes.Float3Node
import com.cws.kanvas.shaderc.nodes.FloatNode
import com.cws.kanvas.shaderc.nodes.Node
import com.cws.kanvas.shaderc.nodes.SamplerNode
import com.cws.kanvas.shaderc.nodes.TextureNode
import com.cws.kanvas.shaderc.scopes.function.FunctionScope

interface Translator {
    fun type(type: Type): String

    fun declare(type: Type, name: String, expr: String? = null): String

    fun function(type: Type, name: String, args: String, scope: FunctionScope, result: String): String

    fun <T : Node> mod(value: T): String

    fun layout(group: Int, binding: Int, alignment: String = ""): String

    fun sample(samplerNode: SamplerNode, textureNode: TextureNode, uv: Float2Node): String

    fun texture(samplerNode: SamplerNode, uv: Float2Node): String
    fun texture(samplerNode: SamplerNode, uv: Float3Node): String
    fun texture(samplerNode: SamplerNode, uv: Float2Node, bias: FloatNode): String
    fun textureLod(samplerNode: SamplerNode, uv: Float2Node): String
    fun textureLod(samplerNode: SamplerNode, uv: Float2Node, lod: FloatNode): String
    fun textureGrad(samplerNode: SamplerNode, uv: Float2Node, dx: Float2Node, dy: Float2Node): String

    fun gl_Position(): String
}