//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_PIPELINE_HPP
#define STC_PIPELINE_HPP

#include "Shader.hpp"
#include "RenderTarget.hpp"
#include "Buffer.hpp"

#include "../frontend/Vertex.hpp"
#include "../frontend/Viewport.hpp"

namespace stc {

#ifdef VK

    struct PipelineBackend {
        VkPipeline handle = null;
        DeviceHandle device;
        PipelineLayoutHandle layout;
    };

    enum PrimitiveTopology {
        PRIMITIVE_TOPOLOGY_TRIANGLE_LIST = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST,
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

#elif METAL



#elif WEBGPU

    struct PipelineBackend {
        RenderPipelineHandle handle;
        BufferHandle vertexBuffer;
        BufferHandle indexBuffer;
        PipelineLayoutHandle layout;
    };

    enum PrimitiveTopology {
        PRIMITIVE_TOPOLOGY_TRIANGLE_LIST = WGPUPrimitiveTopology_TriangleList,
    };

    enum PolygonMode {
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

    struct PipelineCreateInfo {
        std::vector<Attribute> vertexAttributes;
        bool instanced = false;
        uint32_t vertexBufferSlot;
        PrimitiveTopology primitiveTopology = PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
        std::vector<BindingLayout*> binding_layouts;
        Ptr<Shader> vertexShader;
        Ptr<Shader> fragmentShader;
        Ptr<Shader> geometryShader;
        Ptr<Shader> tessEvalShader;
        Ptr<Shader> tessControlShader;
        Ptr<Buffer> vertexBuffer;
        Ptr<Buffer> indexBuffer;
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
        Ptr<RenderTarget> render_target;
    };

    struct Pipeline : PipelineBackend {
        Pipeline(const Device& device, const PipelineCreateInfo& create_info);
        ~Pipeline();
    };

}

#endif //STC_PIPELINE_HPP