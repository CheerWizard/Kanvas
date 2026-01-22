//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_API_H
#define VK_API_H

#include <stdint.h>
#include <vulkan/vulkan.h>

typedef struct VkContext VkContext;
typedef struct VkDeviceQueue VkDeviceQueue;
typedef struct VkBufferResource VkBufferResource;
typedef struct VkSamplerResource VkSamplerResource;
typedef struct VkTextureResource VkTextureResource;
typedef struct VkFenceSync VkFenceSync;
typedef struct VkSemaphoreSync VkSemaphoreSync;
typedef struct VkShader VkShader;
typedef struct VkRenderTarget VkRenderTarget;
typedef struct VkPipe VkPipe;

typedef struct VkContextInfo {
    const char* application_name;
    const char* engine_name;
    void* native_window;
    uint32_t width;
    uint32_t height;
} VkContextInfo;

typedef struct VkSamplerInfo {
    VkFilter magFilter;
    VkFilter minFilter;
    VkSamplerAddressMode addressModeU;
    VkSamplerAddressMode addressModeV;
    VkSamplerAddressMode addressModeW;
    VkBool32 enableAnisotropy;
    float maxAnisotropy;
    VkBool32 unnormalizedCoordinates;
    VkBool32 enableCompare;
    VkCompareOp compareOp;
    VkSamplerMipmapMode mipmapMode;
    VkBorderColor borderColor;
    float mipLodBias;
    float minLod;
    float maxLod;
} VkSamplerInfo;

typedef struct VkTextureInfo {
    VkImageViewType type;
    VkMemoryPropertyFlagBits memoryType;
    uint32_t width;
    uint32_t height;
    uint32_t depth;
    VkFormat format;
    uint32_t mips;
    uint32_t baseMip;
} VkTextureInfo;

typedef struct VkBlend {
    VkBool32 enable;
    VkBlendFactor srcFactorColor;
    VkBlendFactor dstFactorColor;
    VkBlendOp blendOpColor;
    VkBlendFactor srcFactorAlpha;
    VkBlendFactor dstFactorAlpha;
    VkBlendOp blendOpAlpha;
} VkBlend;

typedef struct VkColorAttachment {
    VkImageView view;
    VkFormat format;
    uint32_t samples;
    float clearColor[4];
    VkBlend blend;
} VkColorAttachment;

typedef struct VkDepthAttachment {
    VkImageView view;
    VkBool32 enabled;
    VkFormat format;
    uint32_t samples;
    float depthClearValue;
    VkCompareOp depthCompareOp;
    uint32_t stencilClearValue;
    VkBool32 depthReadOnly;
    VkBool32 depthWriteEnabled;
    VkBool32 stencilReadOnly;
} VkDepthAttachment;

typedef struct VkStencilAttachment {
    VkImageView view;
    VkBool32 enabled;
    VkFormat format;
    uint32_t samples;
    float depthClearValue;
    VkCompareOp depthCompareOp;
    uint32_t stencilClearValue;
    VkBool32 depthReadOnly;
    VkBool32 depthWriteEnabled;
    VkBool32 stencilReadOnly;
} VkStencilAttachment;

typedef struct VkRenderTargetInfo {
    int x;
    int y;
    uint32_t width;
    uint32_t height;
    uint32_t depth;
    VkColorAttachment* colorAttachments;
    size_t colorAttachmentsCount;
    VkDepthAttachment* depthAttachment;
    VkStencilAttachment* stencilAttachment;
} VkRenderTargetInfo;

typedef struct VkShaderInfo {
    const char* name;
    const char* entryPoint;
    uint32_t* spirvCode;
    size_t spirvCodeSize;
} VkShaderInfo;

typedef enum VkAttributeFormat {
    VK_ATTRIBUTE_FORMAT_FLOAT = 1,
    VK_ATTRIBUTE_FORMAT_FLOAT2 = 2,
    VK_ATTRIBUTE_FORMAT_FLOAT3 = 3,
    VK_ATTRIBUTE_FORMAT_FLOAT4 = 4,
} VkAttributeFormat;

