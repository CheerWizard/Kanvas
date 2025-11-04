//
// Created by cheerwizard on 18.10.25.
//

#include "../Texture.hpp"
#include "backend/Device.hpp"

namespace stc {

    Sampler::Sampler(const Device &device, const SamplerCreateInfo &create_info) {
        New(device.handle, WGPUSamplerDescriptor {
            .label = "Sampler",
            .addressModeU = (WGPUAddressMode) create_info.addressModeU,
            .addressModeV = (WGPUAddressMode) create_info.addressModeV,
            .addressModeW = (WGPUAddressMode) create_info.addressModeW,
            .magFilter = (WGPUFilterMode) create_info.magFilter,
            .minFilter = (WGPUFilterMode) create_info.minFilter,
            .mipmapFilter = (WGPUFilterMode) create_info.mipmapMode,
            .lodMinClamp = create_info.minLod,
            .lodMaxClamp = create_info.maxLod,
            .compare = (WGPUCompareFunction) create_info.compareOp,
            .maxAnisotropy = (u16) create_info.maxAnisotropy,
        });
    }

    Sampler::~Sampler() {
        Delete();
    }

    Texture::Texture(const Device& device, const TextureCreateInfo &create_info) : info(create_info) {
        New(device.handle, WGPUTextureDescriptor {
            .nextInChain = nullptr,
            .label = "Texture",
            .size.width = create_info.width,
            .size.height = create_info.height,
            .size.depthOrArrayLayers = create_info.depth,
            .mipLevelCount = create_info.mips,
            .sampleCount = 1,
            .dimension = (WGPUTextureDimension) create_info.type,
            .format = (WGPUTextureFormat) create_info.format,
            .usage = WGPUTextureUsage_CopyDst | WGPUTextureUsage_TextureBinding,
        });

        view.New(device.handle, WGPUTextureViewDescriptor {
            .nextInChain = nullptr,
            .label = "TextureView",
            .format = (WGPUTextureFormat) create_info.format,
            .dimension = (WGPUTextureDimension) create_info.type,
            .baseMipLevel = create_info.baseMip,
            .mipLevelCount = create_info.mips,
            .baseArrayLayer = 0,
            .arrayLayerCount = create_info.depth,
        });
    }

    Texture::~Texture() {
        view.Delete();
        Delete();
    }

    void Texture::update() {
        WGPUImageCopyTexture copy = {};
        copy.texture = texture;
        copy.mipLevel = 0;
        copy.origin = {0,0,0};
        copy.aspect = WGPUTextureAspect_All;

        wgpuQueueWriteTexture(queue, &copy, data, dataSize, &dataLayout, &texDesc.size);
    }

    void* Texture::map() {
        // no-op
        return nullptr;
    }

    void Texture::unmap() {
        // no-op
    }

}