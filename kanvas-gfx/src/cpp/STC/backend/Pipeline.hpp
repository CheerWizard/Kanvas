//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_PIPELINE_HPP
#define STC_PIPELINE_HPP

#include "Binding.hpp"
#include "../frontend/Vertex.hpp"
#include "../frontend/Viewport.hpp"

namespace stc {

#ifdef VK

    struct PipelineBackend {
        VkPipeline handle = null;
        DeviceHandle device;
        BufferHandle vertexBuffer = null;
        BufferHandle indexBuffer = null;
        PipelineLayoutHandle layout;
    };

    enum PrimitiveTopology {
        TRIANGLE_LIST = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST,
    };

    enum PolygonMode {
        POLYGON_MODE_FILL = VK_POLYGON_MODE_FILL,
    };

    enum CullMode {
        CULL_MODE_BACK = VK_CULL_MODE_BACK_BIT,
    };

    enum FrontFace {
        FRONT_FACE_CLOCKWISE = VK_FRONT_FACE_CLOCKWISE,
    };

    enum BlendFactor {
        BLEND_FACTOR_SRC_ALPHA = VK_BLEND_FACTOR_SRC_ALPHA,
        BLEND_FACTOR_ONE_MINUS_SRC_ALPHA = VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA,
        BLEND_FACTOR_ONE = VK_BLEND_FACTOR_ONE,
        BLEND_FACTOR_ZERO = VK_BLEND_FACTOR_ZERO,
    };

    enum BlendOp {
        BLEND_OP_ADD = VK_BLEND_OP_ADD,
    };

#elif METAL



#elif WEBGPU

    struct PipelineBackend {
        RenderPipelineHandle handle;
        BufferHandle vertexBuffer;
        BufferHandle indexBuffer;
        PipelineLayoutHandle layout;
    };

    enum PrimitiveTopology {
        TRIANGLE_LIST = WGPUPrimitiveTopology_TriangleList,
    };

    enum PolygonMode {
        // todo find alternative
        POLYGON_MODE_FILL = 0,
    };

    enum CullMode {
        CULL_MODE_BACK = WGPUCullMode_Back,
    };

    enum FrontFace {
        FRONT_FACE_CLOCKWISE = WGPUFrontFace_CCW,
    };

    enum BlendFactor {
        BLEND_FACTOR_SRC_ALPHA = WGPUBlendFactor_SrcAlpha,
        BLEND_FACTOR_ONE_MINUS_SRC_ALPHA = WGPUBlendFactor_OneMinusSrcAlpha,
        BLEND_FACTOR_ONE = WGPUBlendFactor_One,
        BLEND_FACTOR_ZERO = WGPUBlendFactor_Zero,
    };

    enum BlendOp {
        BLEND_OP_ADD = WGPUBlendOperation_Add,
    };

#endif

    struct Device;
    struct Shader;
    struct RenderPass;
    struct BindingLayout;

    struct PipelineCreateInfo {
        std::vector<VertexAttribute> vertexAttributes;
        bool instanced = false;
        uint32_t vertexBufferSlot;
        PrimitiveTopology primitiveTopology = TRIANGLE_LIST;
        std::vector<BindingLayout*> binding_layouts;
        Ptr<Shader> vertexShader;
        Ptr<Shader> fragmentShader;
        Ptr<Shader> geometryShader;
        Ptr<Shader> tessEvalShader;
        Ptr<Shader> tessControlShader;
        Viewport viewport;
        PolygonMode polygonMode = POLYGON_MODE_FILL;
        float lineWidth = 1.0f;
        CullMode cullMode = CULL_MODE_BACK;
        FrontFace frontFace = FRONT_FACE_CLOCKWISE;
        u8 sampleCount = 1;
        bool enableBlending = true;
        BlendFactor srcBlendFactor = BLEND_FACTOR_SRC_ALPHA;
        BlendFactor dstBlendFactor = BLEND_FACTOR_ONE_MINUS_SRC_ALPHA;
        BlendOp colorBlendOp = BLEND_OP_ADD;
        BlendFactor alphaSrcBlendFactor = BLEND_FACTOR_ONE;
        BlendFactor alphaDstBlendFactor = BLEND_FACTOR_ZERO;
        BlendOp alphaBlendOp = BLEND_OP_ADD;
        Ptr<RenderPass> renderPass;
    };

    struct Pipeline : PipelineBackend {
        Pipeline(const Device& device, const PipelineCreateInfo& create_info);
        ~Pipeline();
    };

}

#endif //STC_PIPELINE_HPP