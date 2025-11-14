//
// Created by cheerwizard on 20.10.25.
//

#include "../Shader.hpp"
#include "backend/Device.hpp"

namespace stc {

    Shader::Shader(const Device& device, const ShaderCreateInfo& create_info)
    : info(create_info), binding_layout(device, create_info.bindings) {

        if (create_info.source.type != SHADER_SOURCE_TYPE_SPIRV) {
            switch (create_info.source.type) {
                case SHADER_SOURCE_TYPE_GLSL:
                    break;
                case SHADER_SOURCE_TYPE_WGSL:
                    ASSERT(false, TAG, "Compilation from WGSL to SPIR-V source type is not supported yet!");
                    break;
                case SHADER_SOURCE_TYPE_MSL:
                    ASSERT(false, TAG, "Compilation from MSL to SPIR-V source type is not supported yet!");
                    break;
                default:
                    break;
            }
        }

        New(device.handle, VkShaderModuleCreateInfo {
            .sType = VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
            .codeSize = spirvBinary.size() * sizeof(u32),
            .pCode = spirvBinary.data()
        });

    }

    Shader::~Shader() {
        Delete();
    }

}
