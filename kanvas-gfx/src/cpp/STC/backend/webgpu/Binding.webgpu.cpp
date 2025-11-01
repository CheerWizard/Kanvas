//
// Created by cheerwizard on 21.10.25.
//

#include "../Binding.hpp"
#include "backend/Device.hpp"

#include "backend/Buffer.hpp"
#include "backend/Texture.hpp"

namespace stc {

    BindingLayout::BindingLayout(
        const Device& device,
        const std::vector<Binding> &bindings
    ) : bindings(bindings) {
        u32 bindings_count = bindings.size();

        std::vector<WGPUBindGroupLayoutEntry> wgpu_bindings;
        wgpu_bindings.resize(bindings_count);

        std::vector<WGPUBindGroupEntry> wgpu_entries;
        wgpu_entries.resize(bindings_count);

        for (u32 i = 0 ; i < bindings_count ; i++) {
            const auto& binding = bindings[i];
            auto& wgpu_binding = wgpu_bindings[i];
            auto& wgpu_entry = wgpu_entries[i];

            wgpu_binding.binding = binding.slot;
            wgpu_binding.visibility = binding.shader_stages;

            wgpu_entry.binding = binding.slot;

            switch (binding.type) {

                case BINDING_TYPE_UNIFORM_BUFFER: {
                    wgpu_binding.buffer.type = WGPUBufferBindingType_Uniform;
                    wgpu_binding.buffer.hasDynamicOffset = false;
                    wgpu_binding.buffer.minBindingSize = 0;

                    Buffer* buffer = (Buffer*) binding.resource;
                    wgpu_entry.buffer = buffer->handle;
                    wgpu_entry.offset = 0;
                    wgpu_entry.size = buffer->size;
                } break;

                case BINDING_TYPE_STORAGE_BUFFER: {
                    wgpu_binding.buffer.type = WGPUBufferBindingType_Storage;
                    wgpu_binding.buffer.hasDynamicOffset = false;
                    wgpu_binding.buffer.minBindingSize = 0;

                    Buffer* buffer = (Buffer*) binding.resource;
                    wgpu_entry.buffer = buffer->handle;
                    wgpu_entry.offset = 0;
                    wgpu_entry.size = buffer->size;
                } break;

                case BINDING_TYPE_SAMPLER: {
                    wgpu_binding.sampler.type = WGPUSamplerBindingType_Filtering;

                    Sampler* sampler = (Sampler*) binding.resource;
                    wgpu_entry.sampler = sampler->handle;
                } break;

                case BINDING_TYPE_TEXTURE: {
                    wgpu_binding.texture.sampleType = WGPUTextureSampleType_Float;
                    wgpu_binding.texture.viewDimension = WGPUTextureViewDimension_2D;
                    wgpu_binding.texture.multisampled = false;

                    Texture* texture = (Texture*) binding.resource;
                    wgpu_entry.textureView = texture->view.handle;
                } break;

                default:
                    break;
            }
        }

        layout.New(device.handle, WGPUBindGroupLayoutDescriptor {
            .entryCount = (u32) wgpu_bindings.size(),
            .entries = wgpu_bindings.data(),
        });

        group.New(device.handle, WGPUBindGroupDescriptor {
            .layout = layout.handle,
            .entryCount = (u32) wgpu_entries.size(),
            .entries = wgpu_entries.data(),
        });
    }

    BindingLayout::~BindingLayout() {
        layout.Delete();
        group.Delete();
    }

}
