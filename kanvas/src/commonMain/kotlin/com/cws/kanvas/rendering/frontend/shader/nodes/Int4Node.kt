package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Int4Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int4

    context(scope: FunctionScope)
    operator fun get(i: Int) = IntNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: IntNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: IntNode get() = IntNode("$expr.x")
    context(scope: FunctionScope)
    val y: IntNode get() = IntNode("$expr.y")
    context(scope: FunctionScope)
    val z: IntNode get() = IntNode("$expr.z")
    context(scope: FunctionScope)
    val w: IntNode get() = IntNode("$expr.w")

    context(scope: FunctionScope)
    val r: IntNode get() = x
    context(scope: FunctionScope)
    val g: IntNode get() = y
    context(scope: FunctionScope)
    val b: IntNode get() = z
    context(scope: FunctionScope)
    val a: IntNode get() = w

    context(scope: FunctionScope)
    val xy: Int2Node get() = Int2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: Int2Node get() = Int2Node("$expr.rg")

    context(scope: FunctionScope)
    val xyz: Int3Node get() = Int3Node("$expr.xyz")
    context(scope: FunctionScope)
    val rgb: Int3Node get() = Int3Node("$expr.rgb")

    context(scope: FunctionScope)
    val wzyx: Int4Node get() = Int4Node("$expr.wzyx")
}

operator fun Int4Node.plus(other: Int4Node) = Int4Node(Expr.op(this, "+", other))
operator fun Int4Node.plus(other: IntNode) = Int4Node(Expr.op(this, "+", other))
operator fun Int4Node.plus(other: Int) = Int4Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int4Node) = Int4Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int4Node) = Int4Node(Expr.op(this, "+", other))

operator fun Int4Node.minus(other: Int4Node) = Int4Node(Expr.op(this, "-", other))
operator fun Int4Node.minus(other: IntNode) = Int4Node(Expr.op(this, "-", other))
operator fun Int4Node.minus(other: Int) = Int4Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int4Node) = Int4Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int4Node) = Int4Node(Expr.op(this, "-", other))

operator fun Int4Node.times(other: Int4Node) = Int4Node(Expr.op(this, "*", other))
operator fun Int4Node.times(other: IntNode) = Int4Node(Expr.op(this, "*", other))
operator fun Int4Node.times(other: Int) = Int4Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int4Node) = Int4Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int4Node) = Int4Node(Expr.op(this, "*", other))

operator fun Int4Node.div(other: Int4Node) = Int4Node(Expr.op(this, "/", other))
operator fun Int4Node.div(other: IntNode) = Int4Node(Expr.op(this, "/", other))
operator fun Int4Node.div(other: Int) = Int4Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int4Node) = Int4Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int4Node) = Int4Node(Expr.op(this, "/", other))

infix fun Int4Node.`==`(other: Int4Node) = Int4Node(Expr.op(this, "/", other))
infix fun Int4Node.`==`(other: IntNode) = Int4Node(Expr.op(this, "/", other))
infix fun Int4Node.`==`(other: Int) = Int4Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int4Node) = Int4Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int4Node) = Int4Node(Expr.op(this, "/", other))

infix fun Int4Node.`>`(other: Int4Node) = Int4Node(Expr.op(this, ">", other))
infix fun Int4Node.`>`(other: IntNode) = Int4Node(Expr.op(this, ">", other))
infix fun Int4Node.`>`(other: Int) = Int4Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int4Node) = Int4Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int4Node) = Int4Node(Expr.op(this, ">", other))

infix fun Int4Node.`>=`(other: Int4Node) = Int4Node(Expr.op(this, ">=", other))
infix fun Int4Node.`>=`(other: IntNode) = Int4Node(Expr.op(this, ">=", other))
infix fun Int4Node.`>=`(other: Int) = Int4Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int4Node) = Int4Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int4Node) = Int4Node(Expr.op(this, ">=", other))

infix fun Int4Node.`<`(other: Int4Node) = Int4Node(Expr.op(this, "<", other))
infix fun Int4Node.`<`(other: IntNode) = Int4Node(Expr.op(this, "<", other))
infix fun Int4Node.`<`(other: Int) = Int4Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int4Node) = Int4Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int4Node) = Int4Node(Expr.op(this, "<", other))

infix fun Int4Node.`<=`(other: Int4Node) = Int4Node(Expr.op(this, "<=", other))
infix fun Int4Node.`<=`(other: IntNode) = Int4Node(Expr.op(this, "<=", other))
infix fun Int4Node.`<=`(other: Int) = Int4Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int4Node) = Int4Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int4Node) = Int4Node(Expr.op(this, "<=", other))