package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation.op

private val register = Node.register { Float2x2Node(it) }

class Float2x2Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Float2x2

    context(scope: FunctionScope)
    operator fun get(i: Int) = Float2Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Float2Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Float2x2Node.plus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "+", other))
operator fun Float2x2Node.plus(other: FloatNode) = Float2x2Node(Translation.op(this, "+", other))
operator fun Float2x2Node.plus(other: Float) = Float2x2Node(Translation.op(this, "+", other))
operator fun FloatNode.plus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "+", other))
operator fun Float.plus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "+", other))

operator fun Float2x2Node.minus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "-", other))
operator fun Float2x2Node.minus(other: FloatNode) = Float2x2Node(Translation.op(this, "-", other))
operator fun Float2x2Node.minus(other: Float) = Float2x2Node(Translation.op(this, "-", other))
operator fun FloatNode.minus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "-", other))
operator fun Float.minus(other: Float2x2Node) = Float2x2Node(Translation.op(this, "-", other))

operator fun Float2x2Node.times(other: Float2x2Node) = Float2x2Node(Translation.op(this, "*", other))
operator fun Float2x2Node.times(other: FloatNode) = Float2x2Node(Translation.op(this, "*", other))
operator fun Float2x2Node.times(other: Float) = Float2x2Node(Translation.op(this, "*", other))
operator fun FloatNode.times(other: Float2x2Node) = Float2x2Node(Translation.op(this, "*", other))
operator fun Float.times(other: Float2x2Node) = Float2x2Node(Translation.op(this, "*", other))

operator fun Float2x2Node.div(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))
operator fun Float2x2Node.div(other: FloatNode) = Float2x2Node(Translation.op(this, "/", other))
operator fun Float2x2Node.div(other: Float) = Float2x2Node(Translation.op(this, "/", other))
operator fun FloatNode.div(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))
operator fun Float.div(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))

infix fun Float2x2Node.`==`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))
infix fun Float2x2Node.`==`(other: FloatNode) = Float2x2Node(Translation.op(this, "/", other))
infix fun Float2x2Node.`==`(other: Float) = Float2x2Node(Translation.op(this, "/", other))
infix fun FloatNode.`==`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))
infix fun Float.`==`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "/", other))

infix fun Float2x2Node.`>`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">", other))
infix fun Float2x2Node.`>`(other: FloatNode) = Float2x2Node(Translation.op(this, ">", other))
infix fun Float2x2Node.`>`(other: Float) = Float2x2Node(Translation.op(this, ">", other))
infix fun FloatNode.`>`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">", other))
infix fun Float.`>`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">", other))

infix fun Float2x2Node.`>=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">=", other))
infix fun Float2x2Node.`>=`(other: FloatNode) = Float2x2Node(Translation.op(this, ">=", other))
infix fun Float2x2Node.`>=`(other: Float) = Float2x2Node(Translation.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">=", other))
infix fun Float.`>=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, ">=", other))

infix fun Float2x2Node.`<`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<", other))
infix fun Float2x2Node.`<`(other: FloatNode) = Float2x2Node(Translation.op(this, "<", other))
infix fun Float2x2Node.`<`(other: Float) = Float2x2Node(Translation.op(this, "<", other))
infix fun FloatNode.`<`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<", other))
infix fun Float.`<`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<", other))

infix fun Float2x2Node.`<=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<=", other))
infix fun Float2x2Node.`<=`(other: FloatNode) = Float2x2Node(Translation.op(this, "<=", other))
infix fun Float2x2Node.`<=`(other: Float) = Float2x2Node(Translation.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<=", other))
infix fun Float.`<=`(other: Float2x2Node) = Float2x2Node(Translation.op(this, "<=", other))