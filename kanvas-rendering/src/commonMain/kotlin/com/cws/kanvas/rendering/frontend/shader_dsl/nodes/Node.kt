package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type

abstract class Node {
    abstract val expr: String
    abstract val type: Type

    final override fun toString() = expr

    companion object {
        val FACTORY = mutableMapOf<String, (expr: String) -> Node>()

        inline fun <reified T : Node> create(expr: String): T {
            val key = T::class.simpleName
            return if (FACTORY.contains(key)) {
                FACTORY[key]?.invoke(expr) as T
            } else {
                error("Node $key is not registered, please register it to Node factory")
            }
        }

        inline fun <reified T : Node> register(noinline factory: (expr: String) -> T) {
            FACTORY[T::class.simpleName.toString()] = factory
        }
    }
}

fun Any.toNode(): Node {
    return when (this) {
        is Boolean -> BooleanNode(toString())
        is Float -> FloatNode(toDouble().toString())
        is UInt -> FloatNode(toString().removeSuffix("u"))
        is Int -> IntNode(toString())
        is Node -> this
        else -> error("Unsupported Node type - $this")
    }
}