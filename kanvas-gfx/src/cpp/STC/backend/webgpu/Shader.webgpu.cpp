//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const ShaderCreateInfo& create_info)
    : binding_layout(device, create_info.bindings) {
        switch (create_info.source.type) {
            case SHADER_SOURCE_TYPE_GLSL:
                break;
            case SHADER_SOURCE_TYPE_WGSL:
                break;
            case SHADER_SOURCE_TYPE_SPIRV:
                break;
        }

        const char* wgslSource = create_info.source.text;

        WGPUShaderModuleWGSLDescriptor wgslDescriptor = {
            .chain.sType = WGPUSType_ShaderModuleWGSLDescriptor,
            .chain.next = nullptr,
            .source = wgslSource,
        };

        New(device.handle, WGPUShaderModuleDescriptor {
            .nextInChain = (WGPUChainedStruct const*) &wgslDescriptor,
            .label = create_info.name,
        });
    }

    Shader::~Shader() {
        Delete();
    }

}
