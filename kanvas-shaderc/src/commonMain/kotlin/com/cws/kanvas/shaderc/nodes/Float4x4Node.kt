package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation.op

private val register = Node.register { Float4x4Node(it) }

class Float4x4Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Float4x4

    context(scope: FunctionScope)
    operator fun get(i: Int) = Float4Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Float4Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Float4x4Node.plus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "+", other))
operator fun Float4x4Node.plus(other: FloatNode) = Float4x4Node(Translation.op(this, "+", other))
operator fun Float4x4Node.plus(other: Float) = Float4x4Node(Translation.op(this, "+", other))
operator fun FloatNode.plus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "+", other))
operator fun Float.plus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "+", other))

operator fun Float4x4Node.minus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "-", other))
operator fun Float4x4Node.minus(other: FloatNode) = Float4x4Node(Translation.op(this, "-", other))
operator fun Float4x4Node.minus(other: Float) = Float4x4Node(Translation.op(this, "-", other))
operator fun FloatNode.minus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "-", other))
operator fun Float.minus(other: Float4x4Node) = Float4x4Node(Translation.op(this, "-", other))

operator fun Float4x4Node.times(other: Float4x4Node) = Float4x4Node(Translation.op(this, "*", other))
operator fun Float4x4Node.times(other: FloatNode) = Float4x4Node(Translation.op(this, "*", other))
operator fun Float4x4Node.times(other: Float) = Float4x4Node(Translation.op(this, "*", other))
operator fun FloatNode.times(other: Float4x4Node) = Float4x4Node(Translation.op(this, "*", other))
operator fun Float.times(other: Float4x4Node) = Float4x4Node(Translation.op(this, "*", other))

operator fun Float4x4Node.div(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))
operator fun Float4x4Node.div(other: FloatNode) = Float4x4Node(Translation.op(this, "/", other))
operator fun Float4x4Node.div(other: Float) = Float4x4Node(Translation.op(this, "/", other))
operator fun FloatNode.div(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))
operator fun Float.div(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))

infix fun Float4x4Node.`==`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))
infix fun Float4x4Node.`==`(other: FloatNode) = Float4x4Node(Translation.op(this, "/", other))
infix fun Float4x4Node.`==`(other: Float) = Float4x4Node(Translation.op(this, "/", other))
infix fun FloatNode.`==`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))
infix fun Float.`==`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "/", other))

infix fun Float4x4Node.`>`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">", other))
infix fun Float4x4Node.`>`(other: FloatNode) = Float4x4Node(Translation.op(this, ">", other))
infix fun Float4x4Node.`>`(other: Float) = Float4x4Node(Translation.op(this, ">", other))
infix fun FloatNode.`>`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">", other))
infix fun Float.`>`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">", other))

infix fun Float4x4Node.`>=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">=", other))
infix fun Float4x4Node.`>=`(other: FloatNode) = Float4x4Node(Translation.op(this, ">=", other))
infix fun Float4x4Node.`>=`(other: Float) = Float4x4Node(Translation.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">=", other))
infix fun Float.`>=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, ">=", other))

infix fun Float4x4Node.`<`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<", other))
infix fun Float4x4Node.`<`(other: FloatNode) = Float4x4Node(Translation.op(this, "<", other))
infix fun Float4x4Node.`<`(other: Float) = Float4x4Node(Translation.op(this, "<", other))
infix fun FloatNode.`<`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<", other))
infix fun Float.`<`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<", other))

infix fun Float4x4Node.`<=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<=", other))
infix fun Float4x4Node.`<=`(other: FloatNode) = Float4x4Node(Translation.op(this, "<=", other))
infix fun Float4x4Node.`<=`(other: Float) = Float4x4Node(Translation.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<=", other))
infix fun Float.`<=`(other: Float4x4Node) = Float4x4Node(Translation.op(this, "<=", other))