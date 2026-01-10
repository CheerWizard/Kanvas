//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const ShaderCreateInfo& create_info)
    : info(create_info), binding_layout(device, create_info.bindings) {
        if (create_info.source.type != SHADER_SOURCE_TYPE_SPIRV) {
            LOG_ERROR(TAG, "Failed to compile not SPIR-V source type for Vulkan!");
            return;
        }

        New(device.handle, VkShaderModuleCreateInfo {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = create_info.source.binary.size() * sizeof(u32),
            .pCode = create_info.source.binary.data(),
        });
    }

    Shader::~Shader() {
        Delete();
    }

    void Shader::updateBindings(const std::vector<Binding> &bindings) {

    }

}
