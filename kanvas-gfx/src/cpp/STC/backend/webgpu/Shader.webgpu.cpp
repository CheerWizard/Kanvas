//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const ShaderCreateInfo& create_info) {
        auto wgslSource = FileBridge::loadFile(create_info.path);

        WGPUShaderModuleWGSLDescriptor wgslDescriptor = {
            .chain.sType = WGPUSType_ShaderModuleWGSLDescriptor,
            .chain.next = nullptr,
            .source = wgslSource.c_str(),
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
