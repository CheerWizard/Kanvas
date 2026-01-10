package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Int4x4Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int4x4

    context(scope: FunctionScope)
    operator fun get(i: Int) = Int4Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Int4Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Int4x4Node.plus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "+", other))
operator fun Int4x4Node.plus(other: IntNode) = Int4x4Node(Expr.op(this, "+", other))
operator fun Int4x4Node.plus(other: Int) = Int4x4Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "+", other))

operator fun Int4x4Node.minus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "-", other))
operator fun Int4x4Node.minus(other: IntNode) = Int4x4Node(Expr.op(this, "-", other))
operator fun Int4x4Node.minus(other: Int) = Int4x4Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int4x4Node) = Int4x4Node(Expr.op(this, "-", other))

operator fun Int4x4Node.times(other: Int4x4Node) = Int4x4Node(Expr.op(this, "*", other))
operator fun Int4x4Node.times(other: IntNode) = Int4x4Node(Expr.op(this, "*", other))
operator fun Int4x4Node.times(other: Int) = Int4x4Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int4x4Node) = Int4x4Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int4x4Node) = Int4x4Node(Expr.op(this, "*", other))

operator fun Int4x4Node.div(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))
operator fun Int4x4Node.div(other: IntNode) = Int4x4Node(Expr.op(this, "/", other))
operator fun Int4x4Node.div(other: Int) = Int4x4Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))

infix fun Int4x4Node.`==`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))
infix fun Int4x4Node.`==`(other: IntNode) = Int4x4Node(Expr.op(this, "/", other))
infix fun Int4x4Node.`==`(other: Int) = Int4x4Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "/", other))

infix fun Int4x4Node.`>`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">", other))
infix fun Int4x4Node.`>`(other: IntNode) = Int4x4Node(Expr.op(this, ">", other))
infix fun Int4x4Node.`>`(other: Int) = Int4x4Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">", other))

infix fun Int4x4Node.`>=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">=", other))
infix fun Int4x4Node.`>=`(other: IntNode) = Int4x4Node(Expr.op(this, ">=", other))
infix fun Int4x4Node.`>=`(other: Int) = Int4x4Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, ">=", other))

infix fun Int4x4Node.`<`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<", other))
infix fun Int4x4Node.`<`(other: IntNode) = Int4x4Node(Expr.op(this, "<", other))
infix fun Int4x4Node.`<`(other: Int) = Int4x4Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<", other))

infix fun Int4x4Node.`<=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<=", other))
infix fun Int4x4Node.`<=`(other: IntNode) = Int4x4Node(Expr.op(this, "<=", other))
infix fun Int4x4Node.`<=`(other: Int) = Int4x4Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int4x4Node) = Int4x4Node(Expr.op(this, "<=", other))