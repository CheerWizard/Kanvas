package com.cws.kanvas.shaderc.nodes

import com.cws.kanvas.shaderc.translation.Translation
import com.cws.kanvas.shaderc.scopes.function.FunctionScope
import com.cws.kanvas.shaderc.translation.Translation.assign
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class VarNode<T : Node>(
    override val expr: String,
    var value: T?,
): Node()

context(scope: FunctionScope)
infix fun <T : Node> VarNode<T>.`=`(value: T) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<BooleanNode>.`=`(value: Boolean) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<FloatNode>.`=`(value: Float) {
    scope.appendLine(Translation.assign(this, value))
}

context(scope: FunctionScope)
infix fun VarNode<IntNode>.`=`(value: Int) {
    scope.appendLine(Translation.assign(this, value))
}

inline fun <reified T : Node> FunctionScope.Var(node: T? = null): ReadWriteProperty<Any?, VarNode<T>> {
    return object : ReadWriteProperty<Any?, VarNode<T>> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): VarNode<T> {
            val varName = property.name
            appendLine(Translation.declare(Translation.type<T>(), varName, node?.toString()))
            return VarNode(varName, node)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: VarNode<T>) {
            if (node != null) {
                value `=` node
            }
        }

    }
}

fun FunctionScope.VarBool(value: Boolean? = null) = Var(value?.toNode())
fun FunctionScope.VarFloat(value: Float? = null) = Var(value?.toNode())
fun FunctionScope.VarInt(value: Int? = null) = Var(value?.toNode())