package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SamplerShadowNode(
    name: String
) : NameNode(name) {

    fun FunctionScope.sample(textureNode: TextureNode, uv: FVec2Node) = object : ReadOnlyProperty<Any?, FVec4Node> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): FVec4Node {
            val varName = property.name
            this@sample.appendLine("vec4 $varName = textureSample($textureNode, , $uv);")
            return FVec4Node(varName)
        }
    }

}