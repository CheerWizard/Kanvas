package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.translation.Translation.op

private val register = Node.register { BooleanNode(it) }

class BooleanNode(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Boolean
}

fun Boolean.toNode() = BooleanNode(toString())

infix fun BooleanNode.`==`(other: BooleanNode) = BooleanNode(Translation.op(this, "==", other))
infix fun BooleanNode.`==`(other: Boolean) = BooleanNode(Translation.op(this, "==", other.toNode()))
infix fun Boolean.`==`(other: BooleanNode) = BooleanNode(Translation.op(this.toNode(), "==", other))

infix fun BooleanNode.`&&`(other: BooleanNode) = BooleanNode(Translation.op(this, "&&", other))
infix fun BooleanNode.`&&`(other: Boolean) = BooleanNode(Translation.op(this, "&&", other.toNode()))
infix fun Boolean.`&&`(other: BooleanNode) = BooleanNode(Translation.op(this.toNode(), "&&", other))

infix fun BooleanNode.`||`(other: BooleanNode) = BooleanNode(Translation.op(this, "||", other))
infix fun BooleanNode.`||`(other: Boolean) = BooleanNode(Translation.op(this, "||", other.toNode()))
infix fun Boolean.`||`(other: BooleanNode) = BooleanNode(Translation.op(this.toNode(), "||", other))

val True = true.toNode()
val False = false.toNode()