//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const char* shaderName, const char* shaderPath) {
        auto wgslSource = FileBridge::loadFile(shaderPath);

        WGPUShaderModuleWGSLDescriptor wgslDescriptor = {
            .chain.sType = WGPUSType_ShaderModuleWGSLDescriptor,
            .chain.next = nullptr,
            .source = wgslSource.c_str(),
        };

        New(device.handle, WGPUShaderModuleDescriptor {
            .nextInChain = (WGPUChainedStruct const*) &wgslDescriptor,
            .label = shaderName,
        });
    }

    Shader::~Shader() {
        Delete();
    }

}
