package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Expr
import com.cws.kanvas.rendering.frontend.shader_dsl.Type
import com.cws.kanvas.rendering.frontend.shader_dsl.assign
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.shader.ComputeScope
import com.cws.kanvas.rendering.frontend.shader_dsl.scopes.shader.GraphicsScope
import com.cws.kanvas.rendering.frontend.shader_dsl.type
import kotlin.properties.ReadOnlyProperty

class SharedVarNode<T : Node>(
    override val expr: String,
    var value: T?,
): Node() {
    override val type: Type = value?.type ?: Type.Void
}

context(scope: FunctionScope)
infix fun <T : Node> SharedVarNode<T>.`=`(value: T) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<BooleanNode>.`=`(value: Boolean) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<FloatNode>.`=`(value: Float) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<IntNode>.`=`(value: Int) {
    scope.appendLine(Expr.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<UIntNode>.`=`(value: UInt) {
    scope.appendLine(Expr.assign(this, value))
}

inline fun <reified T : Node> ComputeScope.SharedVar(node: T? = null): ReadOnlyProperty<Any?, SharedVarNode<T>> {
    return ReadOnlyProperty<Any?, SharedVarNode<T>> { thisRef, property ->
        val varName = property.name
        val type = type<T>()
        if (node == null) {
            appendLine("$type $varName;")
        } else {
            appendLine("$type $varName = $node;")
        }
        SharedVarNode(varName, node)
    }
}

fun ComputeScope.SharedBool(value: Boolean? = null) = SharedVar(value?.toNode())
fun ComputeScope.SharedFloat(value: Float? = null) = SharedVar(value?.toNode())
fun ComputeScope.SharedInt(value: Int? = null) = SharedVar(value?.toNode())
fun ComputeScope.SharedUInt(value: UInt? = null) = SharedVar(value?.toNode())