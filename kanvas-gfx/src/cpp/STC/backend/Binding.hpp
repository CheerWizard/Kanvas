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

    struct BindingLayoutBackend {
        DescriptorSetLayoutHandle layout;
        std::unordered_map<VkDescriptorType, DescriptorPoolHandle> pools;
        VkDescriptorSet set = null;
    };

#elif METAL



#elif WEBGPU

    enum BindingType {
        BINDING_TYPE_UNIFORM_BUFFER = 0,
        BINDING_TYPE_STORAGE_BUFFER,
        BINDING_TYPE_TEXTURE,
        BINDING_TYPE_SAMPLER,
    };

    struct BindingLayoutBackend {
        BindGroupLayoutHandle layout;
        BindGroupHandle group;
    };

#endif

    struct Binding {
        BindingType type;
        u32 slot;
        u32 shader_stages;
        void* resource = nullptr;
    };

    struct Device;

    struct BindingLayout : BindingLayoutBackend {
        std::vector<Binding> bindings;

        BindingLayout(const Device& device, const std::vector<Binding>& bindings);
        ~BindingLayout();

        void resetPool();
    };

}

#endif //STC_BINDING_HPP