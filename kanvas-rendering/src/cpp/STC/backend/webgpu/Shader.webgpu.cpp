//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const ShaderCreateInfo& create_info)
    : binding_layout(device, create_info.bindings) {
        if (create_info.source.type != SHADER_SOURCE_TYPE_WGSL) {
            LOG_ERROR(TAG, "Failed to compile not WGSL source type for WebGPU!");
            return;
        }

        WGPUShaderModuleWGSLDescriptor wgslDescriptor = {
            .chain.sType = WGPUSType_ShaderModuleWGSLDescriptor,
            .chain.next = nullptr,
            .source = create_info.source.text.c_str(),
        };

        New(device.handle, WGPUShaderModuleDescriptor {
            .nextInChain = (WGPUChainedStruct const*) &wgslDescriptor,
            .label = create_info.name.c_str(),
        });
    }

    Shader::~Shader() {
        Delete();
    }

    void Shader::updateBindings(const std::vector<Binding> &bindings) {

    }

}
