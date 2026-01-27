package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op

class UIntNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Primitive.UInt
}

operator fun UIntNode.plus(other: UIntNode) = UIntNode(Expr.op(this, "+", other))
operator fun UInt.plus(other: UIntNode) = UIntNode(Expr.op(this, "+", other))
operator fun UIntNode.plus(other: UInt) = UIntNode(Expr.op(this, "+", other))

operator fun UIntNode.minus(other: UIntNode) = UIntNode(Expr.op(this, "-", other))
operator fun UInt.minus(other: UIntNode) = UIntNode(Expr.op(this, "-", other))
operator fun UIntNode.minus(other: UInt) = UIntNode(Expr.op(this, "-", other))

operator fun UIntNode.times(other: UIntNode) = UIntNode(Expr.op(this, "*", other))
operator fun UInt.times(other: UIntNode) = UIntNode(Expr.op(this, "*", other))
operator fun UIntNode.times(other: UInt) = UIntNode(Expr.op(this, "*", other))

operator fun UIntNode.div(other: UIntNode) = UIntNode(Expr.op(this, "/", other))
operator fun UInt.div(other: UIntNode) = UIntNode(Expr.op(this, "/", other))
operator fun UIntNode.div(other: UInt) = UIntNode(Expr.op(this, "/", other))

infix fun UIntNode.`==`(other: UIntNode) = UIntNode(Expr.op(this, "==", other))
infix fun UInt.`==`(other: UIntNode) = UIntNode(Expr.op(this, "==", other))
infix fun UIntNode.`==`(other: UInt) = UIntNode(Expr.op(this, "==", other))

infix fun UIntNode.`>`(other: UIntNode) = UIntNode(Expr.op(this, ">", other))
infix fun UInt.`>`(other: UIntNode) = UIntNode(Expr.op(this, ">", other))
infix fun UIntNode.`>`(other: UInt) = UIntNode(Expr.op(this, ">", other))

infix fun UIntNode.`>=`(other: UIntNode) = UIntNode(Expr.op(this, ">=", other))
infix fun UInt.`>=`(other: UIntNode) = UIntNode(Expr.op(this, ">=", other))
infix fun UIntNode.`>=`(other: UInt) = UIntNode(Expr.op(this, ">=", other))

infix fun UIntNode.`<`(other: UIntNode) = UIntNode(Expr.op(this, "<", other))
infix fun UInt.`<`(other: UIntNode) = UIntNode(Expr.op(this, "<", other))
infix fun UIntNode.`<`(other: UInt) = UIntNode(Expr.op(this, "<", other))

infix fun UIntNode.`<=`(other: UIntNode) = UIntNode(Expr.op(this, "<=", other))
infix fun UInt.`<=`(other: UIntNode) = UIntNode(Expr.op(this, "<=", other))
infix fun UIntNode.`<=`(other: UInt) = UIntNode(Expr.op(this, "<=", other))