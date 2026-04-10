package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import kotlin.reflect.KClass

open class FunctionNode<T : Node>(
    val clazz: KClass<T>,
    override val expr: String,
) : Node() {
    context(scope: FunctionScope)
    operator fun invoke(vararg args: Any): T {
        scope.appendLine("$expr(${args.joinToString(",")})")
        return createNode(clazz, expr)
    }
}