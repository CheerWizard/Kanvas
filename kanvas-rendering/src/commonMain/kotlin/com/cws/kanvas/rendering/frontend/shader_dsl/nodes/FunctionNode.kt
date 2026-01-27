package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.node
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

open class FunctionNode(
    override val expr: String,
    returnType: Type,
) : Node() {
    override val type: Type = returnType

    context(scope: FunctionScope)
    inline operator fun <reified T : Node> invoke(vararg args: Any): T {
        scope.appendLine("$expr(${args.joinToString { it.toNode().toString() }})")
        return node(type, expr)
    }
}