//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const char* shaderName, const char* shaderPath) {
        // TODO: need to support runtime SPIRV translation, to reload shader code in runtime
        auto bytecode = FileBridge::loadBinaryFile(shaderPath);
        New(device.handle, VkShaderModuleCreateInfo {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = byteCode.size() * sizeof(u32),
            .pCode = byteCode.data()
        });
    }

    Shader::~Shader() {
        Delete();
    }

}
