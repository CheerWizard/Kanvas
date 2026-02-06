//
// Created by Vitalii Andrusyshyn on 23.01.2026.
//

#ifndef KVK_VKINFO_H
#define KVK_VKINFO_H

#include <stdint.h>
#include <vulkan/vulkan_core.h>

using u8 = uint8_t;
using u32 = uint32_t;

typedef struct VkBufferResource VkBufferResource;
typedef struct VkTextureResource VkTextureResource;
typedef struct VkSamplerResource VkSamplerResource;
typedef struct VkRenderTarget VkRenderTarget;
typedef struct VkShader VkShader;
typedef struct VkBinding VkBinding;
typedef struct VkBindingLayout VkBindingLayout;

typedef struct VkContextInfo {
    const char* application_name;
    const char* engine_name;
    u32 width;
    u32 height;
    u32 frameCount;
} VkContextInfo;

typedef struct VkBufferInfo {
    const char* name;
    VkBindingLayout* binding_layout;
    VkBinding* binding;
    VkMemoryPropertyFlagBits memoryType;
    u32 usages;
    size_t size;
    u8 isStatic;
} VkBufferInfo;

typedef struct VkSamplerInfo {
    const char* name;
    VkBindingLayout* binding_layout;
    VkBinding* binding;
    VkFilter magFilter;
    VkFilter minFilter;
    VkSamplerAddressMode addressModeU;
    VkSamplerAddressMode addressModeV;
    VkSamplerAddressMode addressModeW;
    u8 enableAnisotropy;
    float maxAnisotropy;
    u8 unnormalizedCoordinates;
    u8 enableCompare;
    VkCompareOp compareOp;
    VkSamplerMipmapMode mipmapMode;
    VkBorderColor borderColor;
    float mipLodBias;
    float minLod;
    float maxLod;
} VkSamplerInfo;

typedef struct VkTextureInfo {
    const char* name;
    VkBindingLayout* binding_layout;
    VkBinding* binding;
    VkImageViewType type;
    VkMemoryPropertyFlagBits memoryType;
    u32 width;
    u32 height;
    u32 depth;
    VkFormat format;
    u32 mips;
    u32 baseMip;
    u32 samples;
    u8 isStatic;
} VkTextureInfo;

typedef struct VkBlend {
    u8 enable;
    VkBlendFactor srcFactorColor;
    VkBlendFactor dstFactorColor;
    VkBlendOp blendOpColor;
    VkBlendFactor srcFactorAlpha;
    VkBlendFactor dstFactorAlpha;
    VkBlendOp blendOpAlpha;
} VkBlend;

typedef struct VkColorAttachment {
    VkTextureResource* texture;
    float clearColor[4];
    VkBlend blend;
} VkColorAttachment;

typedef struct VkDepthAttachment {
    VkTextureResource* texture;
    u8 enabled;
    float depthClearValue;
    VkCompareOp depthCompareOp;
    u8 depthReadOnly;
    u8 depthWriteEnabled;
} VkDepthAttachment;

typedef struct VkStencilAttachment {
    VkTextureResource* texture;
    u8 enabled;
    u32 stencilClearValue;
    u8 stencilReadOnly;
} VkStencilAttachment;

typedef struct VkRenderTargetInfo {
    const char* name;
    int x;
    int y;
    u32 width;
    u32 height;
    u32 depth;
    VkColorAttachment* colorAttachments;
    size_t colorAttachmentsCount;
    VkDepthAttachment* depthAttachment;
    VkStencilAttachment* stencilAttachment;
} VkRenderTargetInfo;

typedef struct VkShaderInfo {
    const char* name;
    const char* entryPoint;
    u32* spirvCode;
    size_t spirvCodeSize;
    VkBindingLayout** binding_layouts;
    size_t binding_layouts_count;
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
    u32 location;
    VkAttributeType type;
    VkAttributeFormat format;
} VkAttribute;

enum VkBindingType {
    VK_BINDING_TYPE_UNIFORM_BUFFER = VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER,
    VK_BINDING_TYPE_STORAGE_BUFFER = VK_DESCRIPTOR_TYPE_STORAGE_BUFFER,
    VK_BINDING_TYPE_UNIFORM_BUFFER_DYNAMIC = VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC,
    VK_BINDING_TYPE_STORAGE_BUFFER_DYNAMIC = VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC,
    VK_BINDING_TYPE_TEXTURE = VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE,
    VK_BINDING_TYPE_SAMPLER = VK_DESCRIPTOR_TYPE_SAMPLER,
    VK_BINDING_TYPE_TEXTURE_SAMPLER = VK_DESCRIPTOR_TYPE_COMBINED_IMAGE_SAMPLER,
};

typedef struct VkBinding {
    VkBindingType type;
    u32 shader_stages;
    u32 set;
    u32 binding;
    u32 count;
    void* resource;
} VkBinding;

typedef struct VkBindingInfo {
    const char* name;
    VkBinding* bindings;
    size_t bindingsCount;
} VkBindingInfo;

typedef struct VkPipelineInfo {
    const char* name;
    VkAttribute* vertexAttributes;
    size_t vertexAttributesCount;
    u32 vertexBufferSlot;
    u8 instanced;
    VkPrimitiveTopology primitiveTopology;
    VkShader* vertexShader;
    VkShader* fragmentShader;
    VkShader* geometryShader;
    VkBufferResource* vertexBuffer;
    VkBufferResource* indexBuffer;
    float viewportX;
    float viewportY;
    float viewportWidth;
    float viewportHeight;
    float viewportMinDepth;
    float viewportMaxDepth;
    int scissorX;
    int scissorY;
    u32 scissorWidth;
    u32 scissorHeight;
    VkPolygonMode polygonMode;
    float lineWidth;
    VkCullModeFlagBits cullMode;
    VkFrontFace frontFace;
    u32 sampleCount;
    VkRenderTarget* renderTarget;
} VkPipeInfo;

