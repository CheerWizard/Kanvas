package com.cws.kanvas.rendering.frontend.shader.scopes.function

import com.cws.kanvas.rendering.frontend.shader.ScopeDsl
import com.cws.kanvas.rendering.frontend.shader.nodes.FunctionNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Node
import com.cws.kanvas.rendering.frontend.shader.scopes.Scope
import com.cws.kanvas.rendering.frontend.shader.scopes.shader.GraphicsScope
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
): ReadOnlyProperty<Any?, FunctionNode> {
    val scope = FunctionScope()
    val node = scope.block()
    return object : ReadOnlyProperty<Any?, FunctionNode> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): FunctionNode {
            val name = property.name
            val args = scope.argsToString()
            val result = "return $node;"
            appendLine("${node.type} $name($args) {\n$scope \n$result\n}")
            return FunctionNode(name, node.type)
        }
    }
}

inline fun <reified T : Node> FunctionScope.arg(): ReadOnlyProperty<Any?, T> {
    return object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val name = property.name
            val node = Node.create<T>(name)
            addArg(node)
            return node
        }
    }
}