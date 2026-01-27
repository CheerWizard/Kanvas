package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op

class FloatNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Primitive.Float
}

operator fun FloatNode.plus(other: FloatNode) = FloatNode(Expr.op(this, "+", other))
operator fun Float.plus(other: FloatNode) = FloatNode(Expr.op(this, "+", other))
operator fun FloatNode.plus(other: Float) = FloatNode(Expr.op(this, "+", other))

operator fun FloatNode.minus(other: FloatNode) = FloatNode(Expr.op(this, "-", other))
operator fun Float.minus(other: FloatNode) = FloatNode(Expr.op(this, "-", other))
operator fun FloatNode.minus(other: Float) = FloatNode(Expr.op(this, "-", other))

operator fun FloatNode.times(other: FloatNode) = FloatNode(Expr.op(this, "*", other))
operator fun Float.times(other: FloatNode) = FloatNode(Expr.op(this, "*", other))
operator fun FloatNode.times(other: Float) = FloatNode(Expr.op(this, "*", other))

operator fun FloatNode.div(other: FloatNode) = FloatNode(Expr.op(this, "/", other))
operator fun Float.div(other: FloatNode) = FloatNode(Expr.op(this, "/", other))
operator fun FloatNode.div(other: Float) = FloatNode(Expr.op(this, "/", other))

infix fun FloatNode.`==`(other: FloatNode) = FloatNode(Expr.op(this, "==", other))
infix fun Float.`==`(other: FloatNode) = FloatNode(Expr.op(this, "==", other))
infix fun FloatNode.`==`(other: Float) = FloatNode(Expr.op(this, "==", other))

infix fun FloatNode.`>`(other: FloatNode) = FloatNode(Expr.op(this, ">", other))
infix fun Float.`>`(other: FloatNode) = FloatNode(Expr.op(this, ">", other))
infix fun FloatNode.`>`(other: Float) = FloatNode(Expr.op(this, ">", other))

infix fun FloatNode.`>=`(other: FloatNode) = FloatNode(Expr.op(this, ">=", other))
infix fun Float.`>=`(other: FloatNode) = FloatNode(Expr.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float) = FloatNode(Expr.op(this, ">=", other))

infix fun FloatNode.`<`(other: FloatNode) = FloatNode(Expr.op(this, "<", other))
infix fun Float.`<`(other: FloatNode) = FloatNode(Expr.op(this, "<", other))
infix fun FloatNode.`<`(other: Float) = FloatNode(Expr.op(this, "<", other))

infix fun FloatNode.`<=`(other: FloatNode) = FloatNode(Expr.op(this, "<=", other))
infix fun Float.`<=`(other: FloatNode) = FloatNode(Expr.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float) = FloatNode(Expr.op(this, "<=", other))