typedef struct VkComputePipeInfo {
    const char* name;
    VkShader* computeShader;
} VkComputePipeInfo;

enum VkMemoryBarrierAccess {
    READ,
    WRITE
};

extern "C" {

    inline VkContextInfo VkContextInfo_default() {
        VkContextInfo info = {};
        info.application_name = "VkApplication";
        info.engine_name = "VkEngine";
        info.width = 800;
        info.height = 600;
        info.frameCount = 1;
        return info;
    }

    inline VkBufferInfo VkBufferInfo_default() {
        VkBufferInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.binding_layout = VK_NULL_HANDLE;
        info.binding = VK_NULL_HANDLE;
        info.isStatic = VK_FALSE;
        return info;
    }

    inline VkSamplerInfo VkSamplerInfo_default() {
        VkSamplerInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.binding_layout = VK_NULL_HANDLE;
        info.binding = VK_NULL_HANDLE;
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
        info.name = VK_NULL_HANDLE;
        info.binding_layout = VK_NULL_HANDLE;
        info.binding = VK_NULL_HANDLE;
        info.type = VK_IMAGE_VIEW_TYPE_2D;
        info.memoryType = VK_MEMORY_PROPERTY_HOST_VISIBLE_BIT;
        info.width = 0;
        info.height = 0;
        info.depth = 1;
        info.format = VK_FORMAT_R8G8B8A8_SRGB;
        info.mips = 1;
        info.baseMip = 1;
        info.samples = 1;
        info.isStatic = VK_TRUE;
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
        attachment.texture = VK_NULL_HANDLE;
        attachment.clearColor[0] = 0;
        attachment.clearColor[1] = 0;
        attachment.clearColor[2] = 0;
        attachment.clearColor[3] = 1;
        attachment.blend = VkBlend_default();
        return attachment;
    }

    inline VkDepthAttachment VkDepthAttachment_default() {
        VkDepthAttachment attachment = {};
        attachment.texture = VK_NULL_HANDLE;
        attachment.enabled = VK_FALSE;
        attachment.depthClearValue = 1.0f;
        attachment.depthCompareOp = VK_COMPARE_OP_LESS;
        attachment.depthReadOnly = VK_FALSE;
        attachment.depthWriteEnabled = VK_FALSE;
        return attachment;
    }

    inline VkStencilAttachment VkStencilAttachment_default() {
        VkStencilAttachment attachment = {};
        attachment.texture = VK_NULL_HANDLE;
        attachment.enabled = VK_FALSE;
        attachment.stencilClearValue = 1.0;
        attachment.stencilReadOnly = VK_FALSE;
        return attachment;
    }

    inline VkRenderTargetInfo VkRenderTargetInfo_default() {
        VkRenderTargetInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.x = 0;
        info.y = 0;
        info.width = 0;
        info.height = 0;
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
        info.binding_layouts = VK_NULL_HANDLE;
        info.binding_layouts_count = 0;
        return info;
    }

    inline VkAttribute VkAttribute_default() {
        VkAttribute attribute = {};
        attribute.type = VK_ATTRIBUTE_TYPE_VEC3;
        attribute.format = VK_ATTRIBUTE_FORMAT_FLOAT;
        attribute.location = 0;
        return attribute;
    }

    inline VkBinding VkBinding_default() {
        VkBinding binding = {};
        binding.set = 0;
        binding.binding = 0;
        binding.shader_stages = 0;
        binding.resource = VK_NULL_HANDLE;
        return binding;
    }

    inline VkBindingInfo VkBindingInfo_default() {
        VkBindingInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.bindings = VK_NULL_HANDLE;
        info.bindingsCount = 0;
        return info;
    }

    inline VkPipeInfo VkPipelineInfo_default() {
        VkPipeInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.vertexAttributes = VK_NULL_HANDLE;
        info.vertexAttributesCount = 0;
        info.vertexBufferSlot = 0;
        info.primitiveTopology = VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST;
        info.vertexShader = VK_NULL_HANDLE;
        info.fragmentShader = VK_NULL_HANDLE;
        info.geometryShader = VK_NULL_HANDLE;
        info.vertexBuffer = VK_NULL_HANDLE;
        info.indexBuffer = VK_NULL_HANDLE;
        info.viewportX = 0;
        info.viewportY = 0;
        info.viewportWidth = 0;
        info.viewportHeight = 0;
        info.viewportMinDepth = 0;
        info.viewportMaxDepth = 0;
        info.scissorX = 0;
        info.scissorY = 0;
        info.scissorWidth = 0;
        info.scissorHeight = 0;
        info.polygonMode = VK_POLYGON_MODE_FILL;
        info.lineWidth = 1.0f;
        info.cullMode = VK_CULL_MODE_BACK_BIT;
        info.frontFace = VK_FRONT_FACE_CLOCKWISE;
        info.sampleCount = 1;
        info.renderTarget = VK_NULL_HANDLE;
        return info;
    }

    inline VkComputePipeInfo VkComputePipeInfo_default() {
        VkComputePipeInfo info = {};
        info.name = VK_NULL_HANDLE;
        info.computeShader = VK_NULL_HANDLE;
        return info;
    }

}

#endif //KVK_VKINFO_H