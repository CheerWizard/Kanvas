//
// Created by cheerwizard on 18.10.25.
//

#include "../Pipeline.hpp"
#include "backend/Device.hpp"
#include "backend/RenderTarget.hpp"

#include "frontend/Vertex.hpp"

stc::Pipeline::Pipeline(const Device &device, const PipelineCreateInfo &create_info) {
    uint32_t stride = 0;

    std::vector<VkVertexInputAttributeDescription> vk_attributes(create_info.vertexAttributes.size());

    for (int i = 0 ; i < create_info.vertexAttributes.size(); i++) {
        const auto& attribute = create_info.vertexAttributes[i];
        auto& vk_attribute = vk_attributes[i];

        uint32_t size = static_cast<uint32_t>(attribute.type) * sizeof(float);

        vk_attribute.binding = create_info.vertexBufferSlot;
        vk_attribute.location = attribute.location;
        vk_attribute.format = attribute.format;
        vk_attribute.offset = size;

        stride += size;
    }

    VkVertexInputBindingDescription bindingDescription = {
        .binding = create_info.vertexBufferSlot,
        .stride = stride,
    };

    if (create_info.instanced) {
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

    VkPipelineVertexInputStateCreateInfo* pVertexInputInfo = null;
    if (vk_attributes.size() > 0) {
        pVertexInputInfo = &vertexInputInfo;
    }

    VkPipelineInputAssemblyStateCreateInfo inputAssembly = { VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO };
    inputAssembly.topology = (VkPrimitiveTopology) create_info.primitiveTopology;
    inputAssembly.primitiveRestartEnable = VK_FALSE;

    std::array<std::pair<VkShaderStageFlagBits, VkShaderModule>, 5> shaderModules = {
        std::pair(VK_SHADER_STAGE_VERTEX_BIT, create_info.vertexShader->handle),
        { VK_SHADER_STAGE_FRAGMENT_BIT, create_info.fragmentShader->handle },
        { VK_SHADER_STAGE_GEOMETRY_BIT, create_info.geometryShader->handle },
        { VK_SHADER_STAGE_TESSELLATION_EVALUATION_BIT, create_info.tessEvalShader->handle },
        { VK_SHADER_STAGE_TESSELLATION_CONTROL_BIT, create_info.tessEvalShader->handle },
    };

    std::vector<VkPipelineShaderStageCreateInfo> shaderStages;

    for (auto shaderModule : shaderModules) {
        if (shaderModule.second) {
            VkPipelineShaderStageCreateInfo shaderStageInfo {
                .sType = VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO,
                .stage = shaderModule.first,
                .module = shaderModule.second,
                .pName = "main",
            };
            shaderStages.emplace_back(shaderStageInfo);
        }
    }

    std::array<VkDynamicState, 2> dynamicStates = {
        VK_DYNAMIC_STATE_VIEWPORT,
        VK_DYNAMIC_STATE_SCISSOR
    };

    VkPipelineDynamicStateCreateInfo dynamicState = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO,
        .dynamicStateCount = dynamicStates.size(),
        .pDynamicStates = dynamicStates.data(),
    };

    VkViewport vkViewport = {
        .x = create_info.viewport.x,
        .y = create_info.viewport.y,
        .width = static_cast<float>(create_info.viewport.width),
        .height = static_cast<float>(create_info.viewport.height),
        .minDepth = create_info.viewport.minDepth,
        .maxDepth = create_info.viewport.maxDepth,
    };

    VkRect2D scissor = {
        .offset = { static_cast<int32_t>(create_info.viewport.x), static_cast<int32_t>(create_info.viewport.y) },
        .extent = { create_info.viewport.width, create_info.viewport.height },
    };

    VkPipelineViewportStateCreateInfo viewportState = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO,
        .viewportCount = 1,
        .pViewports = &vkViewport,
        .scissorCount = 1,
        .pScissors = &scissor,
    };

    VkPipelineRasterizationStateCreateInfo rasterizer = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO,
        .depthClampEnable = VK_FALSE,
        .rasterizerDiscardEnable = VK_FALSE,
        .polygonMode = (VkPolygonMode) create_info.polygonMode,
        .cullMode = (VkCullModeFlags) create_info.cullMode,
        .frontFace = (VkFrontFace) create_info.frontFace,
        .depthBiasEnable = VK_FALSE,
        .depthBiasConstantFactor = 0.0f,
        .depthBiasClamp = 0.0f,
        .depthBiasSlopeFactor = 0.0f,
        .lineWidth = create_info.lineWidth,
    };

    VkPipelineMultisampleStateCreateInfo multisampling = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO,
        .rasterizationSamples = (VkSampleCountFlagBits) create_info.sampleCount,
        .sampleShadingEnable = VK_FALSE,
        .minSampleShading = 1.0f,
        .pSampleMask = nullptr,
        .alphaToCoverageEnable = VK_FALSE,
        .alphaToOneEnable = VK_FALSE,
    };

    VkPipelineColorBlendAttachmentState colorBlendAttachment = {
        .blendEnable = create_info.enableBlending,
        .srcColorBlendFactor = (VkBlendFactor) create_info.srcBlendFactor,
        .dstColorBlendFactor = (VkBlendFactor) create_info.dstBlendFactor,
        .colorBlendOp = (VkBlendOp) create_info.colorBlendOp,
        .srcAlphaBlendFactor = (VkBlendFactor) create_info.alphaSrcBlendFactor,
        .dstAlphaBlendFactor = (VkBlendFactor) create_info.alphaDstBlendFactor,
        .alphaBlendOp = (VkBlendOp) create_info.alphaBlendOp,
        .colorWriteMask = VK_COLOR_COMPONENT_R_BIT | VK_COLOR_COMPONENT_G_BIT | VK_COLOR_COMPONENT_B_BIT | VK_COLOR_COMPONENT_A_BIT,
    };

    VkPipelineColorBlendStateCreateInfo colorBlending = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO,
        .logicOpEnable = VK_FALSE,
        .logicOp = VK_LOGIC_OP_COPY,
        .attachmentCount = 1,
        .pAttachments = &colorBlendAttachment,
        .blendConstants = { 0.0f, 0.0f, 0.0f, 0.0f },
    };

    VkPipelineLayoutCreateInfo pipelineLayoutInfo = {
        .sType = VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO,
        .setLayoutCount = 0,
        .pSetLayouts = nullptr,
        .pushConstantRangeCount = 0,
        .pPushConstantRanges = nullptr,
    };

    layout.New(device.handle, pipelineLayoutInfo);

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
        .layout = layout.handle,
        .renderPass = create_info.renderPass->handle,
        .subpass = 0,
        .basePipelineHandle = VK_NULL_HANDLE,
        .basePipelineIndex = -1,
    };

    CALL(vkCreateGraphicsPipelines(device.handle, null, 1, &pipelineInfo, &VulkanAllocator::getInstance().callbacks, &handle));
}

stc::Pipeline::~Pipeline() {
    Delete();
}