package com.cws.kanvas.rendering.frontend.shader.nodes

import com.cws.kanvas.rendering.frontend.shader.Type.Array.Unsized
import com.cws.kanvas.rendering.frontend.shader.Type.Array.Sized
import com.cws.kanvas.rendering.frontend.shader.Type
import com.cws.kanvas.rendering.frontend.shader.node
import com.cws.kanvas.rendering.frontend.shader.scopes.function.FunctionScope
import com.cws.kanvas.rendering.frontend.shader.type

class ArrayNode(
    override val expr: String,
    val itemType: Type,
    val size: Int = 0,
) : Node() {
    override val type: Type = if (size == 0) Unsized(itemType) else Sized(itemType, size)

    context(scope: FunctionScope)
    inline operator fun <reified T> set(i: Int, value: T) {
        val itemType = type<T>()
        if (this.itemType == itemType) {
            scope.appendLine("$expr[$i] = $value;")
        } else {
            error("Wrong value type for ArrayNode. Actual=$itemType Expected=${this.itemType}")
        }
    }

    context(scope: FunctionScope)
    inline operator fun <reified T : Node> get(i: Int): T {
        return node(type, "$expr[$i]")
    }

}