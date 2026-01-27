package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

class Int3x3Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int3x3

    context(scope: FunctionScope)
    operator fun get(i: Int) = Int3Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Int3Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Int3x3Node.plus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "+", other))
operator fun Int3x3Node.plus(other: IntNode) = Int3x3Node(Expr.op(this, "+", other))
operator fun Int3x3Node.plus(other: Int) = Int3x3Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "+", other))

operator fun Int3x3Node.minus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "-", other))
operator fun Int3x3Node.minus(other: IntNode) = Int3x3Node(Expr.op(this, "-", other))
operator fun Int3x3Node.minus(other: Int) = Int3x3Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int3x3Node) = Int3x3Node(Expr.op(this, "-", other))

operator fun Int3x3Node.times(other: Int3x3Node) = Int3x3Node(Expr.op(this, "*", other))
operator fun Int3x3Node.times(other: IntNode) = Int3x3Node(Expr.op(this, "*", other))
operator fun Int3x3Node.times(other: Int) = Int3x3Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int3x3Node) = Int3x3Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int3x3Node) = Int3x3Node(Expr.op(this, "*", other))

operator fun Int3x3Node.div(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))
operator fun Int3x3Node.div(other: IntNode) = Int3x3Node(Expr.op(this, "/", other))
operator fun Int3x3Node.div(other: Int) = Int3x3Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))

infix fun Int3x3Node.`==`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))
infix fun Int3x3Node.`==`(other: IntNode) = Int3x3Node(Expr.op(this, "/", other))
infix fun Int3x3Node.`==`(other: Int) = Int3x3Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "/", other))

infix fun Int3x3Node.`>`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">", other))
infix fun Int3x3Node.`>`(other: IntNode) = Int3x3Node(Expr.op(this, ">", other))
infix fun Int3x3Node.`>`(other: Int) = Int3x3Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">", other))

infix fun Int3x3Node.`>=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">=", other))
infix fun Int3x3Node.`>=`(other: IntNode) = Int3x3Node(Expr.op(this, ">=", other))
infix fun Int3x3Node.`>=`(other: Int) = Int3x3Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, ">=", other))

infix fun Int3x3Node.`<`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<", other))
infix fun Int3x3Node.`<`(other: IntNode) = Int3x3Node(Expr.op(this, "<", other))
infix fun Int3x3Node.`<`(other: Int) = Int3x3Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<", other))

infix fun Int3x3Node.`<=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<=", other))
infix fun Int3x3Node.`<=`(other: IntNode) = Int3x3Node(Expr.op(this, "<=", other))
infix fun Int3x3Node.`<=`(other: Int) = Int3x3Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int3x3Node) = Int3x3Node(Expr.op(this, "<=", other))