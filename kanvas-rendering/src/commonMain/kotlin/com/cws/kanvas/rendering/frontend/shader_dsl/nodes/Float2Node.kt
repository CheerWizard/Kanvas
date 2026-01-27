package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

class Float2Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Float2

    context(scope: FunctionScope)
    operator fun get(i: Int) = FloatNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: FloatNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: FloatNode get() = FloatNode("$expr.x")
    context(scope: FunctionScope)
    val y: FloatNode get() = FloatNode("$expr.y")

    context(scope: FunctionScope)
    val r: FloatNode get() = x
    context(scope: FunctionScope)
    val g: FloatNode get() = y

    context(scope: FunctionScope)
    val yx: Float2Node get() = Float2Node("$expr.yx")
}

operator fun Float2Node.plus(other: Float2Node) = Float2Node(Expr.op(this, "+", other))
operator fun Float2Node.plus(other: FloatNode) = Float2Node(Expr.op(this, "+", other))
operator fun Float2Node.plus(other: Float) = Float2Node(Expr.op(this, "+", other))
operator fun FloatNode.plus(other: Float2Node) = Float2Node(Expr.op(this, "+", other))
operator fun Float.plus(other: Float2Node) = Float2Node(Expr.op(this, "+", other))

operator fun Float2Node.minus(other: Float2Node) = Float2Node(Expr.op(this, "-", other))
operator fun Float2Node.minus(other: FloatNode) = Float2Node(Expr.op(this, "-", other))
operator fun Float2Node.minus(other: Float) = Float2Node(Expr.op(this, "-", other))
operator fun FloatNode.minus(other: Float2Node) = Float2Node(Expr.op(this, "-", other))
operator fun Float.minus(other: Float2Node) = Float2Node(Expr.op(this, "-", other))

operator fun Float2Node.times(other: Float2Node) = Float2Node(Expr.op(this, "*", other))
operator fun Float2Node.times(other: FloatNode) = Float2Node(Expr.op(this, "*", other))
operator fun Float2Node.times(other: Float) = Float2Node(Expr.op(this, "*", other))
operator fun FloatNode.times(other: Float2Node) = Float2Node(Expr.op(this, "*", other))
operator fun Float.times(other: Float2Node) = Float2Node(Expr.op(this, "*", other))

operator fun Float2Node.div(other: Float2Node) = Float2Node(Expr.op(this, "/", other))
operator fun Float2Node.div(other: FloatNode) = Float2Node(Expr.op(this, "/", other))
operator fun Float2Node.div(other: Float) = Float2Node(Expr.op(this, "/", other))
operator fun FloatNode.div(other: Float2Node) = Float2Node(Expr.op(this, "/", other))
operator fun Float.div(other: Float2Node) = Float2Node(Expr.op(this, "/", other))

infix fun Float2Node.`==`(other: Float2Node) = Float2Node(Expr.op(this, "/", other))
infix fun Float2Node.`==`(other: FloatNode) = Float2Node(Expr.op(this, "/", other))
infix fun Float2Node.`==`(other: Float) = Float2Node(Expr.op(this, "/", other))
infix fun FloatNode.`==`(other: Float2Node) = Float2Node(Expr.op(this, "/", other))
infix fun Float.`==`(other: Float2Node) = Float2Node(Expr.op(this, "/", other))

infix fun Float2Node.`>`(other: Float2Node) = Float2Node(Expr.op(this, ">", other))
infix fun Float2Node.`>`(other: FloatNode) = Float2Node(Expr.op(this, ">", other))
infix fun Float2Node.`>`(other: Float) = Float2Node(Expr.op(this, ">", other))
infix fun FloatNode.`>`(other: Float2Node) = Float2Node(Expr.op(this, ">", other))
infix fun Float.`>`(other: Float2Node) = Float2Node(Expr.op(this, ">", other))

infix fun Float2Node.`>=`(other: Float2Node) = Float2Node(Expr.op(this, ">=", other))
infix fun Float2Node.`>=`(other: FloatNode) = Float2Node(Expr.op(this, ">=", other))
infix fun Float2Node.`>=`(other: Float) = Float2Node(Expr.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float2Node) = Float2Node(Expr.op(this, ">=", other))
infix fun Float.`>=`(other: Float2Node) = Float2Node(Expr.op(this, ">=", other))

infix fun Float2Node.`<`(other: Float2Node) = Float2Node(Expr.op(this, "<", other))
infix fun Float2Node.`<`(other: FloatNode) = Float2Node(Expr.op(this, "<", other))
infix fun Float2Node.`<`(other: Float) = Float2Node(Expr.op(this, "<", other))
infix fun FloatNode.`<`(other: Float2Node) = Float2Node(Expr.op(this, "<", other))
infix fun Float.`<`(other: Float2Node) = Float2Node(Expr.op(this, "<", other))

infix fun Float2Node.`<=`(other: Float2Node) = Float2Node(Expr.op(this, "<=", other))
infix fun Float2Node.`<=`(other: FloatNode) = Float2Node(Expr.op(this, "<=", other))
infix fun Float2Node.`<=`(other: Float) = Float2Node(Expr.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float2Node) = Float2Node(Expr.op(this, "<=", other))
infix fun Float.`<=`(other: Float2Node) = Float2Node(Expr.op(this, "<=", other))