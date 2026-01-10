package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Int3Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Int3

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
    val r: IntNode get() = x
    context(scope: FunctionScope)
    val g: IntNode get() = y
    context(scope: FunctionScope)
    val b: IntNode get() = z

    context(scope: FunctionScope)
    val xy: Int2Node get() = Int2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: Int2Node get() = Int2Node("$expr.rg")

    context(scope: FunctionScope)
    val zyx: Int3Node get() = Int3Node("$expr.zyx")
    context(scope: FunctionScope)
    val rgb: Int3Node get() = Int3Node("$expr.rgb")
}

operator fun Int3Node.plus(other: Int3Node) = Int3Node(Expr.op(this, "+", other))
operator fun Int3Node.plus(other: IntNode) = Int3Node(Expr.op(this, "+", other))
operator fun Int3Node.plus(other: Int) = Int3Node(Expr.op(this, "+", other))
operator fun IntNode.plus(other: Int3Node) = Int3Node(Expr.op(this, "+", other))
operator fun Int.plus(other: Int3Node) = Int3Node(Expr.op(this, "+", other))

operator fun Int3Node.minus(other: Int3Node) = Int3Node(Expr.op(this, "-", other))
operator fun Int3Node.minus(other: IntNode) = Int3Node(Expr.op(this, "-", other))
operator fun Int3Node.minus(other: Int) = Int3Node(Expr.op(this, "-", other))
operator fun IntNode.minus(other: Int3Node) = Int3Node(Expr.op(this, "-", other))
operator fun Int.minus(other: Int3Node) = Int3Node(Expr.op(this, "-", other))

operator fun Int3Node.times(other: Int3Node) = Int3Node(Expr.op(this, "*", other))
operator fun Int3Node.times(other: IntNode) = Int3Node(Expr.op(this, "*", other))
operator fun Int3Node.times(other: Int) = Int3Node(Expr.op(this, "*", other))
operator fun IntNode.times(other: Int3Node) = Int3Node(Expr.op(this, "*", other))
operator fun Int.times(other: Int3Node) = Int3Node(Expr.op(this, "*", other))

operator fun Int3Node.div(other: Int3Node) = Int3Node(Expr.op(this, "/", other))
operator fun Int3Node.div(other: IntNode) = Int3Node(Expr.op(this, "/", other))
operator fun Int3Node.div(other: Int) = Int3Node(Expr.op(this, "/", other))
operator fun IntNode.div(other: Int3Node) = Int3Node(Expr.op(this, "/", other))
operator fun Int.div(other: Int3Node) = Int3Node(Expr.op(this, "/", other))

infix fun Int3Node.`==`(other: Int3Node) = Int3Node(Expr.op(this, "/", other))
infix fun Int3Node.`==`(other: IntNode) = Int3Node(Expr.op(this, "/", other))
infix fun Int3Node.`==`(other: Int) = Int3Node(Expr.op(this, "/", other))
infix fun IntNode.`==`(other: Int3Node) = Int3Node(Expr.op(this, "/", other))
infix fun Int.`==`(other: Int3Node) = Int3Node(Expr.op(this, "/", other))

infix fun Int3Node.`>`(other: Int3Node) = Int3Node(Expr.op(this, ">", other))
infix fun Int3Node.`>`(other: IntNode) = Int3Node(Expr.op(this, ">", other))
infix fun Int3Node.`>`(other: Int) = Int3Node(Expr.op(this, ">", other))
infix fun IntNode.`>`(other: Int3Node) = Int3Node(Expr.op(this, ">", other))
infix fun Int.`>`(other: Int3Node) = Int3Node(Expr.op(this, ">", other))

infix fun Int3Node.`>=`(other: Int3Node) = Int3Node(Expr.op(this, ">=", other))
infix fun Int3Node.`>=`(other: IntNode) = Int3Node(Expr.op(this, ">=", other))
infix fun Int3Node.`>=`(other: Int) = Int3Node(Expr.op(this, ">=", other))
infix fun IntNode.`>=`(other: Int3Node) = Int3Node(Expr.op(this, ">=", other))
infix fun Int.`>=`(other: Int3Node) = Int3Node(Expr.op(this, ">=", other))

infix fun Int3Node.`<`(other: Int3Node) = Int3Node(Expr.op(this, "<", other))
infix fun Int3Node.`<`(other: IntNode) = Int3Node(Expr.op(this, "<", other))
infix fun Int3Node.`<`(other: Int) = Int3Node(Expr.op(this, "<", other))
infix fun IntNode.`<`(other: Int3Node) = Int3Node(Expr.op(this, "<", other))
infix fun Int.`<`(other: Int3Node) = Int3Node(Expr.op(this, "<", other))

infix fun Int3Node.`<=`(other: Int3Node) = Int3Node(Expr.op(this, "<=", other))
infix fun Int3Node.`<=`(other: IntNode) = Int3Node(Expr.op(this, "<=", other))
infix fun Int3Node.`<=`(other: Int) = Int3Node(Expr.op(this, "<=", other))
infix fun IntNode.`<=`(other: Int3Node) = Int3Node(Expr.op(this, "<=", other))
infix fun Int.`<=`(other: Int3Node) = Int3Node(Expr.op(this, "<=", other))