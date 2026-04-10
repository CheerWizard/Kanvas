package com.cws.kanvas.shaderc.scopes.function

import com.cws.kanvas.shaderc.ScopeDsl
import com.cws.kanvas.shaderc.nodes.FunctionNode
import com.cws.kanvas.shaderc.nodes.Node
import com.cws.kanvas.shaderc.scopes.Scope
import com.cws.kanvas.shaderc.scopes.shader.GraphicsScope
import com.cws.kanvas.shaderc.translation.Translation
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@ScopeDsl
open class FunctionScope : Scope() {
    private val args = mutableListOf<Node>()
    fun addArg(arg: Node) = args.add(arg)
    fun argsToString() = args.joinToString(", ") { it.toString() }.removeSuffix(", ")
}

inline fun <reified T : Node> GraphicsScope.function(
    block: FunctionScope.() -> T,
): ReadOnlyProperty<Any?, FunctionNode<T>> {
    val scope = FunctionScope()
    val node = scope.block()
    return object : ReadOnlyProperty<Any?, FunctionNode<T>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): FunctionNode<T> {
            val name = property.name
            val args = scope.argsToString()
            val result = "return $node;"a
            appendLine(Translation.function(node.type, name, args, scope, result))
            return FunctionNode(T::class, name)
        }
    }
}

inline fun <reified T : Node> FunctionScope.arg(): ReadOnlyProperty<Any?, T> {
    return object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val name = property.name
            val node = Node.createNode<T>(name)
            addArg(node)
            return node
        }
    }
}