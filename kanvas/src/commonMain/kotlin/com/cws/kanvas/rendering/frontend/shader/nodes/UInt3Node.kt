package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class UInt3Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.UInt3

    context(scope: FunctionScope)
    operator fun get(i: Int) = UIntNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: UIntNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: UIntNode get() = UIntNode("$expr.x")
    context(scope: FunctionScope)
    val y: UIntNode get() = UIntNode("$expr.y")
    context(scope: FunctionScope)
    val z: UIntNode get() = UIntNode("$expr.z")

    context(scope: FunctionScope)
    val r: UIntNode get() = x
    context(scope: FunctionScope)
    val g: UIntNode get() = y
    context(scope: FunctionScope)
    val b: UIntNode get() = z

    context(scope: FunctionScope)
    val xy: UInt2Node get() = UInt2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: UInt2Node get() = UInt2Node("$expr.rg")

    context(scope: FunctionScope)
    val zyx: UInt3Node get() = UInt3Node("$expr.zyx")
    context(scope: FunctionScope)
    val rgb: UInt3Node get() = UInt3Node("$expr.rgb")
}

operator fun UInt3Node.plus(other: UInt3Node) = UInt3Node(Expr.op(this, "+", other))
operator fun UInt3Node.plus(other: UIntNode) = UInt3Node(Expr.op(this, "+", other))
operator fun UInt3Node.plus(other: UInt) = UInt3Node(Expr.op(this, "+", other))
operator fun UIntNode.plus(other: UInt3Node) = UInt3Node(Expr.op(this, "+", other))
operator fun UInt.plus(other: UInt3Node) = UInt3Node(Expr.op(this, "+", other))

operator fun UInt3Node.minus(other: UInt3Node) = UInt3Node(Expr.op(this, "-", other))
operator fun UInt3Node.minus(other: UIntNode) = UInt3Node(Expr.op(this, "-", other))
operator fun UInt3Node.minus(other: UInt) = UInt3Node(Expr.op(this, "-", other))
operator fun UIntNode.minus(other: UInt3Node) = UInt3Node(Expr.op(this, "-", other))
operator fun UInt.minus(other: UInt3Node) = UInt3Node(Expr.op(this, "-", other))

operator fun UInt3Node.times(other: UInt3Node) = UInt3Node(Expr.op(this, "*", other))
operator fun UInt3Node.times(other: UIntNode) = UInt3Node(Expr.op(this, "*", other))
operator fun UInt3Node.times(other: UInt) = UInt3Node(Expr.op(this, "*", other))
operator fun UIntNode.times(other: UInt3Node) = UInt3Node(Expr.op(this, "*", other))
operator fun UInt.times(other: UInt3Node) = UInt3Node(Expr.op(this, "*", other))

operator fun UInt3Node.div(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))
operator fun UInt3Node.div(other: UIntNode) = UInt3Node(Expr.op(this, "/", other))
operator fun UInt3Node.div(other: UInt) = UInt3Node(Expr.op(this, "/", other))
operator fun UIntNode.div(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))
operator fun UInt.div(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))

infix fun UInt3Node.`==`(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))
infix fun UInt3Node.`==`(other: UIntNode) = UInt3Node(Expr.op(this, "/", other))
infix fun UInt3Node.`==`(other: UInt) = UInt3Node(Expr.op(this, "/", other))
infix fun UIntNode.`==`(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))
infix fun UInt.`==`(other: UInt3Node) = UInt3Node(Expr.op(this, "/", other))

infix fun UInt3Node.`>`(other: UInt3Node) = UInt3Node(Expr.op(this, ">", other))
infix fun UInt3Node.`>`(other: UIntNode) = UInt3Node(Expr.op(this, ">", other))
infix fun UInt3Node.`>`(other: UInt) = UInt3Node(Expr.op(this, ">", other))
infix fun UIntNode.`>`(other: UInt3Node) = UInt3Node(Expr.op(this, ">", other))
infix fun UInt.`>`(other: UInt3Node) = UInt3Node(Expr.op(this, ">", other))

infix fun UInt3Node.`>=`(other: UInt3Node) = UInt3Node(Expr.op(this, ">=", other))
infix fun UInt3Node.`>=`(other: UIntNode) = UInt3Node(Expr.op(this, ">=", other))
infix fun UInt3Node.`>=`(other: UInt) = UInt3Node(Expr.op(this, ">=", other))
infix fun UIntNode.`>=`(other: UInt3Node) = UInt3Node(Expr.op(this, ">=", other))
infix fun UInt.`>=`(other: UInt3Node) = UInt3Node(Expr.op(this, ">=", other))

infix fun UInt3Node.`<`(other: UInt3Node) = UInt3Node(Expr.op(this, "<", other))
infix fun UInt3Node.`<`(other: UIntNode) = UInt3Node(Expr.op(this, "<", other))
infix fun UInt3Node.`<`(other: UInt) = UInt3Node(Expr.op(this, "<", other))
infix fun UIntNode.`<`(other: UInt3Node) = UInt3Node(Expr.op(this, "<", other))
infix fun UInt.`<`(other: UInt3Node) = UInt3Node(Expr.op(this, "<", other))

infix fun UInt3Node.`<=`(other: UInt3Node) = UInt3Node(Expr.op(this, "<=", other))
infix fun UInt3Node.`<=`(other: UIntNode) = UInt3Node(Expr.op(this, "<=", other))
infix fun UInt3Node.`<=`(other: UInt) = UInt3Node(Expr.op(this, "<=", other))
infix fun UIntNode.`<=`(other: UInt3Node) = UInt3Node(Expr.op(this, "<=", other))
infix fun UInt.`<=`(other: UInt3Node) = UInt3Node(Expr.op(this, "<=", other))