package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import kotlin.reflect.KClass

abstract class Node {
    abstract val expr: String
    abstract val type: Type

    final override fun toString() = expr

    companion object {
        val FACTORY = mutableMapOf<String, (expr: String) -> Node>()
        val TYPES = mutableMapOf<String, Type>()

        inline fun <reified T : Node> createNode(expr: String): T {
            return createNode(T::class, expr)
        }

        fun <T : Node> createNode(clazz: KClass<T>, expr: String): T {
            val key = clazz.simpleName
            val factory = FACTORY[key]
            return factory?.invoke(expr) as? T ?: error("Node $key is not registered, please use register<T : Node> {}")
        }

        inline fun <reified T : Node> register(noinline factory: (expr: String) -> T) {
            val nodeType = T::class.simpleName.toString()
            val type = factory("")
            FACTORY[] = factory
            FACTORY[T::class.simpleName.toString()] = factory
        }
    }
}
