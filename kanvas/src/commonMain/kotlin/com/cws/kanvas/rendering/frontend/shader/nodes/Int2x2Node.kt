package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Int2x2Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int2x2

    context(scope: FunctionScope)
    operator fun get(i: Int) = Int2Node("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: Int2Node) = scope.appendLine("$expr[$i] = $v;")
}

operator fun Int2x2Node.plus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "+", other))
operator fun Int2x2Node.plus(other: IntNode) = Int2x2Node(Expr.op(this, "+", other))
operator fun Int2x2Node.plus(other: Int) = Int2x2Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "+", other))

operator fun Int2x2Node.minus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "-", other))
operator fun Int2x2Node.minus(other: IntNode) = Int2x2Node(Expr.op(this, "-", other))
operator fun Int2x2Node.minus(other: Int) = Int2x2Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int2x2Node) = Int2x2Node(Expr.op(this, "-", other))

operator fun Int2x2Node.times(other: Int2x2Node) = Int2x2Node(Expr.op(this, "*", other))
operator fun Int2x2Node.times(other: IntNode) = Int2x2Node(Expr.op(this, "*", other))
operator fun Int2x2Node.times(other: Int) = Int2x2Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int2x2Node) = Int2x2Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int2x2Node) = Int2x2Node(Expr.op(this, "*", other))

operator fun Int2x2Node.div(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))
operator fun Int2x2Node.div(other: IntNode) = Int2x2Node(Expr.op(this, "/", other))
operator fun Int2x2Node.div(other: Int) = Int2x2Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))

infix fun Int2x2Node.`==`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))
infix fun Int2x2Node.`==`(other: IntNode) = Int2x2Node(Expr.op(this, "/", other))
infix fun Int2x2Node.`==`(other: Int) = Int2x2Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "/", other))

infix fun Int2x2Node.`>`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">", other))
infix fun Int2x2Node.`>`(other: IntNode) = Int2x2Node(Expr.op(this, ">", other))
infix fun Int2x2Node.`>`(other: Int) = Int2x2Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">", other))

infix fun Int2x2Node.`>=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">=", other))
infix fun Int2x2Node.`>=`(other: IntNode) = Int2x2Node(Expr.op(this, ">=", other))
infix fun Int2x2Node.`>=`(other: Int) = Int2x2Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, ">=", other))

infix fun Int2x2Node.`<`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<", other))
infix fun Int2x2Node.`<`(other: IntNode) = Int2x2Node(Expr.op(this, "<", other))
infix fun Int2x2Node.`<`(other: Int) = Int2x2Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<", other))

infix fun Int2x2Node.`<=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<=", other))
infix fun Int2x2Node.`<=`(other: IntNode) = Int2x2Node(Expr.op(this, "<=", other))
infix fun Int2x2Node.`<=`(other: Int) = Int2x2Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int2x2Node) = Int2x2Node(Expr.op(this, "<=", other))