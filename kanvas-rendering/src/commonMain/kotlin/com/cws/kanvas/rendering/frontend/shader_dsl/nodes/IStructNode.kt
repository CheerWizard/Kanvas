package com.cws.kanvas.rendering.frontend.shader_dsl.nodes

import com.cws.kanvas.rendering.frontend.shader_dsl.Type

abstract class IStructNode(
    type: Type.Struct,
) : Node() {
    override val type: Type = type
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class StructNode