typedef enum VkAttributeType {
    VK_ATTRIBUTE_TYPE_PRIMITIVE = 1,
    VK_ATTRIBUTE_TYPE_VEC2 = 2,
    VK_ATTRIBUTE_TYPE_VEC3 = 3,
    VK_ATTRIBUTE_TYPE_VEC4 = 4,
    VK_ATTRIBUTE_TYPE_MAT2 = 8,
    VK_ATTRIBUTE_TYPE_MAT3 = 12,
    VK_ATTRIBUTE_TYPE_MAT4 = 16,
} VkAttributeType;

typedef struct VkAttribute {
    uint32_t location;
    VkAttributeType type;
    VkAttributeFormat format;
} VkAttribute;

typedef struct VkPipelineInfo {
    VkAttribute* vertexAttributes;
    size_t vertexAttributesCount;
    uint32_t vertexBufferSlot;
    VkBool32 instanced;
    VkPrimitiveTopology primitiveTopology;
    VkShader* vertexShader;
    VkShader* fragmentShader;
    VkShader* geometryShader;
    VkBuffer vertexBuffer;
    VkBuffer indexBuffer;
    VkViewport viewport;
    VkRect2D scissor;
    VkPolygonMode polygonMode;
    float lineWidth;
    VkCullModeFlagBits cullMode;
    VkFrontFace frontFace;
    uint32_t sampleCount;
    VkRenderTarget* renderTarget;
} VkPipelineInfo;

