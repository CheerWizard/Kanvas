package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation
import kotlin.properties.ReadOnlyProperty

class ValNode<T : Node>(
    override val expr: String,
    val value: T,
): Node()

inline fun <reified T : Node> FunctionScope.Val(node: T): ReadOnlyProperty<Any?, ValNode<T>> {
    return ReadOnlyProperty<Any?, ValNode<T>> { thisRef, property ->
        val varName = property.name
        appendLine(Translation.declare(Translation.type<T>(), varName, node.toString()))
        ValNode(varName, node)
    }
}

fun FunctionScope.ValBool(value: Boolean) = Val(value.toNode())
fun FunctionScope.ValFloat(value: Float) = Val(value.toNode())
fun FunctionScope.ValInt(value: Int) = Val(value.toNode())