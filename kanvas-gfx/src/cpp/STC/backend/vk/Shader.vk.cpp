//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"

namespace stc {

    Shader::Shader(const char *name) {
        char filepath[256];
        sprintf(filepath, "%s/");
        std::vector<u32> byteCode = fileManager.readSPV(name);
        VkShaderModuleCreateInfo createInfo = {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = byteCode.size() * sizeof(uint32_t),
            .pCode = byteCode.data()
        };
        New(device, createInfo);
    }

    Shader::~Shader() {
        Delete();
    }

}