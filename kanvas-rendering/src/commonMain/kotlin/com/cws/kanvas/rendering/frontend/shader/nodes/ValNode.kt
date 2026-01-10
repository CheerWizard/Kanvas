package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader.type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ValNode<T : Node>(
    override val expr: String,
    val value: T,
): Node() {
    override val type: Type = value.type
}

inline fun <reified T : Node> FunctionScope.Val(node: T): ReadOnlyProperty<Any?, ValNode<T>> {
    return object : ReadOnlyProperty<Any?, ValNode<T>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): ValNode<T> {
            val varName = property.name
            val type = type<T>()
            appendLine("$type $varName = $node;")
            return ValNode(varName, node)
        }
    }
}

fun FunctionScope.ValBool(value: Boolean) = Val(value.toNode())
fun FunctionScope.ValFloat(value: Float) = Val(value.toNode())
fun FunctionScope.ValInt(value: Int) = Val(value.toNode())
fun FunctionScope.ValUInt(value: UInt) = Val(value.toNode())