package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import kotlin.reflect.KClass

class ArrayUnsizedNode<T : Node>(
    override val expr: String,
    val clazz: KClass<T>,
    itemType: Type,
) : Node() {

    override val type: Type = Type.ArrayUnsized(itemType)

    context(scope: FunctionScope)
    operator fun set(i: Int, value: T) = scope.appendLine("$expr[$i] = $value;")

    context(scope: FunctionScope)
    operator fun get(i: Int): T = createNode(clazz, "$expr[$i]")

}

class ArraySizedNode<T : Node>(
    override val expr: String,
    val clazz: KClass<T>,
    itemType: Type,
    size: Int,
) : Node() {

    override val type: Type = Type.ArraySized(itemType, size)

    context(scope: FunctionScope)
    operator fun set(i: Int, value: T) = scope.appendLine("$expr[$i] = $value;")

    context(scope: FunctionScope)
    operator fun get(i: Int): T = createNode(clazz, "$expr[$i]")

}