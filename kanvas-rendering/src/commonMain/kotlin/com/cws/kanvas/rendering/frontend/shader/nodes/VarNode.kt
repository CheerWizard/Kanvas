package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Expr
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.assign
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader.type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class VarNode<T : Node>(
    override val expr: String,
    var value: T?,
): Node() {
    override val type: Type = value?.type ?: Type.Void
}

context(scope: FunctionScope)
infix fun <T : Node> VarNode<T>.`=`(value: T) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<BooleanNode>.`=`(value: Boolean) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<FloatNode>.`=`(value: Float) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<IntNode>.`=`(value: Int) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<UIntNode>.`=`(value: UInt) {
    scope.appendLine(Expr.assign(this, value))
}

inline fun <reified T : Node> FunctionScope.Var(node: T? = null): ReadOnlyProperty<Any?, VarNode<T>> {
    return object : ReadOnlyProperty<Any?, VarNode<T>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): VarNode<T> {
            val varName = property.name
            val type = type<T>()
            if (node == null) {
                appendLine("$type $varName;")
            } else {
                appendLine("$type $varName = $node;")
            }
            return VarNode(varName, node)
        }
    }
}

fun FunctionScope.VarBool(value: Boolean? = null) = Var(value?.toNode())
fun FunctionScope.VarFloat(value: Float? = null) = Var(value?.toNode())
fun FunctionScope.VarInt(value: Int? = null) = Var(value?.toNode())
fun FunctionScope.VarUInt(value: UInt? = null) = Var(value?.toNode())