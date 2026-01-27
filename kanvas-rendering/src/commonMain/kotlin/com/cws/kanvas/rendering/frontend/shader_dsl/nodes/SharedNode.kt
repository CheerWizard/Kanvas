package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.assign
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.shader.GraphicsScope
import com.cws.kanvas.rendering.frontend.shader_dsl.type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SharedNode<T : Node>(
    override val expr: String,
    var value: T?,
): Node() {
    override val type: Type = value?.type ?: Type.Void
}

context(scope: FunctionScope)
infix fun <T : Node> SharedNode<T>.`=`(value: T) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedNode<BooleanNode>.`=`(value: Boolean) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedNode<FloatNode>.`=`(value: Float) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedNode<IntNode>.`=`(value: Int) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedNode<UIntNode>.`=`(value: UInt) {
    scope.appendLine(Expr.assign(this, value))
}

inline fun <reified T : Node> GraphicsScope.Shared(node: T? = null): ReadOnlyProperty<Any?, SharedNode<T>> {
    return object : ReadOnlyProperty<Any?, SharedNode<T>> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): SharedNode<T> {
            val varName = property.name
            val type = type<T>()
            if (node == null) {
                appendLine("$type $varName;")
            } else {
                appendLine("$type $varName = $node;")
            }
            return SharedNode(varName, node)
        }
    }
}

fun GraphicsScope.SharedBool(value: Boolean? = null) = Shared(value?.toNode())
fun GraphicsScope.SharedFloat(value: Float? = null) = Shared(value?.toNode())
fun GraphicsScope.SharedInt(value: Int? = null) = Shared(value?.toNode())
fun GraphicsScope.SharedUInt(value: UInt? = null) = Shared(value?.toNode())