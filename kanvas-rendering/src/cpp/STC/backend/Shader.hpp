//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_SHADER_HPP
#define STC_SHADER_HPP

#include "Binding.hpp"

namespace stc {

#ifdef VK

    struct ShaderBackend : ShaderModuleHandle {};

    enum ShaderStage {
        SHADER_STAGE_VERTEX = VK_SHADER_STAGE_VERTEX_BIT,
        SHADER_STAGE_FRAGMENT = VK_SHADER_STAGE_FRAGMENT_BIT,
        SHADER_STAGE_COMPUTE = VK_SHADER_STAGE_COMPUTE_BIT,
        SHADER_STAGE_DEFAULT = SHADER_STAGE_VERTEX,
    };

#elif METAL

    struct ShaderBackend : ShaderModuleHandle {};

    enum ShaderStage {
        SHADER_STAGE_VERTEX = MTL::RenderStageVertex,
        SHADER_STAGE_FRAGMENT = MTL::RenderStageFragment,
        SHADER_STAGE_DEFAULT = SHADER_STAGE_VERTEX,
    };

#elif WEBGPU

    struct ShaderBackend : ShaderModuleHandle {};

    enum ShaderStage {
        SHADER_STAGE_VERTEX = WGPUShaderStage_Vertex,
        SHADER_STAGE_FRAGMENT = WGPUShaderStage_Fragment,
        SHADER_STAGE_COMPUTE = WGPUShaderStage_Compute,
    };

#endif

    struct Device;

    enum ShaderSourceType {
        SHADER_SOURCE_TYPE_SPIRV,
        SHADER_SOURCE_TYPE_GLSL,
        SHADER_SOURCE_TYPE_WGSL,
        SHADER_SOURCE_TYPE_MSL,
        SHADER_SOURCE_TYPE_HLSL,
        SHADER_SOURCE_TYPE_DEFAULT = SHADER_SOURCE_TYPE_GLSL,
    };

    struct ShaderSource {
        ShaderSourceType type = SHADER_SOURCE_TYPE_DEFAULT;
        ShaderStage stage = SHADER_STAGE_DEFAULT;
        std::string text;
        std::vector<u32> binary;
    };

    struct ShaderCreateInfo {
        std::string name;
        std::string entryPoint = "main";
        ShaderSource source;
        std::vector<Binding> bindings;
    };

    struct Shader : ShaderBackend {
        ShaderCreateInfo info;
        Scope<BindingLayout> binding_layout;

        Shader(const Device& device, const ShaderCreateInfo& create_info);
        ~Shader();

        void updateBindings(const std::vector<Binding>& bindings);

    private:
        static constexpr auto TAG = "Shader";
    };

}

#endif //STC_SHADER_HPP