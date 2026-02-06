package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SamplerShadowNode(
    override val expr: String,
    override val type: Type,
) : Node() {

    context(scope: FunctionScope)
    fun sample(textureNode: TextureNode, uv: Float2Node) =
        ReadOnlyProperty<Any?, Float4Node> { thisRef, property ->
            val varName = property.name
            scope.appendLine("vec4 $varName = textureSample($expr, $textureNode, $uv);")
            Float4Node(varName)
        }

}