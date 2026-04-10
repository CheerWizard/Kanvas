package com.cws.kanvas.shaderc.scopes.function

import com.cws.kanvas.shaderc.nodes.`=`
import com.cws.kanvas.shaderc.nodes.BooleanNode
import com.cws.kanvas.shaderc.nodes.Node
import com.cws.kanvas.shaderc.nodes.VarInt

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

fun s() {
    FunctionScope().apply {
        var i by VarInt(10)
        i `=` 10
        While() {

        }
    }
}