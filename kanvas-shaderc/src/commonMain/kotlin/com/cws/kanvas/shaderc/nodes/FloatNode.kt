package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.translation.Translation.op

private val register = Node.register { FloatNode(it) }

class FloatNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Float
}

fun Float.toNode() = FloatNode(toDouble().toString())
fun Double.toNode() = FloatNode(toString())

operator fun FloatNode.plus(other: FloatNode) = FloatNode(Translation.op(this, "+", other))
operator fun Float.plus(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "+", other))
operator fun FloatNode.plus(other: Float) = FloatNode(Translation.op(this, "+", other.toNode()))

operator fun FloatNode.minus(other: FloatNode) = FloatNode(Translation.op(this, "-", other))
operator fun Float.minus(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "-", other))
operator fun FloatNode.minus(other: Float) = FloatNode(Translation.op(this, "-", other.toNode()))

operator fun FloatNode.times(other: FloatNode) = FloatNode(Translation.op(this, "*", other))
operator fun Float.times(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "*", other))
operator fun FloatNode.times(other: Float) = FloatNode(Translation.op(this, "*", other.toNode()))

operator fun FloatNode.div(other: FloatNode) = FloatNode(Translation.op(this, "/", other))
operator fun Float.div(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "/", other))
operator fun FloatNode.div(other: Float) = FloatNode(Translation.op(this, "/", other.toNode()))

infix fun FloatNode.`==`(other: FloatNode) = FloatNode(Translation.op(this, "==", other))
infix fun Float.`==`(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "==", other))
infix fun FloatNode.`==`(other: Float) = FloatNode(Translation.op(this, "==", other.toNode()))

infix fun FloatNode.`)`(other: FloatNode) = FloatNode(Translation.op(this, ">", other))
infix fun Float.`>`(other: FloatNode) = FloatNode(Translation.op(this.toNode(), ">", other))
infix fun FloatNode.`>`(other: Float) = FloatNode(Translation.op(this, ">", other.toNode()))

infix fun FloatNode.`>=`(other: FloatNode) = FloatNode(Translation.op(this, ">=", other))
infix fun Float.`>=`(other: FloatNode) = FloatNode(Translation.op(this.toNode(), ">=", other))
infix fun FloatNode.`>=`(other: Float) = FloatNode(Translation.op(this, ">=", other.toNode()))

infix fun FloatNode.`<`(other: FloatNode) = FloatNode(Translation.op(this, "<", other))
infix fun Float.`<`(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "<", other))
infix fun FloatNode.`<`(other: Float) = FloatNode(Translation.op(this, "<", other.toNode()))

infix fun FloatNode.`<=`(other: FloatNode) = FloatNode(Translation.op(this, "<=", other))
infix fun Float.`<=`(other: FloatNode) = FloatNode(Translation.op(this.toNode(), "<=", other))
infix fun FloatNode.`<=`(other: Float) = FloatNode(Translation.op(this, "<=", other.toNode()))