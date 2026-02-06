package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader_dsl.type
import kotlin.properties.ReadOnlyProperty

class ValNode<T : Node>(
    override val expr: String,
    val value: T,
): Node() {
    override val type: Type = value.type
}

inline fun <reified T : Node> FunctionScope.Val(node: T): ReadOnlyProperty<Any?, ValNode<T>> {
    return ReadOnlyProperty<Any?, ValNode<T>> { thisRef, property ->
        val varName = property.name
        val type = type<T>()
        appendLine("$type $varName = $node;")
        ValNode(varName, node)
    }
}

fun FunctionScope.ValBool(value: Boolean) = Val(value.toNode())
fun FunctionScope.ValFloat(value: Float) = Val(value.toNode())
fun FunctionScope.ValInt(value: Int) = Val(value.toNode())
fun FunctionScope.ValUInt(value: UInt) = Val(value.toNode())