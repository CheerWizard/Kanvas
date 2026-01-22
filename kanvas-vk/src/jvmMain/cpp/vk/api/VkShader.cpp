//
// Created by cheerwizard on 20.10.25.
//

#include "VkShader.hpp"

VkShader::VkShader(VkDevice device, const VkShaderInfo &info)
: device(device), info(info) {
    VkShaderModuleCreateInfo create_info = {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = info.spirvCodeSize * sizeof(u32),
            .pCode = info.spirvCode,
    };
    VK_CHECK(vkCreateShaderModule(device, &create_info, VK_CALLBACKS, &shader));
}

VkShader::~VkShader() {
    if (shader) {
        vkDestroyShaderModule(device, shader, VK_CALLBACKS);
        shader = nullptr;
    }
}
