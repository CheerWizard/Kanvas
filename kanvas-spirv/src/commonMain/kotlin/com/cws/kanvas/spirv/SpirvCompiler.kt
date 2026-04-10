package com.cws.kanvas.spirv

import com.cws.print.Print
import com.cws.std.memory.NativeBuffer
import com.cws.std.memory.NativeString
import org.lwjgl.util.shaderc.Shaderc.*

enum class ShaderType(val value: Int) {
    VERTEX(shaderc_vertex_shader),
    FRAGMENT(shaderc_fragment_shader),
    GEOMETRY(shaderc_geometry_shader),
    COMPUTE(shaderc_compute_shader)
}

class SpirvCompiler {

    companion object {
        private const val TAG = "SpirvCompiler"
    }

    private var handle = 0L

    fun create() {
        if (handle == 0L) {
            handle = shaderc_compiler_initialize()
        }
    }

    fun destroy() {
        if (handle != 0L) {
            shaderc_compiler_release(handle)
        }
    }

    fun compile(
        shaderType: ShaderType,
        shaderName: NativeString,
        entryPoint: NativeString,
        source: NativeString,
    ): NativeBuffer? {
        val source = source.pack()
        if (source == null) {
            Print.e(TAG, "Failed to compile shader. Source is null or empty - $source")
            return null
        }

        val shaderName = shaderName.pack()
        if (shaderName == null) {
            Print.e(TAG, "Failed to compile shader. Shader name is null or empty - $shaderName")
            return null
        }

        val entryPoint = entryPoint.pack()
        if (entryPoint == null) {
            Print.e(TAG, "Failed to compile shader. Shader entry point is null or empty - $entryPoint")
            return null
        }

        val result = shaderc_compile_into_spv(
            handle,
            source.buffer,
            shaderType.value,
            shaderName.buffer,
            entryPoint.buffer,
            0
        )

        val status = shaderc_result_get_compilation_status(result)
        if (status == shaderc_compilation_status_success) {
            val spirvBytes = shaderc_result_get_bytes(result)
            if (spirvBytes == null) {
                Print.e(TAG, "Failed to compile shader. Shader result bytes is null")
                return null
            }
            return NativeBuffer(spirvBytes)
        } else {
            handleCompilationErrors(status)
            val message = shaderc_result_get_error_message(result)
            Print.e(TAG, "Error details: $message")
            return null
        }
    }

    private fun handleCompilationErrors(status: Int) {
        when (status) {
            shaderc_compilation_status_invalid_stage -> {
                Print.e(TAG, "Failed to compile shader. Invalid stage!")
            }
            shaderc_compilation_status_compilation_error -> {
                Print.e(TAG, "Failed to compile shader. Compilation error!")
            }
            shaderc_compilation_status_internal_error -> {
                Print.e(TAG, "Failed to compile shader. Internal error!")
            }
            shaderc_compilation_status_null_result_object -> {
                Print.e(TAG, "Failed to compile shader. Null result object!")
            }
            shaderc_compilation_status_invalid_assembly -> {
                Print.e(TAG, "Failed to compile shader. Invalid assembly!")
            }
            shaderc_compilation_status_validation_error -> {
                Print.e(TAG, "Failed to compile shader. Validation error!")
            }
            shaderc_compilation_status_transformation_error -> {
                Print.e(TAG, "Failed to compile shader. Transformation error!")
            }
            shaderc_compilation_status_configuration_error -> {
                Print.e(TAG, "Failed to compile shader. Configuration error!")
            }
        }
    }

}
