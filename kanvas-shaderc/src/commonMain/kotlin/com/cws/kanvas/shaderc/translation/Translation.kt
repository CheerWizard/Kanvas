package com.cws.kanvas.shaderc.translation

import com.cws.kanvas.shaderc.Type
import com.cws.kanvas.shaderc.nodes.Float2Node
import com.cws.kanvas.shaderc.nodes.Float3Node
import com.cws.kanvas.shaderc.nodes.FloatNode
import com.cws.kanvas.shaderc.nodes.Node
import com.cws.kanvas.shaderc.nodes.SamplerNode
import com.cws.kanvas.shaderc.nodes.TextureNode
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.print.Print

object Translation : Translator {

    val TAG = Translation::class.simpleName.orEmpty()

    var translator: Translator = GLSLTranslator()

//    inline fun <reified T : Node> type(): String {
//        when (T::class) {
//            // arrays
//            ArrayNode::class -> {
//                Print.e(TAG, "type(nodeType: String) doesn't support ArrayNode. Please use typeArray(nodeType: String, size: Int) instead!")
//                return ""
//            }
//        }
//
//        val nodeType = T::class.simpleName
//        return if (nodeType.isNullOrEmpty()) {
//            Print.e(TAG, "Unsupported nodeType = $nodeType. Can't be null or empty!")
//            ""
//        } else {
//            type(nodeType)
//        }
//    }

    // Basic
    fun <L : Node, R: Node> Translation.op(left: L, op: String, right: R) = "($left $op $right)"
    fun <L : Node, R : Node> Translation.assign(left: L, right: R) = "$left = $right;"

    // Math
    fun <L : Node, R : Node> Translation.dot(left: L, right: R) = "dot($left, $right)"
    fun <L : Node, R : Node> Translation.cross(left: L, right: R) = "cross($left, $right)"
    fun <T : Node> Translation.abs(value: T) = "abs($value)"
    fun <T : Node> Translation.sign(value: T) = "sign($value)"
    fun <T : Node> Translation.floor(value: T) = "floor($value)"
    fun <T : Node> Translation.ceil(value: T) = "ceil($value)"
    fun <T : Node> Translation.round(value: T) = "round($value)"
    fun <T : Node> Translation.trunc(value: T) = "trunc($value)"
    fun <T : Node> Translation.fract(value: T) = "fract($value)"
    fun <T : Node> Translation.clamp(x: T, min: T, max: T) = "clamp($x, $min, $max)"
    fun <T : Node> Translation.mix(a: T, b: T, t: T) = "mix($a, $b, $t)"
    fun <T : Node> Translation.step(edge: T, x: T) = "step($edge, $x)"
    fun <T : Node> Translation.smoothstep(e0: T, e1: T, x: T) = "smoothstep($e0, $e1, $x)"
    fun <T : Node> Translation.sqrt(x: T) = "sqrt($x)"
    fun <T : Node> Translation.inversesqrt(x: T) = "inversesqrt($x)"
    fun <T : Node> Translation.pow(base: T, exp: T) = "pow($base, $exp)"
    fun <T : Node> Translation.exp(x: T) = "exp($x)"
    fun <T : Node> Translation.log(x: T) = "log($x)"
    fun <T : Node> Translation.exp2(x: T) = "exp2($x)"
    fun <T : Node> Translation.log2(x: T) = "log2($x)"
    fun <T : Node> Translation.sin(x: T) = "sin($x)"
    fun <T : Node> Translation.cos(x: T) = "cos($x)"
    fun <T : Node> Translation.tan(x: T) = "tan($x)"
    fun <T : Node> Translation.asin(x: T) = "asin($x)"
    fun <T : Node> Translation.acos(x: T) = "acos($x)"
    fun <T : Node> Translation.atan(x: T) = "atan($x)"
    fun <T : Node> Translation.atan2(y: T, x: T) = "atan2($y, $x)"
    fun <T : Node> Translation.sinh(x: T) = "sinh($x)"
    fun <T : Node> Translation.cosh(x: T) = "cosh($x)"
    fun <T : Node> Translation.tanh(x: T) = "tanh($x)"
    fun <T : Node> Translation.asinh(x: T) = "asinh($x)"
    fun <T : Node> Translation.acosh(x: T) = "acosh($x)"
    fun <T : Node> Translation.atanh(x: T) = "atanh($x)"
    fun <T : Node> Translation.length(v: T) = "length($v)"
    fun <T : Node> Translation.normalize(v: T) = "normalize($v)"
    fun <T : Node> Translation.distance(p1: T, p2: T) = "distance($p1, $p2)"
    fun <T : Node> Translation.reflect(v: T, n: T, eta: T) = "reflect($v, $n)"
    fun <T : Node> Translation.refract(v: T, n: T, eta: T) = "refract($v, $n, $eta)"
    fun <T : Node> Translation.faceforward(n: T, v: T, ref: T) = "faceforward($n, $v, $ref)"
    fun <T : Node> Translation.transpose(m: T) = "transpose($m)"
    fun <T : Node> Translation.determinant(m: T) = "determinant($m)"
    fun <T : Node> Translation.inverse(m: T) = "inverse($m)"

    // Translator specific

    override fun type(type: Type): String = translator.type(type)

    override fun const(): String = translator.const()

    override fun declare(type: Type, name: String, expr: String?): String = translator.declare(type, name, expr)

    override fun function(
        type: Type,
        name: String,
        args: String,
        scope: FunctionScope,
        result: String
    ): String = translator.function(type, name, args, scope, result)

    override fun layout(group: Int, binding: Int, alignment: String): String = translator.layout(group, binding, alignment)

    override fun <T : Node> mod(value: T): String = translator.mod(value)

    override fun sample(
        samplerNode: SamplerNode,
        textureNode: TextureNode,
        uv: Float2Node
    ): String = translator.sample(samplerNode, textureNode, uv)

    override fun texture(samplerNode: SamplerNode, uv: Float2Node): String = translator.texture(samplerNode, uv)
    override fun texture(samplerNode: SamplerNode, uv: Float2Node, bias: FloatNode): String = translator.texture(samplerNode, uv, bias)
    override fun texture(samplerNode: SamplerNode, uv: Float3Node): String = translator.texture(samplerNode, uv)
    override fun textureGrad(
        samplerNode: SamplerNode,
        uv: Float2Node,
        dx: Float2Node,
        dy: Float2Node
    ): String = translator.textureGrad(samplerNode, uv, dx, dy)
    override fun textureLod(samplerNode: SamplerNode, uv: Float2Node): String = translator.textureLod(samplerNode, uv)
    override fun textureLod(samplerNode: SamplerNode, uv: Float2Node, lod: FloatNode): String = translator.textureLod(samplerNode, uv, lod)

    override fun gl_Position(): String = translator.gl_Position()

}