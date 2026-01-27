package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

class Float3x3Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Float3x3

    context(scope: FunctionScope)
    operator fun get(i: Int) = Float3Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Float3Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Float3x3Node.plus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "+", other))
operator fun Float3x3Node.plus(other: FloatNode) = Float3x3Node(Expr.op(this, "+", other))
operator fun Float3x3Node.plus(other: Float) = Float3x3Node(Expr.op(this, "+", other))
operator fun FloatNode.plus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "+", other))
operator fun Float.plus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "+", other))

operator fun Float3x3Node.minus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "-", other))
operator fun Float3x3Node.minus(other: FloatNode) = Float3x3Node(Expr.op(this, "-", other))
operator fun Float3x3Node.minus(other: Float) = Float3x3Node(Expr.op(this, "-", other))
operator fun FloatNode.minus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "-", other))
operator fun Float.minus(other: Float3x3Node) = Float3x3Node(Expr.op(this, "-", other))

operator fun Float3x3Node.times(other: Float3x3Node) = Float3x3Node(Expr.op(this, "*", other))
operator fun Float3x3Node.times(other: FloatNode) = Float3x3Node(Expr.op(this, "*", other))
operator fun Float3x3Node.times(other: Float) = Float3x3Node(Expr.op(this, "*", other))
operator fun FloatNode.times(other: Float3x3Node) = Float3x3Node(Expr.op(this, "*", other))
operator fun Float.times(other: Float3x3Node) = Float3x3Node(Expr.op(this, "*", other))

operator fun Float3x3Node.div(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))
operator fun Float3x3Node.div(other: FloatNode) = Float3x3Node(Expr.op(this, "/", other))
operator fun Float3x3Node.div(other: Float) = Float3x3Node(Expr.op(this, "/", other))
operator fun FloatNode.div(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))
operator fun Float.div(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))

infix fun Float3x3Node.`==`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))
infix fun Float3x3Node.`==`(other: FloatNode) = Float3x3Node(Expr.op(this, "/", other))
infix fun Float3x3Node.`==`(other: Float) = Float3x3Node(Expr.op(this, "/", other))
infix fun FloatNode.`==`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))
infix fun Float.`==`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "/", other))

infix fun Float3x3Node.`>`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">", other))
infix fun Float3x3Node.`>`(other: FloatNode) = Float3x3Node(Expr.op(this, ">", other))
infix fun Float3x3Node.`>`(other: Float) = Float3x3Node(Expr.op(this, ">", other))
infix fun FloatNode.`>`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">", other))
infix fun Float.`>`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">", other))

infix fun Float3x3Node.`>=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">=", other))
infix fun Float3x3Node.`>=`(other: FloatNode) = Float3x3Node(Expr.op(this, ">=", other))
infix fun Float3x3Node.`>=`(other: Float) = Float3x3Node(Expr.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">=", other))
infix fun Float.`>=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, ">=", other))

infix fun Float3x3Node.`<`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<", other))
infix fun Float3x3Node.`<`(other: FloatNode) = Float3x3Node(Expr.op(this, "<", other))
infix fun Float3x3Node.`<`(other: Float) = Float3x3Node(Expr.op(this, "<", other))
infix fun FloatNode.`<`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<", other))
infix fun Float.`<`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<", other))

infix fun Float3x3Node.`<=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<=", other))
infix fun Float3x3Node.`<=`(other: FloatNode) = Float3x3Node(Expr.op(this, "<=", other))
infix fun Float3x3Node.`<=`(other: Float) = Float3x3Node(Expr.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<=", other))
infix fun Float.`<=`(other: Float3x3Node) = Float3x3Node(Expr.op(this, "<=", other))