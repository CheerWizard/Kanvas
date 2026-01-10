package com.cws.kanvas.rendering.frontend.shader.scopes.function

import com.cws.kanvas.rendering.frontend.shader.nodes.BooleanNode

inline fun FunctionScope.If(
    condition: BooleanNode,
    block: FunctionScope.() -> Unit,
) {
    val innerScope = FunctionScope()
    innerScope.block()
    appendLine("if ($condition) {")
    appendLine(innerScope.toString())
    appendLine("}")
}

inline fun FunctionScope.ElseIf(
    condition: BooleanNode,
    block: FunctionScope.() -> Unit,
) {
    val innerScope = FunctionScope()
    innerScope.block()
    appendLine("else if ($condition) {")
    appendLine(innerScope.toString())
    appendLine("}")
}

inline fun FunctionScope.Else(
    block: FunctionScope.() -> Unit,
) {
    val innerScope = FunctionScope()
    innerScope.block()
    appendLine("else {")
    appendLine(innerScope.toString())
    appendLine("}")
}