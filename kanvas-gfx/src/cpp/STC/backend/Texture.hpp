//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_TEXTURE_HPP
#define STC_TEXTURE_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    enum TextureType {
        TEXTURE_2D = VK_IMAGE_VIEW_TYPE_2D,
        TEXTURE_3D = VK_IMAGE_VIEW_TYPE_3D,
        TEXTURE_CUBE_MAP = VK_IMAGE_VIEW_TYPE_CUBE,
        TEXTURE_ARRAY = VK_IMAGE_VIEW_TYPE_2D_ARRAY,
    };

    enum TextureFormat {
        FORMAT_R8 = VK_FORMAT_R8_SRGB,
        FORMAT_RG8 = VK_FORMAT_R8G8_SRGB,
        FORMAT_RGB8 = VK_FORMAT_R8G8B8_SRGB,
        FORMAT_RGBA8 = VK_FORMAT_R8G8B8A8_SRGB,

        FORMAT_R16 = VK_FORMAT_R16_SFLOAT,
        FORMAT_RG16 = VK_FORMAT_R16G16_SFLOAT,
        FORMAT_RGB16 = VK_FORMAT_R16G16B16_SFLOAT,
        FORMAT_RGBA16 = VK_FORMAT_R16G16B16A16_SFLOAT,

        FORMAT_R32 = VK_FORMAT_R32_SFLOAT,
        FORMAT_RG32 = VK_FORMAT_R32G32_SFLOAT,
        FORMAT_RGB32 = VK_FORMAT_R32G32B32_SFLOAT,
        FORMAT_RGBA32 = VK_FORMAT_R32G32B32A32_SFLOAT,

        FORMAT_DEPTH16 = VK_FORMAT_D16_UNORM,
        FORMAT_DEPTH24 = VK_FORMAT_D24_UNORM_S8_UINT,
        FORMAT_DEPTH32 = VK_FORMAT_D32_SFLOAT,

        FORMAT_STENCIL = VK_FORMAT_D16_UNORM,
    };

    enum SamplerFilter {
        FILTER_LINEAR = VK_FILTER_LINEAR,
    };

    enum SamplerMode {
        MODE_REPEAT = VK_SAMPLER_ADDRESS_MODE_REPEAT,
    };

    enum SamplerMipMapMode {
        MIPMAP_LINEAR = VK_SAMPLER_MIPMAP_MODE_LINEAR,
    };

    enum CompareOp {
        COMPARE_OP_NONE = -1,
        COMPARE_OP_ALWAYS = VK_COMPARE_OP_ALWAYS,
        COMPARE_OP_NEVER = VK_COMPARE_OP_NEVER,
        COMPARE_OP_LESS = VK_COMPARE_OP_LESS,
        COMPARE_OP_LESS_EQUAL = VK_COMPARE_OP_LESS_OR_EQUAL,
        COMPARE_OP_GREATER = VK_COMPARE_OP_GREATER,
        COMPARE_OP_GREATER_EQUAL = VK_COMPARE_OP_GREATER_OR_EQUAL,
        COMPARE_OP_EQUAL = VK_COMPARE_OP_EQUAL,
        COMPARE_OP_NOT_EQUAL = VK_COMPARE_OP_NOT_EQUAL,
    };

    struct SamplerBackend : SamplerHandle {};

    struct TextureBackend : ImageHandle {
        ImageViewHandle view;
        VmaAllocation allocation = {};
    };

#elif METAL



