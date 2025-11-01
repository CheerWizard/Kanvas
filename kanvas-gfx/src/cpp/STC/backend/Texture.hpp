//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_TEXTURE_HPP
#define STC_TEXTURE_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

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
        COMPARE_OP_ALWAYS = VK_COMPARE_OP_ALWAYS,
    };

    enum BorderColor {
        BORDER_COLOR_FLOAT_OPAQUE_BLACK = VK_BORDER_COLOR_FLOAT_OPAQUE_BLACK,
    };

    struct SamplerBackend : SamplerHandle {};

    struct TextureBackend : ImageHandle {
        ImageViewHandle imageView;
        VmaAllocation allocation = {};
    };

#elif METAL



#elif WEBGPU

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
        COMPARE_OP_ALWAYS = WGPUCompareFunction_Always,
    };

    enum BorderColor {
        // TODO find alternative
        BORDER_COLOR_FLOAT_OPAQUE_BLACK = 0,
    };

    enum TextureType {
        TEXTURE_2D = WGPUTextureViewDimension_2D,
        TEXTURE_3D = WGPUTextureViewDimension_3D,
        TEXTURE_CUBE_MAP = WGPUTextureViewDimension_Cube,
        TEXTURE_ARRAY = WGPUTextureViewDimension_2DArray,
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
        CompareOp compareOperation = COMPARE_OP_ALWAYS;
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