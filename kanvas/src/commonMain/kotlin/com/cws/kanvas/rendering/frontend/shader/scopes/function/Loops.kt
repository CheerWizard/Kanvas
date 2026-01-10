package com.cws.kanvas.rendering.frontend.shader.scopes.function

import com.cws.kanvas.rendering.frontend.shader.nodes.BooleanNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Node

inline fun FunctionScope.For(
    init: Node,
    condition: BooleanNode,
    step: Node,
    block: FunctionScope.() -> Unit,
) {
    val innerScope = FunctionScope()
    innerScope.block()
    appendLine("for ($init; $condition; $step) {")
    appendLine(innerScope.toString())
    appendLine("}")
}

inline fun FunctionScope.While(
    condition: BooleanNode,
    block: FunctionScope.() -> Unit,
) {
    val innerScope = FunctionScope()
    innerScope.block()
    appendLine("while ($condition) {")
    appendLine(innerScope.toString())
    appendLine("}")
}