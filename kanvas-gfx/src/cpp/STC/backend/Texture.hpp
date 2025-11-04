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
        FORMAT_RGBA8 = VK_FORMAT_R8G8B8A8_SRGB,
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

    enum BorderColor {
        BORDER_COLOR_FLOAT_OPAQUE_BLACK = VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK,
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
        FORMAT_RGBA8 = WGPUTextureFormat_RGBA8UnormSrgb,
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

    enum BorderColor {
        // TODO find alternative
        BORDER_COLOR_FLOAT_OPAQUE_BLACK = 0,
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
        BorderColor borderColor = BORDER_COLOR_FLOAT_OPAQUE_BLACK;
        bool unnormalizedCoordinates = false;
        bool enableCompare = false;
        CompareOp compareOp = COMPARE_OP_ALWAYS;
        SamplerMipMapMode mipmapMode = MIPMAP_LINEAR;
        float mipLodBias = 0.0f;
        float minLod = 0.0f;
        float maxLod = 0.0f;
    };

    struct Device;

    struct Sampler : SamplerBackend {
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

    struct Texture : TextureBackend {
        TextureCreateInfo info;

        Texture(const Device& device, const TextureCreateInfo& info);
        ~Texture();

        void update();

    private:
        static constexpr auto TAG = "Texture";
    };

}

#endif //STC_TEXTURE_HPP