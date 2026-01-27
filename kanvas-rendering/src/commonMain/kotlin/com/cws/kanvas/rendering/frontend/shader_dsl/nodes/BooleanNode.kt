package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op

class BooleanNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Primitive.Boolean
}

fun Boolean.toNode() = BooleanNode(toString())

infix fun BooleanNode.`==`(other: BooleanNode) = BooleanNode(Expr.op(this, "==", other))
infix fun BooleanNode.`==`(other: Boolean) = BooleanNode(Expr.op(this, "==", other))
infix fun Boolean.`==`(other: BooleanNode) = BooleanNode(Expr.op(this, "==", other))

infix fun BooleanNode.`&&`(other: BooleanNode) = BooleanNode(Expr.op(this, "&&", other))
infix fun BooleanNode.`&&`(other: Boolean) = BooleanNode(Expr.op(this, "&&", other))
infix fun Boolean.`&&`(other: BooleanNode) = BooleanNode(Expr.op(this, "&&", other))

infix fun BooleanNode.`||`(other: BooleanNode) = BooleanNode(Expr.op(this, "||", other))
infix fun BooleanNode.`||`(other: Boolean) = BooleanNode(Expr.op(this, "||", other))
infix fun Boolean.`||`(other: BooleanNode) = BooleanNode(Expr.op(this, "||", other))

val True = true.toNode()
val False = false.toNode()