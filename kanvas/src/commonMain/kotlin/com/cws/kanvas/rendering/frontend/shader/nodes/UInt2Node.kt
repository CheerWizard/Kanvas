package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class UInt2Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.UInt2

    context(scope: FunctionScope)
    operator fun get(i: Int) = UIntNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: UIntNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: UIntNode get() = UIntNode("$expr.x")
    context(scope: FunctionScope)
    val y: UIntNode get() = UIntNode("$expr.y")

    context(scope: FunctionScope)
    val r: UIntNode get() = x
    context(scope: FunctionScope)
    val g: UIntNode get() = y

    context(scope: FunctionScope)
    val yx: UInt2Node get() = UInt2Node("$expr.yx")
}

operator fun UInt2Node.plus(other: UInt2Node) = UInt2Node(Expr.op(this, "+", other))
operator fun UInt2Node.plus(other: UIntNode) = UInt2Node(Expr.op(this, "+", other))
operator fun UInt2Node.plus(other: UInt) = UInt2Node(Expr.op(this, "+", other))
operator fun UIntNode.plus(other: UInt2Node) = UInt2Node(Expr.op(this, "+", other))
operator fun UInt.plus(other: UInt2Node) = UInt2Node(Expr.op(this, "+", other))

operator fun UInt2Node.minus(other: UInt2Node) = UInt2Node(Expr.op(this, "-", other))
operator fun UInt2Node.minus(other: UIntNode) = UInt2Node(Expr.op(this, "-", other))
operator fun UInt2Node.minus(other: UInt) = UInt2Node(Expr.op(this, "-", other))
operator fun UIntNode.minus(other: UInt2Node) = UInt2Node(Expr.op(this, "-", other))
operator fun UInt.minus(other: UInt2Node) = UInt2Node(Expr.op(this, "-", other))

operator fun UInt2Node.times(other: UInt2Node) = UInt2Node(Expr.op(this, "*", other))
operator fun UInt2Node.times(other: UIntNode) = UInt2Node(Expr.op(this, "*", other))
operator fun UInt2Node.times(other: UInt) = UInt2Node(Expr.op(this, "*", other))
operator fun UIntNode.times(other: UInt2Node) = UInt2Node(Expr.op(this, "*", other))
operator fun UInt.times(other: UInt2Node) = UInt2Node(Expr.op(this, "*", other))

operator fun UInt2Node.div(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))
operator fun UInt2Node.div(other: UIntNode) = UInt2Node(Expr.op(this, "/", other))
operator fun UInt2Node.div(other: UInt) = UInt2Node(Expr.op(this, "/", other))
operator fun UIntNode.div(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))
operator fun UInt.div(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))

infix fun UInt2Node.`==`(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))
infix fun UInt2Node.`==`(other: UIntNode) = UInt2Node(Expr.op(this, "/", other))
infix fun UInt2Node.`==`(other: UInt) = UInt2Node(Expr.op(this, "/", other))
infix fun UIntNode.`==`(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))
infix fun UInt.`==`(other: UInt2Node) = UInt2Node(Expr.op(this, "/", other))

infix fun UInt2Node.`>`(other: UInt2Node) = UInt2Node(Expr.op(this, ">", other))
infix fun UInt2Node.`>`(other: UIntNode) = UInt2Node(Expr.op(this, ">", other))
infix fun UInt2Node.`>`(other: UInt) = UInt2Node(Expr.op(this, ">", other))
infix fun UIntNode.`>`(other: UInt2Node) = UInt2Node(Expr.op(this, ">", other))
infix fun UInt.`>`(other: UInt2Node) = UInt2Node(Expr.op(this, ">", other))

infix fun UInt2Node.`>=`(other: UInt2Node) = UInt2Node(Expr.op(this, ">=", other))
infix fun UInt2Node.`>=`(other: UIntNode) = UInt2Node(Expr.op(this, ">=", other))
infix fun UInt2Node.`>=`(other: UInt) = UInt2Node(Expr.op(this, ">=", other))
infix fun UIntNode.`>=`(other: UInt2Node) = UInt2Node(Expr.op(this, ">=", other))
infix fun UInt.`>=`(other: UInt2Node) = UInt2Node(Expr.op(this, ">=", other))

infix fun UInt2Node.`<`(other: UInt2Node) = UInt2Node(Expr.op(this, "<", other))
infix fun UInt2Node.`<`(other: UIntNode) = UInt2Node(Expr.op(this, "<", other))
infix fun UInt2Node.`<`(other: UInt) = UInt2Node(Expr.op(this, "<", other))
infix fun UIntNode.`<`(other: UInt2Node) = UInt2Node(Expr.op(this, "<", other))
infix fun UInt.`<`(other: UInt2Node) = UInt2Node(Expr.op(this, "<", other))

infix fun UInt2Node.`<=`(other: UInt2Node) = UInt2Node(Expr.op(this, "<=", other))
infix fun UInt2Node.`<=`(other: UIntNode) = UInt2Node(Expr.op(this, "<=", other))
infix fun UInt2Node.`<=`(other: UInt) = UInt2Node(Expr.op(this, "<=", other))
infix fun UIntNode.`<=`(other: UInt2Node) = UInt2Node(Expr.op(this, "<=", other))
infix fun UInt.`<=`(other: UInt2Node) = UInt2Node(Expr.op(this, "<=", other))