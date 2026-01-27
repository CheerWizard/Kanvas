package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SamplerNode(
    override val expr: String,
) : Node() {

    override val type: Type = Type.Sampler.Default

    context(scope: FunctionScope)
    fun sample(textureNode: TextureNode, uv: Float2Node) = object : ReadOnlyProperty<Any?, Float4Node> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Float4Node {
            val varName = property.name
            scope.appendLine("vec4 $varName = textureSample($textureNode, ${this@SamplerNode}, $uv);")
            return Float4Node(varName)
        }
    }

}