#elif WEBGPU

    enum TextureType {
        TEXTURE_2D = WGPUTextureViewDimension_2D,
        TEXTURE_3D = WGPUTextureViewDimension_3D,
        TEXTURE_CUBE_MAP = WGPUTextureViewDimension_Cube,
        TEXTURE_ARRAY = WGPUTextureViewDimension_2DArray,
    };

    enum TextureFormat {
        FORMAT_R8 = WGPUTextureFormat_R8Unorm,
        FORMAT_RG8 = WGPUTextureFormat_RG8Unorm,
        FORMAT_RGB8 = WGPUTextureFormat_Undefined,
        FORMAT_RGBA8 = WGPUTextureFormat_RGBA8Unorm,

        FORMAT_R16 = WGPUTextureFormat_R16Float,
        FORMAT_RG16 = WGPUTextureFormat_RG16Float,
        FORMAT_RGB16 = WGPUTextureFormat_Undefined,
        FORMAT_RGBA16 = WGPUTextureFormat_RGBA16Float,

        FORMAT_R32 = WGPUTextureFormat_R32Float,
        FORMAT_RG32 = WGPUTextureFormat_RG32Float,
        FORMAT_RGB32 = WGPUTextureFormat_Undefined,
        FORMAT_RGBA32 = WGPUTextureFormat_RGBA32Float,

        FORMAT_DEPTH16 = WGPUTextureFormat_Depth16Unorm,
        FORMAT_DEPTH24 = WGPUTextureFormat_Depth24UnormStencil8,
        FORMAT_DEPTH32 = WGPUTextureFormat_Depth32Float,

        FORMAT_STENCIL = WGPUTextureFormat_Stencil8,
    };

    enum SamplerFilter {
        FILTER_LINEAR = WGPUFilterMode_Linear,
    };

    enum SamplerMode {
        MODE_REPEAT = WGPUAddressMode_Repeat,
    };

    enum SamplerMipMapMode {
        MIPMAP_LINEAR = WGPUFilterMode_Linear,
    };

    enum CompareOp {
        COMPARE_OP_NONE = WGPUCompareFunction_Undefined,
        COMPARE_OP_ALWAYS = WGPUCompareFunction_Always,
        COMPARE_OP_NEVER = WGPUCompareFunction_Never,
        COMPARE_OP_LESS = WGPUCompareFunction_Less,
        COMPARE_OP_LESS_EQUAL = WGPUCompareFunction_LessEqual,
        COMPARE_OP_GREATER = WGPUCompareFunction_Greater,
        COMPARE_OP_GREATER_EQUAL = WGPUCompareFunction_GreaterEqual,
        COMPARE_OP_EQUAL = WGPUCompareFunction_Equal,
        COMPARE_OP_NOT_EQUAL = WGPUCompareFunction_NotEqual,
    };

    struct SamplerBackend : SamplerHandle {};

    struct TextureBackend : TextureHandle {
        TextureViewHandle view;
    };

#endif

    struct SamplerCreateInfo {
        SamplerFilter magFilter = FILTER_LINEAR;
        SamplerFilter minFilter = FILTER_LINEAR;
        SamplerMode addressModeU = MODE_REPEAT;
        SamplerMode addressModeV = MODE_REPEAT;
        SamplerMode addressModeW = MODE_REPEAT;
        bool enableAnisotropy = true;
        float maxAnisotropy = 1.0f;
        bool unnormalizedCoordinates = false;
        bool enableCompare = false;
        CompareOp compareOp = COMPARE_OP_ALWAYS;
        SamplerMipMapMode mipmapMode = MIPMAP_LINEAR;
        float mipLodBias = 0.0f;
        float minLod = 0.0f;
        float maxLod = 0.0f;
    };

    struct Device;

    struct Sampler : SamplerBackend, Resource {
        Sampler(const Device& device, const SamplerCreateInfo& details);
        ~Sampler();

    private:
        static constexpr auto TAG = "Sampler";
    };

    struct TextureCreateInfo {
        TextureType type = TEXTURE_2D;
        MemoryType memoryType = MEMORY_TYPE_HOST;
        u32 width = 0;
        u32 height = 0;
        u32 depth = 1;
        TextureFormat format = FORMAT_RGBA8;
        u32 mips = 1;
        u32 baseMip = 1;
    };

    struct Texture : TextureBackend, Resource {
        TextureCreateInfo info;

        Texture(const Device& device, const TextureCreateInfo& info);
        ~Texture();

        void update();

        operator TextureHandle() const {
            return handle;
        }

    private:
        static constexpr auto TAG = "Texture";
    };

}

#endif //STC_TEXTURE_HPP