//
// Created by cheerwizard on 20.10.25.
//

#include "VkShader.hpp"
#include "VkBindingLayout.hpp"
#include "VkContext.hpp"
#include "VkPipe.hpp"

VkShader* VkShader_create(VkContext* context, VkShaderInfo* info) {
    return new VkShader(context->device, *info);
}

void VkShader_destroy(VkShader* shader) {
    delete shader;
}

void VkShader_setInfo(VkShader* shader, VkShaderInfo* info) {
    shader->update(*info);
}

VkShader::VkShader(VkDevice device, const VkShaderInfo &info)
: device(device), info(info) {
    VkShaderModuleCreateInfo create_info = {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = info.spirvCodeSize * sizeof(u32),
            .pCode = info.spirvCode,
    };
    VK_CHECK(vkCreateShaderModule(device, &create_info, VK_CALLBACKS, &shader));
    VK_DEBUG_NAME(device, VK_OBJECT_TYPE_SHADER_MODULE, shader, info.name);

    binding_layout = new VkBindingLayout(device, VkBindingInfo {
        .bindings = info.bindings,
        .bindingsCount = info.bindingsCount,
    });
}

VkShader::~VkShader() {
    if (shader) {
        vkDestroyShaderModule(device, shader, VK_CALLBACKS);
        shader = nullptr;
    }

    if (binding_layout) {
        delete binding_layout;
        binding_layout = nullptr;
    }
}

void VkShader::update(const VkShaderInfo& newInfo) {
    this->~VkShader();
    new (this) VkShader(device, newInfo);

    if (pipe) {
        pipe->update();
    }

    if (binding_layout) {
        binding_layout->update(VkBindingInfo {

        });
    }
}
