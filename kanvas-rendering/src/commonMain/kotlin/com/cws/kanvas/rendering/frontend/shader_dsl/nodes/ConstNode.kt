package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.shader.GraphicsScope
import com.cws.kanvas.rendering.frontend.shader_dsl.type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ConstNode<T : Node>(
    override val expr: String,
    val value: T,
): Node() {
    override val type: Type = value.type
}

inline fun <reified T : Node> GraphicsScope.Const(node: T): ReadOnlyProperty<Any?, ConstNode<T>> {
    return object : ReadOnlyProperty<Any?, ConstNode<T>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): ConstNode<T> {
            val varName = property.name
            val type = type<T>()
            appendLine("const $type $varName = $node;")
            return ConstNode(varName, node)
        }
    }
}

fun GraphicsScope.ConstBool(value: Boolean) = Const(value.toNode())
fun GraphicsScope.ConstFloat(value: Float) = Const(value.toNode())
fun GraphicsScope.ConstInt(value: Int) = Const(value.toNode())
fun GraphicsScope.ConstUInt(value: UInt) = Const(value.toNode())