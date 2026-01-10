package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Int2Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int2

    context(scope: FunctionScope)
    operator fun get(i: Int) = IntNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: IntNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: IntNode get() = IntNode("$expr.x")
    context(scope: FunctionScope)
    val y: IntNode get() = IntNode("$expr.y")

    context(scope: FunctionScope)
    val r: IntNode get() = x
    context(scope: FunctionScope)
    val g: IntNode get() = y

    context(scope: FunctionScope)
    val yx: Int2Node get() = Int2Node("$expr.yx")
}

operator fun Int2Node.plus(other: Int2Node) = Int2Node(Expr.op(this, "+", other))
operator fun Int2Node.plus(other: IntNode) = Int2Node(Expr.op(this, "+", other))
operator fun Int2Node.plus(other: Int) = Int2Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int2Node) = Int2Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int2Node) = Int2Node(Expr.op(this, "+", other))

operator fun Int2Node.minus(other: Int2Node) = Int2Node(Expr.op(this, "-", other))
operator fun Int2Node.minus(other: IntNode) = Int2Node(Expr.op(this, "-", other))
operator fun Int2Node.minus(other: Int) = Int2Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int2Node) = Int2Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int2Node) = Int2Node(Expr.op(this, "-", other))

operator fun Int2Node.times(other: Int2Node) = Int2Node(Expr.op(this, "*", other))
operator fun Int2Node.times(other: IntNode) = Int2Node(Expr.op(this, "*", other))
operator fun Int2Node.times(other: Int) = Int2Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int2Node) = Int2Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int2Node) = Int2Node(Expr.op(this, "*", other))

operator fun Int2Node.div(other: Int2Node) = Int2Node(Expr.op(this, "/", other))
operator fun Int2Node.div(other: IntNode) = Int2Node(Expr.op(this, "/", other))
operator fun Int2Node.div(other: Int) = Int2Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int2Node) = Int2Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int2Node) = Int2Node(Expr.op(this, "/", other))

infix fun Int2Node.`==`(other: Int2Node) = Int2Node(Expr.op(this, "/", other))
infix fun Int2Node.`==`(other: IntNode) = Int2Node(Expr.op(this, "/", other))
infix fun Int2Node.`==`(other: Int) = Int2Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int2Node) = Int2Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int2Node) = Int2Node(Expr.op(this, "/", other))

infix fun Int2Node.`>`(other: Int2Node) = Int2Node(Expr.op(this, ">", other))
infix fun Int2Node.`>`(other: IntNode) = Int2Node(Expr.op(this, ">", other))
infix fun Int2Node.`>`(other: Int) = Int2Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int2Node) = Int2Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int2Node) = Int2Node(Expr.op(this, ">", other))

infix fun Int2Node.`>=`(other: Int2Node) = Int2Node(Expr.op(this, ">=", other))
infix fun Int2Node.`>=`(other: IntNode) = Int2Node(Expr.op(this, ">=", other))
infix fun Int2Node.`>=`(other: Int) = Int2Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int2Node) = Int2Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int2Node) = Int2Node(Expr.op(this, ">=", other))

infix fun Int2Node.`<`(other: Int2Node) = Int2Node(Expr.op(this, "<", other))
infix fun Int2Node.`<`(other: IntNode) = Int2Node(Expr.op(this, "<", other))
infix fun Int2Node.`<`(other: Int) = Int2Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int2Node) = Int2Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int2Node) = Int2Node(Expr.op(this, "<", other))

infix fun Int2Node.`<=`(other: Int2Node) = Int2Node(Expr.op(this, "<=", other))
infix fun Int2Node.`<=`(other: IntNode) = Int2Node(Expr.op(this, "<=", other))
infix fun Int2Node.`<=`(other: Int) = Int2Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int2Node) = Int2Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int2Node) = Int2Node(Expr.op(this, "<=", other))