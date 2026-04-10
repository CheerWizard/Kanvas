package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private val register = Node.register { SamplerNode(it) }

class SamplerNode(
    override val expr: String,
) : Node() {

    context(scope: FunctionScope)
    fun sample(textureNode: TextureNode, uv: Float2Node) = object : ReadOnlyProperty<Any?, Float4Node> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): Float4Node {
            val varName = property.name
            scope.appendLine(Translation.declare(
                Translation.type<Float4Node>(),
                varName,
                Translation.sample(this@SamplerNode, textureNode, uv)
            ))
            return Float4Node(varName)
        }
    }

}