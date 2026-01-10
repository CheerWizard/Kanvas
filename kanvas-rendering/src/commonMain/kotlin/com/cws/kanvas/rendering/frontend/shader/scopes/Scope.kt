package com.cws.kanvas.rendering.frontend.shader.scopes

import com.cws.kanvas.rendering.frontend.shader.ScopeDsl

@ScopeDsl
open class Scope {

    private val buffer = StringBuilder()
    private val scopes = mutableListOf<Scope>()

    fun parse(parentBuffer: StringBuilder) {
        scopes.forEach { scope -> scope.parse(buffer) }
        parentBuffer.append(buffer)
    }

    fun append(text: String) {
        buffer.append(text)
    }

    fun appendLine(text: String) {
        buffer.appendLine(text)
    }

    final override fun toString(): String = buffer.toString()

}