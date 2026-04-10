package com.cws.kanvas.shaderc.sandbox

import com.cws.kanvas.shaderc.nodes.`=`
import com.cws.kanvas.shaderc.nodes.Float4Node
import com.cws.kanvas.shaderc.nodes.Float4x4Node
import com.cws.kanvas.shaderc.scopes.function.arg
import com.cws.kanvas.shaderc.scopes.function.function
import com.cws.kanvas.shaderc.scopes.shader.main
import com.cws.kanvas.shaderc.vertexShader
import com.cws.print.JVMPrintContext
import com.cws.print.Print
import java.io.File

fun main() {
    Print.install(
        context = JVMPrintContext(),
    ) {
        generateShader("shaderc/TestShader.shaderc") {
            vertexShader {
                val transform by function<Float4Node> {
                    val pos by arg<Float4Node>()
                    val model by arg<Float4x4Node>()
//                    val camera by arg<CameraDataNode>()

                    model * pos
                }

                main {
                    val t = transform()
                    gl_Position `=` t[0]
                }
            }
        }
    }
}

private inline fun generateShader(filepath: String, block: () -> String) {
    val shaderSource = block()
    try {
        File(filepath).writeText(shaderSource)
    } catch (e: Exception) {
        Print.e("ShadercSandbox", "generateShader failed to write shader to $filepath", e)
    }
}