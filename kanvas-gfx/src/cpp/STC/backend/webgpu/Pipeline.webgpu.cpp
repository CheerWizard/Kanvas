//
// Created by cheerwizard on 18.10.25.
//

#include "../Pipeline.hpp"

#include "backend/Device.hpp"
#include "backend/RenderTarget.hpp"

stc::Pipeline::Pipeline(const Device &device, const PipelineCreateInfo &create_info) {
    WGPURenderPipelineDescriptor descriptor = {};

    u32 stride = 0;
    u32 attributeCount = create_info.vertexAttributes.size();

    std::vector<WGPUVertexAttribute> wgpu_attributes(attributeCount);
    for (int i = 0 ; i < attributeCount; i++) {
        const auto& attribute = create_info.vertexAttributes[i];
        auto& wgpu_attribute = wgpu_attributes[i];

        u32 size = (u32) attribute.type * sizeof(float);

        wgpu_attribute.shaderLocation = attribute.location;
        wgpu_attribute.format = (WGPUVertexFormat) attribute.format;
        wgpu_attribute.offset = size;

        stride += size;
    }

    WGPUVertexBufferLayout vertexBufferLayout = {
        .arrayStride = stride,
        .attributeCount = attributeCount,
        .attributes = wgpu_attributes.data(),
    };

    if (create_info.instanced) {
        vertexBufferLayout.stepMode = WGPUVertexStepMode_Instance;
    } else {
        vertexBufferLayout.stepMode = WGPUVertexStepMode_Vertex;
    }

    WGPUVertexState vertex_state = {
        .module = create_info.vertexShader->handle,
        .entryPoint = create_info.vertexShader->info.entryPoint,
        .constantCount = 0,
        .constants = nullptr,
        .bufferCount = 1,
        .buffers = &vertexBufferLayout,
    };

    descriptor.vertex = vertex_state;

    const auto& color_attachments = create_info.render_target->info.colorAttachments;
    auto color_attachment_count = color_attachments.size();
    std::vector<WGPUColorTargetState> color_target_states(color_attachment_count);

    for (u32 i = 0 ; i < color_attachment_count ; i++) {
        auto& color_target_state = color_target_states[i];
        const auto& color_attachment = color_attachments[i];

        color_target_state.format = (WGPUTextureFormat) color_attachment.format;
        color_target_state.writeMask = WGPUColorWriteMask_All;

        WGPUBlendState blend_state = {
            .color = WGPUBlendComponent {
                .operation = (WGPUBlendOperation) color_attachment.blendColor.operation,
                .srcFactor = (WGPUBlendFactor) color_attachment.blendColor.srcFactor,
                .dstFactor = (WGPUBlendFactor) color_attachment.blendColor.dstFactor,
            },
            .alpha = WGPUBlendComponent {
                .operation = (WGPUBlendOperation) color_attachment.blendAlpha.operation,
                .srcFactor = (WGPUBlendFactor) color_attachment.blendAlpha.srcFactor,
                .dstFactor = (WGPUBlendFactor) color_attachment.blendAlpha.dstFactor,
            }
        };

        if (color_attachment.enableBlending) {
            color_target_state.blend = &blend_state;
        }
    }

    WGPUFragmentState fragment_state = {
        .module = create_info.fragmentShader->handle,
        .entryPoint = "main",
        .constantCount = 0,
        .constants = nullptr,
        .targetCount = (u32) color_target_states.size(),
        .targets = color_target_states.data(),
    };

    descriptor.fragment = &fragment_state;

    const auto& depth_attachment = create_info.render_target->info.depthAttachment;
    WGPUDepthStencilState depthStencil = {
        .format = (WGPUTextureFormat) depth_attachment.format,
        .depthWriteEnabled = depth_attachment.depthWriteEnabled,
        .depthCompare = (WGPUCompareFunction) depth_attachment.depthCompareOp,
    };
    if (depth_attachment.enabled) {
        descriptor.depthStencil = &depthStencil;
    }

    WGPUPrimitiveState primitive_state = {
        .topology = (WGPUPrimitiveTopology) create_info.primitiveTopology,
        // indices always have u16 size
        .stripIndexFormat = WGPUIndexFormat_Uint16,
        .frontFace = (WGPUFrontFace) create_info.frontFace,
        .cullMode = (WGPUCullMode) create_info.cullMode,
    };
    descriptor.primitive = primitive_state;

    // TODO change when need to support multisample pipeline
    WGPUMultisampleState multisample_state = {
        .count = create_info.sampleCount,
    };
    descriptor.multisample = multisample_state;

    usize binding_layout_count = create_info.binding_layouts.size();
    std::vector<WGPUBindGroupLayout> wgpu_bind_group_layouts(binding_layout_count);
    for (u32 i = 0 ; i < binding_layout_count ; i++) {
        wgpu_bind_group_layouts[i] = create_info.binding_layouts[i]->layout.handle;
    }
    layout.New(device.handle, WGPUPipelineLayoutDescriptor {
        .bindGroupLayoutCount = (u32) wgpu_bind_group_layouts.size(),
        .bindGroupLayouts = wgpu_bind_group_layouts.data(),
    });
    descriptor.layout = layout.handle;

    handle.New(device.handle, descriptor);
}

stc::Pipeline::~Pipeline() {
    handle.Delete();
}