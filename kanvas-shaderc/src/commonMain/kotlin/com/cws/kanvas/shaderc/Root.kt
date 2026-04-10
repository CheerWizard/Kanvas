package com.cws.kanvas.shaderc

import com.cws.kanvas.shaderc.scopes.Scope
import com.cws.kanvas.shaderc.scopes.shader.ComputeScope
import com.cws.kanvas.shaderc.scopes.shader.FragmentScope
import com.cws.kanvas.shaderc.scopes.shader.VertexScope

inline fun <T : Scope> root(scope: T, block: T.() -> Unit): String {
    val buffer = StringBuilder()
    scope.block()
    scope.parse(buffer)
    return buffer.toString()
}

inline fun vertexShader(block: VertexScope.() -> Unit) = root(VertexScope(), block)
inline fun fragmentShader(block: FragmentScope.() -> Unit) = root(FragmentScope(), block)
inline fun computeShader(block: ComputeScope.() -> Unit) = root(ComputeScope(), block)