package com.cws.kanvas.rendering.frontend.shader

import com.cws.kanvas.rendering.frontend.CameraDataNode
import com.cws.kanvas.rendering.frontend.shader.nodes.Float3Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.Float4x4Node
import com.cws.kanvas.rendering.frontend.shader.nodes.FloatNode
import com.cws.kanvas.rendering.frontend.shader.nodes.SamplerNode
import com.cws.kanvas.rendering.frontend.shader.nodes.times
import com.cws.kanvas.rendering.frontend.shader.scopes.Scope
import com.cws.kanvas.rendering.frontend.shader.scopes.function.arg
import com.cws.kanvas.rendering.frontend.shader.scopes.function.function
import com.cws.kanvas.rendering.frontend.shader.scopes.shader.ComputeScope
import com.cws.kanvas.rendering.frontend.shader.scopes.shader.FragmentScope
import com.cws.kanvas.rendering.frontend.shader.scopes.shader.VertexScope
import com.cws.kanvas.rendering.frontend.shader.scopes.shader.main

inline fun <T : Scope> root(scope: T, block: T.() -> Unit): String {
    val buffer = StringBuilder()
    scope.block()
    scope.parse(buffer)
    return buffer.toString()
}

inline fun vertexShader(block: VertexScope.() -> Unit) = root(VertexScope(), block)
inline fun fragmentShader(block: FragmentScope.() -> Unit) = root(FragmentScope(), block)
inline fun computeShader(block: ComputeScope.() -> Unit) = root(ComputeScope(), block)

fun test() {
    vertexShader {
        val transform by function<Float4Node> {
            val pos by arg<Float3Node>()
            val model by arg<Float4x4Node>()
            val camera by arg<CameraDataNode>()

            camera.projection * camera.view * model
        }

        main {
            val t = transform.invoke<Float4x4Node>()
            gl_Position `=`
        }
    }
}