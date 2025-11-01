//
// Created by cheerwizard on 21.10.25.
//

#include "../Binding.hpp"
#include "backend/Device.hpp"

namespace stc {

    BindingLayout::BindingLayout(
        const Device& device,
        const std::vector<Binding> &bindings
    ) : bindings(bindings) {
        u32 bindings_count = bindings.size();
        std::vector<VkDescriptorSetLayoutBinding> vkBindings;
        vkBindings.resize(bindings_count);

        for (u32 i = 0 ; i < bindings_count ; i++) {
            const auto& binding = bindings[i];
            vkBindings[i] = {
                .binding = binding.slot,
                .descriptorType = (VkDescriptorType) binding.type,
                .descriptorCount = 1,
                .stageFlags = (VkShaderStageFlags) binding.shader_stages,
                .pImmutableSamplers = nullptr,
            };
        }

        VkDescriptorSetLayoutCreateInfo layoutInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO,
            .bindingCount = bindings_count,
            .pBindings = vkBindings.data(),
        };

        New(device.handle, layoutInfo);
    }

    BindingLayout::~BindingLayout() {
        Delete();
    }

    BindingSetPool::BindingSetPool(const Device &device, BindingType type, u32 size) {
        VkDescriptorPoolSize poolSize = {
            .type = (VkDescriptorType) type,
            .descriptorCount = size
        };

        VkDescriptorPoolCreateInfo poolInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO,
            .maxSets = 1,
            .poolSizeCount = 1,
            .pPoolSizes = &poolSize,
        };

        New(device.handle, poolInfo);
    }

    BindingSetPool::~BindingSetPool() {
        Delete();
    }

    void BindingSetPool::reset() {
        CALL(vkResetDescriptorPool(device, handle, VK_DESCRIPTOR_POOL_CREATE_FREE_DESCRIPTOR_SET_BIT));
    }

    BindingSet::BindingSet(const BindingSetPool &pool, const BindingLayout &layout) {
        this->device = pool.device;
        this->pool = pool.handle;
        VkDescriptorSetAllocateInfo allocInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO,
            .descriptorPool = pool.handle,
            .descriptorSetCount = 1,
            .pSetLayouts = &layout.handle,
        };
        CALL(vkAllocateDescriptorSets(device, &allocInfo, &handle));
    }

    BindingSet::~BindingSet() {
        CALL(vkFreeDescriptorSets(device, pool, 1, &handle));
    }

}
