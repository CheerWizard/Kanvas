package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.translation.Translation.op

private val register = Node.register { IntNode(it) }

class IntNode(
    override val expr: String,
) : Node()

fun Int.toNode() = IntNode(toString())

operator fun IntNode.plus(other: IntNode) = IntNode(Translation.op(this, "+", other))
operator fun Int.plus(other: IntNode) = IntNode(Translation.op(this, "+", other))
operator fun IntNode.plus(other: Int) = IntNode(Translation.op(this, "+", other))

operator fun IntNode.minus(other: IntNode) = IntNode(Translation.op(this, "-", other))
operator fun Int.minus(other: IntNode) = IntNode(Translation.op(this, "-", other))
operator fun IntNode.minus(other: Int) = IntNode(Translation.op(this, "-", other))

operator fun IntNode.times(other: IntNode) = IntNode(Translation.op(this, "*", other))
operator fun Int.times(other: IntNode) = IntNode(Translation.op(this, "*", other))
operator fun IntNode.times(other: Int) = IntNode(Translation.op(this, "*", other))

operator fun IntNode.div(other: IntNode) = IntNode(Translation.op(this, "/", other))
operator fun Int.div(other: IntNode) = IntNode(Translation.op(this, "/", other))
operator fun IntNode.div(other: Int) = IntNode(Translation.op(this, "/", other))

infix fun IntNode.`==`(other: IntNode) = IntNode(Translation.op(this, "==", other))
infix fun Int.`==`(other: IntNode) = IntNode(Translation.op(this, "==", other))
infix fun IntNode.`==`(other: Int) = IntNode(Translation.op(this, "==", other))

infix fun IntNode.`>`(other: IntNode) = IntNode(Translation.op(this, ">", other))
infix fun Int.`>`(other: IntNode) = IntNode(Translation.op(this, ">", other))
infix fun IntNode.`>`(other: Int) = IntNode(Translation.op(this, ">", other))

infix fun IntNode.`>=`(other: IntNode) = IntNode(Translation.op(this, ">=", other))
infix fun Int.`>=`(other: IntNode) = IntNode(Translation.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int) = IntNode(Translation.op(this, ">=", other))

infix fun IntNode.`<`(other: IntNode) = IntNode(Translation.op(this, "<", other))
infix fun Int.`<`(other: IntNode) = IntNode(Translation.op(this, "<", other))
infix fun IntNode.`<`(other: Int) = IntNode(Translation.op(this, "<", other))

infix fun IntNode.`<=`(other: IntNode) = IntNode(Translation.op(this, "<=", other))
infix fun Int.`<=`(other: IntNode) = IntNode(Translation.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int) = IntNode(Translation.op(this, "<=", other))