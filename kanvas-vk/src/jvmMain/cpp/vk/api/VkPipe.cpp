//
// Created by cheerwizard on 18.10.25.
//

#include "VkPipe.hpp"

#include <vector>

#include "VkRenderTarget.hpp"
#include "VkShader.hpp"

VkPipe::VkPipe(VkDevice device, const VkPipelineInfo &info) {
    uint32_t stride = 0;

    std::vector<VkVertexInputAttributeDescription> vk_attributes(info.vertexAttributesCount);

    for (int i = 0 ; i < info.vertexAttributesCount; i++) {
        const auto& attribute = info.vertexAttributes[i];
        auto& vk_attribute = vk_attributes[i];

        uint32_t size = static_cast<uint32_t>(attribute.type) * sizeof(float);

        vk_attribute.binding = info.vertexBufferSlot;
        vk_attribute.location = attribute.location;
        vk_attribute.format = (VkFormat) attribute.format;
        vk_attribute.offset = size;

        stride += size;
    }

    VkVertexInputBindingDescription bindingDescription = {
        .binding = info.vertexBufferSlot,
        .stride = stride,
    };

    if (info.instanced == VK_TRUE) {
        bindingDescription.inputRate = VK_VERTEX_INPUT_RATE_INSTANCE;
    } else {
        bindingDescription.inputRate = VK_VERTEX_INPUT_RATE_VERTEX;
    }

    VkPipelineVertexInputStateCreateInfo vertexInputInfo {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO,
        .vertexBindingDescriptionCount = 1,
        .pVertexBindingDescriptions = &bindingDescription,
        .vertexAttributeDescriptionCount = static_cast<uint32_t>(vk_attributes.size()),
        .pVertexAttributeDescriptions = vk_attributes.data(),
    };

    VkPipelineVertexInputStateCreateInfo* pVertexInputInfo = nullptr;
    if (vk_attributes.size() > 0) {
        pVertexInputInfo = &vertexInputInfo;
    }

    VkPipelineInputAssemblyStateCreateInfo inputAssembly = {
        VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO
    };
    inputAssembly.topology = (VkPrimitiveTopology) info.primitiveTopology;
    inputAssembly.primitiveRestartEnable = VK_FALSE;

    std::array shaderModules = {
        std::pair(VK_SHADER_STAGE_VERTEX_BIT, info.vertexShader),
        std::pair(VK_SHADER_STAGE_FRAGMENT_BIT, info.fragmentShader),
        std::pair(VK_SHADER_STAGE_GEOMETRY_BIT, info.geometryShader),
    };

    std::vector<VkPipelineShaderStageCreateInfo> shaderStages;

    for (auto shaderModule : shaderModules) {
        if (shaderModule.second) {
            VkPipelineShaderStageCreateInfo shaderStageInfo {
                .sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO,
                .stage = shaderModule.first,
                .module = shaderModule.second->shader,
                .pName = shaderModule.second->info.entryPoint,
            };
            shaderStages.emplace_back(shaderStageInfo);
        }
    }

    std::array dynamicStates = {
        VK_DYNAMIC_STATE_VIEWPORT,
        VK_DYNAMIC_STATE_SCISSOR
    };

    VkPipelineDynamicStateCreateInfo dynamicState = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO,
        .dynamicStateCount = dynamicStates.size(),
        .pDynamicStates = dynamicStates.data(),
    };

    VkRect2D scissor = {
        .offset = { static_cast<int32_t>(info.viewport.x), static_cast<int32_t>(info.viewport.y) },
        .extent = { (uint32_t) info.viewport.width, (uint32_t) info.viewport.height },
    };

    VkPipelineViewportStateCreateInfo viewportState = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO,
        .viewportCount = 1,
        .pViewports = &info.viewport,
        .scissorCount = 1,
        .pScissors = &scissor,
    };

    VkPipelineRasterizationStateCreateInfo rasterizer = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO,
        .depthClampEnable = VK_FALSE,
        .rasterizerDiscardEnable = VK_FALSE,
        .polygonMode = (VkPolygonMode) info.polygonMode,
        .cullMode = (VkCullModeFlags) info.cullMode,
        .frontFace = (VkFrontFace) info.frontFace,
        .depthBiasEnable = VK_FALSE,
        .depthBiasConstantFactor = 0.0f,
        .depthBiasClamp = 0.0f,
        .depthBiasSlopeFactor = 0.0f,
        .lineWidth = info.lineWidth,
    };

    VkPipelineMultisampleStateCreateInfo multisampling = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO,
        .rasterizationSamples = (VkSampleCountFlagBits) info.sampleCount,
        .sampleShadingEnable = VK_FALSE,
        .minSampleShading = 1.0f,
        .pSampleMask = nullptr,
        .alphaToCoverageEnable = VK_FALSE,
        .alphaToOneEnable = VK_FALSE,
    };

    size_t colorAttachmentsCount = info.renderTarget->info.colorAttachmentsCount;

    ASSERT(info.renderTarget != nullptr && colorAttachmentsCount > 0, TAG, "Assertion failed. Render target must be set for VkPipe!")

    std::vector<VkPipelineColorBlendAttachmentState> colorAttachments(colorAttachmentsCount);

    for (int i = 0 ; i < colorAttachmentsCount ; i++) {
        const auto colorAttachment = info.renderTarget->info.colorAttachments[i];
        colorAttachments[i] = {
            .blendEnable = colorAttachment.blend.enable,
            .srcColorBlendFactor = colorAttachment.blend.srcFactorColor,
            .dstColorBlendFactor = colorAttachment.blend.dstFactorColor,
            .colorBlendOp = colorAttachment.blend.blendOpColor,
            .srcAlphaBlendFactor = colorAttachment.blend.srcFactorAlpha,
            .dstAlphaBlendFactor = colorAttachment.blend.dstFactorAlpha,
            .alphaBlendOp = colorAttachment.blend.blendOpAlpha,
            .colorWriteMask = VK_COLOR_COMPONENT_R_BIT | VK_COLOR_COMPONENT_G_BIT | VK_COLOR_COMPONENT_B_BIT | VK_COLOR_COMPONENT_A_BIT,
        };
    }

    VkPipelineColorBlendStateCreateInfo colorBlending = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO,
        .logicOpEnable = VK_FALSE,
        .logicOp = VK_LOGIC_OP_COPY,
        .attachmentCount = (uint32_t) colorAttachments.size(),
        .pAttachments = colorAttachments.data(),
        .blendConstants = { 0.0f, 0.0f, 0.0f, 0.0f },
    };

    VkPipelineLayoutCreateInfo pipelineLayoutInfo = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO,
        .setLayoutCount = 0,
        .pSetLayouts = nullptr,
        .pushConstantRangeCount = 0,
        .pPushConstantRanges = nullptr,
    };

    VK_CHECK(vkCreatePipelineLayout(device, &pipelineLayoutInfo, VK_CALLBACKS, &pipelineLayout));

    VkGraphicsPipelineCreateInfo pipelineInfo = {
        .sType = VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO,
        .stageCount = static_cast<uint32_t>(shaderStages.size()),
        .pStages = shaderStages.data(),
        .pVertexInputState = pVertexInputInfo,
        .pInputAssemblyState = &inputAssembly,
        .pViewportState = &viewportState,
        .pRasterizationState = &rasterizer,
        .pMultisampleState = &multisampling,
        .pDepthStencilState = nullptr,
        .pColorBlendState = &colorBlending,
        .pDynamicState = &dynamicState,
        .layout = pipelineLayout,
        .renderPass = info.renderTarget->render_pass,
        .subpass = 0,
        .basePipelineHandle = VK_NULL_HANDLE,
        .basePipelineIndex = -1,
    };

    VK_CHECK(vkCreateGraphicsPipelines(device, nullptr, 1, &pipelineInfo, VK_CALLBACKS, &pipeline));
}

VkPipe::~VkPipe() {
    if (pipeline) {
        vkDestroyPipeline(device, pipeline, VK_CALLBACKS);
        pipeline = nullptr;
    }
}