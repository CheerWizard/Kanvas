package com.cws.kanvas.rendering.frontend.shader.scopes.shader

import com.cws.kanvas.rendering.frontend.shader.ScopeDsl
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.nodes.BooleanNode
import com.cws.kanvas.rendering.frontend.shader.nodes.FloatNode
import com.cws.kanvas.rendering.frontend.shader.nodes.IntNode
import com.cws.kanvas.rendering.frontend.shader.nodes.TypeNode
import com.cws.kanvas.rendering.frontend.shader.nodes.UIntNode
import com.cws.kanvas.rendering.frontend.shader.scopes.Scope
import com.cws.kanvas.rendering.frontend.shader.type
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@ScopeDsl
open class ShaderScope: Scope()

inline fun <reified T : TypeNode> ShaderScope.Var(): ReadOnlyProperty<Any?, T> {
    return object : ReadOnlyProperty<Any?, T> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val varName = property.name
            val type = type<T>()
            appendLine("$type $varName;")
            return node<T>(varName)
        }
    }
}

fun ShaderScope.bool(): ReadOnlyProperty<Any?, BooleanNode> {
    return object : ReadOnlyProperty<Any?, BooleanNode> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): BooleanNode {
            val varName = property.name
            appendLine("${Type.Primitive.Boolean} $varName;")
            return BooleanNode(varName)
        }
    }
}

fun ShaderScope.float(): ReadOnlyProperty<Any?, FloatNode> {
    return object : ReadOnlyProperty<Any?, FloatNode> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): FloatNode {
            val varName = property.name
            appendLine("${Type.Primitive.Float} $varName;")
            return FloatNode(varName)
        }
    }
}

fun ShaderScope.int(): ReadOnlyProperty<Any?, IntNode> {
    return object : ReadOnlyProperty<Any?, IntNode> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): IntNode {
            val varName = property.name
            appendLine("${Type.Primitive.Int} $varName;")
            return IntNode(varName)
        }
    }
}

fun ShaderScope.uint(): ReadOnlyProperty<Any?, UIntNode> {
    return object : ReadOnlyProperty<Any?, UIntNode> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): UIntNode {
            val varName = property.name
            appendLine("${Type.Primitive.UInt} $varName;")
            return UIntNode(varName)
        }
    }
}