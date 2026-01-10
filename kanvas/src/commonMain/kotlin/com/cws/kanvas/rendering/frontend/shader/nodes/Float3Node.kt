package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.op
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope

class Float3Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Float3

    context(scope: FunctionScope)
    operator fun get(i: Int) = FloatNode("$expr[$i]")

    context(scope: FunctionScope)
    operator fun set(i: Int, v: FloatNode) = scope.appendLine("$expr[$i] = $v;")

    context(scope: FunctionScope)
    val x: FloatNode get() = FloatNode("$expr.x")
    context(scope: FunctionScope)
    val y: FloatNode get() = FloatNode("$expr.y")
    context(scope: FunctionScope)
    val z: FloatNode get() = FloatNode("$expr.z")

    context(scope: FunctionScope)
    val r: FloatNode get() = x
    context(scope: FunctionScope)
    val g: FloatNode get() = y
    context(scope: FunctionScope)
    val b: FloatNode get() = z

    context(scope: FunctionScope)
    val xy: Float2Node get() = Float2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: Float2Node get() = Float2Node("$expr.rg")

    context(scope: FunctionScope)
    val xyz: Float3Node get() = Float3Node("$expr.xyz")
    context(scope: FunctionScope)
    val zyx: Float3Node get() = Float3Node("$expr.zyx")
    context(scope: FunctionScope)
    val rgb: Float3Node get() = Float3Node("$expr.rgb")
}

operator fun Float3Node.plus(other: Float3Node) = Float3Node(Expr.op(this, "+", other))
operator fun Float3Node.plus(other: FloatNode) = Float3Node(Expr.op(this, "+", other))
operator fun Float3Node.plus(other: Float) = Float3Node(Expr.op(this, "+", other))
operator fun FloatNode.plus(other: Float3Node) = Float3Node(Expr.op(this, "+", other))
operator fun Float.plus(other: Float3Node) = Float3Node(Expr.op(this, "+", other))

operator fun Float3Node.minus(other: Float3Node) = Float3Node(Expr.op(this, "-", other))
operator fun Float3Node.minus(other: FloatNode) = Float3Node(Expr.op(this, "-", other))
operator fun Float3Node.minus(other: Float) = Float3Node(Expr.op(this, "-", other))
operator fun FloatNode.minus(other: Float3Node) = Float3Node(Expr.op(this, "-", other))
operator fun Float.minus(other: Float3Node) = Float3Node(Expr.op(this, "-", other))

operator fun Float3Node.times(other: Float3Node) = Float3Node(Expr.op(this, "*", other))
operator fun Float3Node.times(other: FloatNode) = Float3Node(Expr.op(this, "*", other))
operator fun Float3Node.times(other: Float) = Float3Node(Expr.op(this, "*", other))
operator fun FloatNode.times(other: Float3Node) = Float3Node(Expr.op(this, "*", other))
operator fun Float.times(other: Float3Node) = Float3Node(Expr.op(this, "*", other))

operator fun Float3Node.div(other: Float3Node) = Float3Node(Expr.op(this, "/", other))
operator fun Float3Node.div(other: FloatNode) = Float3Node(Expr.op(this, "/", other))
operator fun Float3Node.div(other: Float) = Float3Node(Expr.op(this, "/", other))
operator fun FloatNode.div(other: Float3Node) = Float3Node(Expr.op(this, "/", other))
operator fun Float.div(other: Float3Node) = Float3Node(Expr.op(this, "/", other))

infix fun Float3Node.`==`(other: Float3Node) = Float3Node(Expr.op(this, "/", other))
infix fun Float3Node.`==`(other: FloatNode) = Float3Node(Expr.op(this, "/", other))
infix fun Float3Node.`==`(other: Float) = Float3Node(Expr.op(this, "/", other))
infix fun FloatNode.`==`(other: Float3Node) = Float3Node(Expr.op(this, "/", other))
infix fun Float.`==`(other: Float3Node) = Float3Node(Expr.op(this, "/", other))

infix fun Float3Node.`>`(other: Float3Node) = Float3Node(Expr.op(this, ">", other))
infix fun Float3Node.`>`(other: FloatNode) = Float3Node(Expr.op(this, ">", other))
infix fun Float3Node.`>`(other: Float) = Float3Node(Expr.op(this, ">", other))
infix fun FloatNode.`>`(other: Float3Node) = Float3Node(Expr.op(this, ">", other))
infix fun Float.`>`(other: Float3Node) = Float3Node(Expr.op(this, ">", other))

infix fun Float3Node.`>=`(other: Float3Node) = Float3Node(Expr.op(this, ">=", other))
infix fun Float3Node.`>=`(other: FloatNode) = Float3Node(Expr.op(this, ">=", other))
infix fun Float3Node.`>=`(other: Float) = Float3Node(Expr.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float3Node) = Float3Node(Expr.op(this, ">=", other))
infix fun Float.`>=`(other: Float3Node) = Float3Node(Expr.op(this, ">=", other))

infix fun Float3Node.`<`(other: Float3Node) = Float3Node(Expr.op(this, "<", other))
infix fun Float3Node.`<`(other: FloatNode) = Float3Node(Expr.op(this, "<", other))
infix fun Float3Node.`<`(other: Float) = Float3Node(Expr.op(this, "<", other))
infix fun FloatNode.`<`(other: Float3Node) = Float3Node(Expr.op(this, "<", other))
infix fun Float.`<`(other: Float3Node) = Float3Node(Expr.op(this, "<", other))

infix fun Float3Node.`<=`(other: Float3Node) = Float3Node(Expr.op(this, "<=", other))
infix fun Float3Node.`<=`(other: FloatNode) = Float3Node(Expr.op(this, "<=", other))
infix fun Float3Node.`<=`(other: Float) = Float3Node(Expr.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float3Node) = Float3Node(Expr.op(this, "<=", other))
infix fun Float.`<=`(other: Float3Node) = Float3Node(Expr.op(this, "<=", other))