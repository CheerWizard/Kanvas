package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

class UInt4Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.UInt4

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
    val w: UIntNode get() = UIntNode("$expr.w")

    context(scope: FunctionScope)
    val r: UIntNode get() = x
    context(scope: FunctionScope)
    val g: UIntNode get() = y
    context(scope: FunctionScope)
    val b: UIntNode get() = z
    context(scope: FunctionScope)
    val a: UIntNode get() = w

    context(scope: FunctionScope)
    val xy: UInt2Node get() = UInt2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: UInt2Node get() = UInt2Node("$expr.rg")

    context(scope: FunctionScope)
    val xyz: UInt3Node get() = UInt3Node("$expr.xyz")
    context(scope: FunctionScope)
    val rgb: UInt3Node get() = UInt3Node("$expr.rgb")

    context(scope: FunctionScope)
    val wzyx: UInt4Node get() = UInt4Node("$expr.wzyx")
}

operator fun UInt4Node.plus(other: UInt4Node) = UInt4Node(Expr.op(this, "+", other))
operator fun UInt4Node.plus(other: UIntNode) = UInt4Node(Expr.op(this, "+", other))
operator fun UInt4Node.plus(other: UInt) = UInt4Node(Expr.op(this, "+", other))
operator fun UIntNode.plus(other: UInt4Node) = UInt4Node(Expr.op(this, "+", other))
operator fun UInt.plus(other: UInt4Node) = UInt4Node(Expr.op(this, "+", other))

operator fun UInt4Node.minus(other: UInt4Node) = UInt4Node(Expr.op(this, "-", other))
operator fun UInt4Node.minus(other: UIntNode) = UInt4Node(Expr.op(this, "-", other))
operator fun UInt4Node.minus(other: UInt) = UInt4Node(Expr.op(this, "-", other))
operator fun UIntNode.minus(other: UInt4Node) = UInt4Node(Expr.op(this, "-", other))
operator fun UInt.minus(other: UInt4Node) = UInt4Node(Expr.op(this, "-", other))

operator fun UInt4Node.times(other: UInt4Node) = UInt4Node(Expr.op(this, "*", other))
operator fun UInt4Node.times(other: UIntNode) = UInt4Node(Expr.op(this, "*", other))
operator fun UInt4Node.times(other: UInt) = UInt4Node(Expr.op(this, "*", other))
operator fun UIntNode.times(other: UInt4Node) = UInt4Node(Expr.op(this, "*", other))
operator fun UInt.times(other: UInt4Node) = UInt4Node(Expr.op(this, "*", other))

operator fun UInt4Node.div(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))
operator fun UInt4Node.div(other: UIntNode) = UInt4Node(Expr.op(this, "/", other))
operator fun UInt4Node.div(other: UInt) = UInt4Node(Expr.op(this, "/", other))
operator fun UIntNode.div(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))
operator fun UInt.div(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))

infix fun UInt4Node.`==`(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))
infix fun UInt4Node.`==`(other: UIntNode) = UInt4Node(Expr.op(this, "/", other))
infix fun UInt4Node.`==`(other: UInt) = UInt4Node(Expr.op(this, "/", other))
infix fun UIntNode.`==`(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))
infix fun UInt.`==`(other: UInt4Node) = UInt4Node(Expr.op(this, "/", other))

infix fun UInt4Node.`>`(other: UInt4Node) = UInt4Node(Expr.op(this, ">", other))
infix fun UInt4Node.`>`(other: UIntNode) = UInt4Node(Expr.op(this, ">", other))
infix fun UInt4Node.`>`(other: UInt) = UInt4Node(Expr.op(this, ">", other))
infix fun UIntNode.`>`(other: UInt4Node) = UInt4Node(Expr.op(this, ">", other))
infix fun UInt.`>`(other: UInt4Node) = UInt4Node(Expr.op(this, ">", other))

infix fun UInt4Node.`>=`(other: UInt4Node) = UInt4Node(Expr.op(this, ">=", other))
infix fun UInt4Node.`>=`(other: UIntNode) = UInt4Node(Expr.op(this, ">=", other))
infix fun UInt4Node.`>=`(other: UInt) = UInt4Node(Expr.op(this, ">=", other))
infix fun UIntNode.`>=`(other: UInt4Node) = UInt4Node(Expr.op(this, ">=", other))
infix fun UInt.`>=`(other: UInt4Node) = UInt4Node(Expr.op(this, ">=", other))

infix fun UInt4Node.`<`(other: UInt4Node) = UInt4Node(Expr.op(this, "<", other))
infix fun UInt4Node.`<`(other: UIntNode) = UInt4Node(Expr.op(this, "<", other))
infix fun UInt4Node.`<`(other: UInt) = UInt4Node(Expr.op(this, "<", other))
infix fun UIntNode.`<`(other: UInt4Node) = UInt4Node(Expr.op(this, "<", other))
infix fun UInt.`<`(other: UInt4Node) = UInt4Node(Expr.op(this, "<", other))

infix fun UInt4Node.`<=`(other: UInt4Node) = UInt4Node(Expr.op(this, "<=", other))
infix fun UInt4Node.`<=`(other: UIntNode) = UInt4Node(Expr.op(this, "<=", other))
infix fun UInt4Node.`<=`(other: UInt) = UInt4Node(Expr.op(this, "<=", other))
infix fun UIntNode.`<=`(other: UInt4Node) = UInt4Node(Expr.op(this, "<=", other))
infix fun UInt.`<=`(other: UInt4Node) = UInt4Node(Expr.op(this, "<=", other))