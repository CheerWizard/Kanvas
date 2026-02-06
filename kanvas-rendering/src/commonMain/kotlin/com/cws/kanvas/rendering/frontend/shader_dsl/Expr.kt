package com.cws.kanvas.rendering.frontend.shader_dsl

import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.Float2Node
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.Float3Node
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.FloatNode
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.SamplerNode
import com.cws.kanvas.rendering.frontend.shader_dsl.nodes.TextureNode

expect object Expr {
    fun <T> mod(value: T): String
    fun sample(samplerNode: SamplerNode, textureNode: TextureNode, uv: Float2Node): String
    fun texture(samplerNode: SamplerNode, uv: Float2Node): String
    fun texture(samplerNode: SamplerNode, uv: Float3Node): String
    fun texture(samplerNode: SamplerNode, uv: Float2Node, bias: FloatNode): String
    fun textureLod(samplerNode: SamplerNode, uv: Float2Node): String
    fun textureLod(samplerNode: SamplerNode, uv: Float2Node, lod: FloatNode): String
    fun textureGrad(samplerNode: SamplerNode, uv: Float2Node, dx: Float2Node, dy: Float2Node): String
}

// Basic
fun <L, R> Expr.op(left: L, op: String, right: R) = "($left $op $right)"
fun <L, R> Expr.assign(left: L, right: R) = "$left = $right;"

// Math
fun <L, R> Expr.dot(left: L, right: R) = "dot($left, $right)"
fun <L, R> Expr.cross(left: L, right: R) = "cross($left, $right)"
fun <T> Expr.abs(value: T) = "abs($value)"
fun <T> Expr.sign(value: T) = "sign($value)"
fun <T> Expr.floor(value: T) = "floor($value)"
fun <T> Expr.ceil(value: T) = "ceil($value)"
fun <T> Expr.round(value: T) = "round($value)"
fun <T> Expr.trunc(value: T) = "trunc($value)"
fun <T> Expr.fract(value: T) = "fract($value)"
fun <T> Expr.clamp(x: T, min: T, max: T) = "clamp($x, $min, $max)"
fun <T> Expr.mix(a: T, b: T, t: T) = "mix($a, $b, $t)"
fun <T> Expr.step(edge: T, x: T) = "step($edge, $x)"
fun <T> Expr.smoothstep(e0: T, e1: T, x: T) = "smoothstep($e0, $e1, $x)"
fun <T> Expr.sqrt(x: T) = "sqrt($x)"
fun <T> Expr.inversesqrt(x: T) = "inversesqrt($x)"
fun <T> Expr.pow(base: T, exp: T) = "pow($base, $exp)"
fun <T> Expr.exp(x: T) = "exp($x)"
fun <T> Expr.log(x: T) = "log($x)"
fun <T> Expr.exp2(x: T) = "exp2($x)"
fun <T> Expr.log2(x: T) = "log2($x)"
fun <T> Expr.sin(x: T) = "sin($x)"
fun <T> Expr.cos(x: T) = "cos($x)"
fun <T> Expr.tan(x: T) = "tan($x)"
fun <T> Expr.asin(x: T) = "asin($x)"
fun <T> Expr.acos(x: T) = "acos($x)"
fun <T> Expr.atan(x: T) = "atan($x)"
fun <T> Expr.atan2(y: T, x: T) = "atan2($y, $x)"
fun <T> Expr.sinh(x: T) = "sinh($x)"
fun <T> Expr.cosh(x: T) = "cosh($x)"
fun <T> Expr.tanh(x: T) = "tanh($x)"
fun <T> Expr.asinh(x: T) = "asinh($x)"
fun <T> Expr.acosh(x: T) = "acosh($x)"
fun <T> Expr.atanh(x: T) = "atanh($x)"
fun <T> Expr.length(v: T) = "length($v)"
fun <T> Expr.normalize(v: T) = "normalize($v)"
fun <T> Expr.distance(p1: T, p2: T) = "distance($p1, $p2)"
fun <T> Expr.reflect(v: T, n: T, eta: T) = "reflect($v, $n)"
fun <T> Expr.refract(v: T, n: T, eta: T) = "refract($v, $n, $eta)"
fun <T> Expr.faceforward(n: T, v: T, ref: T) = "faceforward($n, $v, $ref)"
fun <T> Expr.transpose(m: T) = "transpose($m)"
fun <T> Expr.determinant(m: T) = "determinant($m)"
fun <T> Expr.inverse(m: T) = "inverse($m)"