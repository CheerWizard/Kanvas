package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op

class IntNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Primitive.Int
}

operator fun IntNode.plus(other: IntNode) = IntNode(Expr.op(this, "+", other))
operator fun Int.plus(other: IntNode) = IntNode(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int) = IntNode(Expr.op(this, "+", other))

operator fun IntNode.minus(other: IntNode) = IntNode(Expr.op(this, "-", other))
operator fun Int.minus(other: IntNode) = IntNode(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int) = IntNode(Expr.op(this, "-", other))

operator fun IntNode.times(other: IntNode) = IntNode(Expr.op(this, "*", other))
operator fun Int.times(other: IntNode) = IntNode(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int) = IntNode(Expr.op(this, "*", other))

operator fun IntNode.div(other: IntNode) = IntNode(Expr.op(this, "/", other))
operator fun Int.div(other: IntNode) = IntNode(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int) = IntNode(Expr.op(this, "/", other))

infix fun IntNode.`==`(other: IntNode) = IntNode(Expr.op(this, "==", other))
infix fun Int.`==`(other: IntNode) = IntNode(Expr.op(this, "==", other))
infix fun IntNode.`==`(other: Int) = IntNode(Expr.op(this, "==", other))

infix fun IntNode.`>`(other: IntNode) = IntNode(Expr.op(this, ">", other))
infix fun Int.`>`(other: IntNode) = IntNode(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int) = IntNode(Expr.op(this, ">", other))

infix fun IntNode.`>=`(other: IntNode) = IntNode(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: IntNode) = IntNode(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int) = IntNode(Expr.op(this, ">=", other))

infix fun IntNode.`<`(other: IntNode) = IntNode(Expr.op(this, "<", other))
infix fun Int.`<`(other: IntNode) = IntNode(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int) = IntNode(Expr.op(this, "<", other))

infix fun IntNode.`<=`(other: IntNode) = IntNode(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: IntNode) = IntNode(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int) = IntNode(Expr.op(this, "<=", other))