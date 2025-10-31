//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_BINDING_HPP
#define STC_BINDING_HPP

#include "backend/Shader.hpp"

namespace stc {

#ifdef VK

    enum BindingType {
        BINDING_TYPE_UNIFORM_BUFFER = VK_DESCRIPTOR_TYPE_UNIFORM_BUFFER_DYNAMIC,
        BINDING_TYPE_STORAGE_BUFFER = VK_DESCRIPTOR_TYPE_STORAGE_BUFFER_DYNAMIC,
        BINDING_TYPE_TEXTURE = VK_DESCRIPTOR_TYPE_SAMPLED_IMAGE,
        BINDING_TYPE_SAMPLER = VK_DESCRIPTOR_TYPE_SAMPLER,
    };

    struct BindingLayoutBackend : DescriptorSetLayoutHandle {};

    struct BindingSetBackend {
        VkDevice device = null;
        VkDescriptorPool pool = null;
        VkDescriptorSet handle = null;
    };

    struct BindingSetPoolBackend : DescriptorPoolHandle {};

#elif METAL



#elif WEBGPU

    enum BindingType {
        BINDING_TYPE_UNIFORM_BUFFER = WGPUBufferBindingType_Uniform,
        BINDING_TYPE_STORAGE_BUFFER = WGPUBufferBindingType_Storage,
        BINDING_TYPE_TEXTURE = WGPUBufferBindingType_Undefined,
        BINDING_TYPE_SAMPLER = WGPUBufferBindingType_Undefined,
    };

    struct BindingLayoutBackend : BindGroupLayoutHandle {};

    struct BindingSetBackend : BindGroupHandle {};

    struct BindingSetPoolBackend {};

#endif

    struct Binding {
        BindingType type;
        u32 slot;
        u32 shader_stages;
    };

    struct Device;

    struct BindingLayout : BindingLayoutBackend {
        std::vector<Binding> bindings;

        BindingLayout(const Device& device, const std::vector<Binding>& bindings);
        ~BindingLayout();
    };

    struct BindingSetPool : BindingSetPoolBackend {
        BindingSetPool(const Device& device, BindingType type, u32 size);
        ~BindingSetPool();
        void reset();
    };

    struct BindingSet : BindingSetBackend {
        BindingSet(const BindingSetPool& pool, const BindingLayout& layout);
        ~BindingSet();
    };

}

#endif //STC_BINDING_HPP