//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_SHADER_HPP
#define STC_SHADER_HPP

#include "Handle.hpp"
#include "../bridges/FileBridge.hpp"

namespace stc {

#ifdef VK

    enum ShaderStage {
        SHADER_STAGE_VERTEX = VK_SHADER_STAGE_VERTEX_BIT,
        SHADER_STAGE_FRAGMENT = VK_SHADER_STAGE_FRAGMENT_BIT,
        SHADER_STAGE_COMPUTE = VK_SHADER_STAGE_COMPUTE_BIT,
    };

    struct ShaderBackend : ShaderModuleHandle {};

#elif METAL



#elif WEBGPU

    enum ShaderStage {
        SHADER_STAGE_VERTEX = WGPUShaderStage_Vertex,
        SHADER_STAGE_FRAGMENT = WGPUShaderStage_Fragment,
        SHADER_STAGE_COMPUTE = WGPUShaderStage_Compute,
    };

    struct ShaderBackend : ShaderModuleHandle {};

#endif

    struct Device;

    struct ShaderCreateInfo {
        const char* name;
        const char* path;
        const char* entryPoint = "main";
    };

    struct Shader : ShaderBackend {
        ShaderCreateInfo info;

        Shader(const Device& device, const ShaderCreateInfo& create_info);
        ~Shader();
    };

}

#endif //STC_SHADER_HPP