extern "C" {

    VkContext* VkContext_create(
        void* native_window,
        const char* application_name,
        const char* engine_name
    );
    void VkContext_destroy(VkContext* context);
    void VkContext_wait(VkContext* context);
    VkDeviceQueue* VkContext_getQueue(VkContext* context);

    void VkDeviceQueue_reset(VkDeviceQueue* device_queue);

    VkFenceSync* VkFenceSync_create(VkContext* context, int signaled);
    void VkFenceSync_destroy(VkFenceSync* fence);
    void VkFenceSync_wait(VkFenceSync* fence, uint64_t timeout = UINT64_MAX);
    void VkFenceSync_reset(VkFenceSync* fence);

    VkSemaphoreSync* VkSemaphoreSync_create(VkContext* context);
    void VkSemaphoreSync_destroy(VkSemaphoreSync* semaphore);

    inline VkSamplerInfo VkSamplerInfo_default() {
        VkSamplerInfo info = {};
        info.magFilter = VK_FILTER_LINEAR;
        info.minFilter = VK_FILTER_LINEAR;
        info.addressModeU = VK_SAMPLER_ADDRESS_MODE_REPEAT;
        info.addressModeV = VK_SAMPLER_ADDRESS_MODE_REPEAT;
        info.addressModeW = VK_SAMPLER_ADDRESS_MODE_REPEAT;
        info.enableAnisotropy = VK_TRUE;
        info.maxAnisotropy = 1.0f;
        info.unnormalizedCoordinates = VK_FALSE;
        info.enableCompare = VK_FALSE;
        info.compareOp = VK_COMPARE_OP_ALWAYS;
        info.mipmapMode = VK_SAMPLER_MIPMAP_MODE_LINEAR;
        info.borderColor = VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK;
        info.mipLodBias = 0.0f;
        info.minLod = 0.0f;
        info.maxLod = 0.0f;
        return info;
    }

    inline VkTextureInfo VkTextureInfo_default() {
        VkTextureInfo info = {};
        info.type = VK_IMAGE_VIEW_TYPE_2D;
        info.memoryType = VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
        info.width = 0;
        info.height = 0;
        info.depth = 1;
        info.format = VK_FORMAT_R8G8B8A8_SRGB;
        info.mips = 1;
        info.baseMip = 1;
        return info;
    }

    inline VkBlend VkBlend_default() {
        VkBlend blend = {};
        blend.enable = VK_FALSE;
        blend.srcFactorColor = VK_BLEND_FACTOR_ONE;
        blend.dstFactorColor = VK_BLEND_FACTOR_ZERO;
        blend.blendOpColor = VK_BLEND_OP_ADD;
        blend.srcFactorAlpha = VK_BLEND_FACTOR_ONE;
        blend.dstFactorAlpha = VK_BLEND_FACTOR_ZERO;
        blend.blendOpAlpha = VK_BLEND_OP_ADD;
        return blend;
    }

    inline VkColorAttachment VkColorAttachment_default() {
        VkColorAttachment attachment = {};
        attachment.view = VK_NULL_HANDLE;
        attachment.format;
        attachment.samples = 1;
        attachment.clearColor[0] = 0;
        attachment.clearColor[1] = 0;
        attachment.clearColor[2] = 0;
        attachment.clearColor[3] = 1;
        attachment.colorBlendSrcFactor;
        attachment.colorBlendDstFactor;
        attachment.colorBlendOp;
        attachment.alphaBlendSrcFactor;
        attachment.alphaBlendDstFactor;
        attachment.alphaBlendOp;
        return attachment;
    }

    inline VkDepthAttachment VkDepthAttachment_default() {
        VkDepthAttachment attachment = {};
        attachment.view = VK_NULL_HANDLE;
        attachment.enabled = VK_FALSE;
        attachment.format;
        attachment.samples = 1;
        attachment.depthClearValue = 1.0f;
        attachment.depthCompareOp = VK_COMPARE_OP_LESS;
        attachment.stencilClearValue = 1.0;
        attachment.depthReadOnly = VK_FALSE;
        attachment.depthWriteEnabled = VK_FALSE;
        attachment.stencilReadOnly = VK_FALSE;
        return attachment;
    }

    inline VkStencilAttachment VkStencilAttachment_default() {
        VkStencilAttachment attachment = {};
        attachment.view = VK_NULL_HANDLE;
        attachment.enabled = VK_FALSE;
        attachment.format;
        attachment.samples = 1;
        attachment.depthClearValue = 1.0f;
        attachment.depthCompareOp = VK_COMPARE_OP_LESS;
        attachment.stencilClearValue = 1.0;
        attachment.depthReadOnly = VK_FALSE;
        attachment.depthWriteEnabled = VK_FALSE;
        attachment.stencilReadOnly = VK_FALSE;
        return attachment;
    }

    inline VkRenderTargetInfo VkRenderTargetInfo_default() {
        VkRenderTargetInfo info = {};
        info.x = 0;
        info.y = 0;
        info.width;
        info.height;
        info.depth = 1;
        info.colorAttachments = VK_NULL_HANDLE;
        info.colorAttachmentsCount = 0;
        info.depthAttachment = VK_NULL_HANDLE;
        info.stencilAttachment = VK_NULL_HANDLE;
        return info;
    }

    inline VkShaderInfo VkShaderInfo_default() {
        VkShaderInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.entryPoint = "main";
        info.spirvCode = VK_NULL_HANDLE;
        info.spirvCodeSize = 0;
        return info;
    }

    inline VkPipelineInfo VkPipelineInfo_default() {
        VkPipelineInfo info = {};
        info.vertexAttributes = VK_NULL_HANDLE;
        info.vertexAttributesCount = 0;
        info.vertexBufferSlot = 0;
        info.primitiveTopology = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
        info.vertexShader = VK_NULL_HANDLE;
        info.fragmentShader = VK_NULL_HANDLE;
        info.geometryShader = VK_NULL_HANDLE;
        info.vertexBuffer = VK_NULL_HANDLE;
        info.indexBuffer = VK_NULL_HANDLE;
        info.viewport;
        info.scissor.offset.x = (int) info.viewport.x;
        info.scissor.offset.y = (int) info.viewport.y;
        info.scissor.extent.width = (uint32_t) info.viewport.width;
        info.scissor.extent.height = (uint32_t) info.viewport.height;
        info.polygonMode = VK_POLYGON_MODE_FILL;
        info.lineWidth = 1.0f;
        info.cullMode = VK_CULL_MODE_BACK_BIT;
        info.frontFace = VK_FRONT_FACE_CLOCKWISE;
        info.sampleCount = 1;
        info.renderTarget = VK_NULL_HANDLE;
        return info;
    }

}

#endif //VK_API_H
