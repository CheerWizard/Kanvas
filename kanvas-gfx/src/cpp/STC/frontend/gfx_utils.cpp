//
// Created by cheerwizard on 22.07.25.
//

#include <fstream>

// TODO: will consider how to port it so I can use it to translate shader to SPIRV

//    // read shader file
//    std::ifstream file(filepath, std::ios::ate | std::ios::binary);
//    ASSERT(file.is_open(), "Failed to open shader file %s", filepath);
//    size_t fileSize = file.tellg();
//    char* buffer = new char[fileSize];
//    file.seekg(0);
//    file.read(buffer, fileSize);
//    file.close();
//    // compile shader to bytecode
//    shaderc::Compiler compiler;
//    shaderc::CompileOptions options;
//    shaderc_shader_kind shaderKind;
//    switch (type) {
//        case VK_SHADER_STAGE_VERTEX_BIT:
//            shaderKind = shaderc_glsl_vertex_shader;
//            break;
//        case VK_SHADER_STAGE_FRAGMENT_BIT:
//            shaderKind = shaderc_glsl_fragment_shader;
//            break;
//        case VK_SHADER_STAGE_COMPUTE_BIT:
//            shaderKind = shaderc_glsl_compute_shader;
//            break;
//        default:
//            ASSERT(false, "Unsupported shader type = %d", type);
//            break;
//    }
//    shaderc::SpvCompilationResult result = compiler.CompileGlslToSpv(buffer, shaderKind, filepath, options);
//    const auto& errorMessage = result.GetErrorMessage();
//    const auto& status = result.GetCompilationStatus();
//    delete[] buffer;
//    ASSERT(status == shaderc_compilation_status_success, "Failed to compile shader code: %s", errorMessage.c_str())
//    return { result.cbegin(), result.cend() };