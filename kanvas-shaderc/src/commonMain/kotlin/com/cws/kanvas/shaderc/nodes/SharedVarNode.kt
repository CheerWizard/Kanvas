package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.scopes.shader.ComputeScope
import com.cws.kanvas.shaderc.translation.Translation.assign
import kotlin.properties.ReadOnlyProperty

class SharedVarNode<T : Node>(
    override val expr: String,
    var value: T?,
): Node()

context(scope: FunctionScope)
infix fun <T : Node> SharedVarNode<T>.`=`(value: T) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<BooleanNode>.`=`(value: Boolean) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<FloatNode>.`=`(value: Float) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun SharedVarNode<IntNode>.`=`(value: Int) {
    scope.appendLine(Translation.assign(this, value))
}

inline fun <reified T : Node> ComputeScope.SharedVar(node: T? = null): ReadOnlyProperty<Any?, SharedVarNode<T>> {
    return ReadOnlyProperty<Any?, SharedVarNode<T>> { thisRef, property ->
        val varName = property.name
        appendLine(Translation.declare(Translation.type<T>(), varName, node?.toString()))
        SharedVarNode(varName, node)
    }
}

fun ComputeScope.SharedBool(value: Boolean? = null) = SharedVar(value?.toNode())
fun ComputeScope.SharedFloat(value: Float? = null) = SharedVar(value?.toNode())
fun ComputeScope.SharedInt(value: Int? = null) = SharedVar(value?.toNode())