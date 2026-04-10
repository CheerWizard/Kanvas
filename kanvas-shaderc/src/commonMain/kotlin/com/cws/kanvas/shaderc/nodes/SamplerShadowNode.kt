package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation
import kotlin.properties.ReadOnlyProperty

private val register = Node.register { SamplerShadowNode(it) }

class SamplerShadowNode(
    override val expr: String,
) : Node() {

    context(scope: FunctionScope)
    fun sample(textureNode: TextureNode, uv: Float2Node) =
        ReadOnlyProperty<Any?, Float4Node> { thisRef, property ->
            val varName = property.name
            scope.appendLine(Translation.declare(Translation.type<Float4Node>(), varName, Translation.textureSample(expr, textureNode, uv)))
            Float4Node(varName)
        }

}