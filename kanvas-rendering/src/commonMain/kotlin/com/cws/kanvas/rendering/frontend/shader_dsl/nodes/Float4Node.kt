package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.op
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope

class Float4Node(
    override val expr: String,
) : Node() {
    override val type: Type = Type.Math.Float4

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
    val w: FloatNode get() = FloatNode("$expr.w")

    context(scope: FunctionScope)
    val r: FloatNode get() = x
    context(scope: FunctionScope)
    val g: FloatNode get() = y
    context(scope: FunctionScope)
    val b: FloatNode get() = z
    context(scope: FunctionScope)
    val a: FloatNode get() = w

    context(scope: FunctionScope)
    val xy: Float2Node get() = Float2Node("$expr.xy")
    context(scope: FunctionScope)
    val rg: Float2Node get() = Float2Node("$expr.rg")

    context(scope: FunctionScope)
    val xyz: Float3Node get() = Float3Node("$expr.xyz")
    context(scope: FunctionScope)
    val rgb: Float3Node get() = Float3Node("$expr.rgb")

    context(scope: FunctionScope)
    val wzyx: Float4Node get() = Float4Node("$expr.wzyx")
}

operator fun Float4Node.plus(other: Float4Node) = Float4Node(Expr.op(this, "+", other))
operator fun Float4Node.plus(other: FloatNode) = Float4Node(Expr.op(this, "+", other))
operator fun Float4Node.plus(other: Float) = Float4Node(Expr.op(this, "+", other))
operator fun FloatNode.plus(other: Float4Node) = Float4Node(Expr.op(this, "+", other))
operator fun Float.plus(other: Float4Node) = Float4Node(Expr.op(this, "+", other))

operator fun Float4Node.minus(other: Float4Node) = Float4Node(Expr.op(this, "-", other))
operator fun Float4Node.minus(other: FloatNode) = Float4Node(Expr.op(this, "-", other))
operator fun Float4Node.minus(other: Float) = Float4Node(Expr.op(this, "-", other))
operator fun FloatNode.minus(other: Float4Node) = Float4Node(Expr.op(this, "-", other))
operator fun Float.minus(other: Float4Node) = Float4Node(Expr.op(this, "-", other))

operator fun Float4Node.times(other: Float4Node) = Float4Node(Expr.op(this, "*", other))
operator fun Float4Node.times(other: FloatNode) = Float4Node(Expr.op(this, "*", other))
operator fun Float4Node.times(other: Float) = Float4Node(Expr.op(this, "*", other))
operator fun FloatNode.times(other: Float4Node) = Float4Node(Expr.op(this, "*", other))
operator fun Float.times(other: Float4Node) = Float4Node(Expr.op(this, "*", other))

operator fun Float4Node.div(other: Float4Node) = Float4Node(Expr.op(this, "/", other))
operator fun Float4Node.div(other: FloatNode) = Float4Node(Expr.op(this, "/", other))
operator fun Float4Node.div(other: Float) = Float4Node(Expr.op(this, "/", other))
operator fun FloatNode.div(other: Float4Node) = Float4Node(Expr.op(this, "/", other))
operator fun Float.div(other: Float4Node) = Float4Node(Expr.op(this, "/", other))

infix fun Float4Node.`==`(other: Float4Node) = Float4Node(Expr.op(this, "/", other))
infix fun Float4Node.`==`(other: FloatNode) = Float4Node(Expr.op(this, "/", other))
infix fun Float4Node.`==`(other: Float) = Float4Node(Expr.op(this, "/", other))
infix fun FloatNode.`==`(other: Float4Node) = Float4Node(Expr.op(this, "/", other))
infix fun Float.`==`(other: Float4Node) = Float4Node(Expr.op(this, "/", other))

infix fun Float4Node.`>`(other: Float4Node) = Float4Node(Expr.op(this, ">", other))
infix fun Float4Node.`>`(other: FloatNode) = Float4Node(Expr.op(this, ">", other))
infix fun Float4Node.`>`(other: Float) = Float4Node(Expr.op(this, ">", other))
infix fun FloatNode.`>`(other: Float4Node) = Float4Node(Expr.op(this, ">", other))
infix fun Float.`>`(other: Float4Node) = Float4Node(Expr.op(this, ">", other))

infix fun Float4Node.`>=`(other: Float4Node) = Float4Node(Expr.op(this, ">=", other))
infix fun Float4Node.`>=`(other: FloatNode) = Float4Node(Expr.op(this, ">=", other))
infix fun Float4Node.`>=`(other: Float) = Float4Node(Expr.op(this, ">=", other))
infix fun FloatNode.`>=`(other: Float4Node) = Float4Node(Expr.op(this, ">=", other))
infix fun Float.`>=`(other: Float4Node) = Float4Node(Expr.op(this, ">=", other))

infix fun Float4Node.`<`(other: Float4Node) = Float4Node(Expr.op(this, "<", other))
infix fun Float4Node.`<`(other: FloatNode) = Float4Node(Expr.op(this, "<", other))
infix fun Float4Node.`<`(other: Float) = Float4Node(Expr.op(this, "<", other))
infix fun FloatNode.`<`(other: Float4Node) = Float4Node(Expr.op(this, "<", other))
infix fun Float.`<`(other: Float4Node) = Float4Node(Expr.op(this, "<", other))

infix fun Float4Node.`<=`(other: Float4Node) = Float4Node(Expr.op(this, "<=", other))
infix fun Float4Node.`<=`(other: FloatNode) = Float4Node(Expr.op(this, "<=", other))
infix fun Float4Node.`<=`(other: Float) = Float4Node(Expr.op(this, "<=", other))
infix fun FloatNode.`<=`(other: Float4Node) = Float4Node(Expr.op(this, "<=", other))
infix fun Float.`<=`(other: Float4Node) = Float4Node(Expr.op(this, "<=", other))