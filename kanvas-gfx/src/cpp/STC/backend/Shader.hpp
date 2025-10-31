//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_SHADER_HPP
#define STC_SHADER_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    enum ShaderStage {
        SHADER_STAGE_VERTEX = VK_SHADER_STAGE_VERTEX_BIT,
        SHADER_STAGE_FRAGMENT = VK_SHADER_STAGE_FRAGMENT_BIT,
        SHADER_STAGE_GEOMETRY = VK_SHADER_STAGE_GEOMETRY_BIT,
        SHADER_STAGE_TESS_EVAL = VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT,
        SHADER_STAGE_TESS_CONTROL = VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT,
        SHADER_STAGE_COMPUTE = VK_SHADER_STAGE_COMPUTE_BIT,
    };

    struct ShaderBackend : ShaderModuleHandle {};

#elif METAL



#elif WEBGPU

    enum ShaderStage {
        SHADER_STAGE_VERTEX = WGPUShaderStage_Vertex,
        SHADER_STAGE_FRAGMENT = WGPUShaderStage_Fragment,
        SHADER_STAGE_GEOMETRY = WGPUShaderStage_None,
        SHADER_STAGE_TESS_EVAL = WGPUShaderStage_None,
        SHADER_STAGE_TESS_CONTROL = WGPUShaderStage_None,
        SHADER_STAGE_COMPUTE = WGPUShaderStage_Compute,
    };

    struct ShaderBackend : ShaderModuleHandle {};

#endif

    struct Shader : ShaderBackend {
        Shader(const char* src);
        ~Shader();
    };

}

#endif //STC_SHADER_HPP