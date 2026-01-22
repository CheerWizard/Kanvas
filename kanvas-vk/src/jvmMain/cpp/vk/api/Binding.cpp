//
// Created by cheerwizard on 21.10.25.
//

#include "Binding.hpp"
#include "DescriptorPools.hpp"
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
            DescriptorPools::newPool((VkDescriptorType) binding.type, 10);
        }

        layout.New(device.handle, VkDescriptorSetLayoutCreateInfo {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_LAYOUT_CREATE_INFO,
            .bindingCount = bindings_count,
            .pBindings = vkBindings.data(),
        });

        for (u32 i = 0 ; i < bindings_count ; i++) {
            DescriptorPools::newSet((VkDescriptorType) bindings[i].type, layout.handle);
        }
    }

    BindingLayout::~BindingLayout() {
        layout.Delete();
    }

    void BindingLayout::update(const std::vector<Binding> &bindings) {

    }

    void BindingLayout::updateResource(const Resource &resource) {
        VkDescriptorBufferInfo bufferInfo = {
            .buffer = handle,
            .offset = 0,
            .range = info.size,
        };

        VkWriteDescriptorSet descriptorWrite = {a
            .sType = VK_STRUCTURE_TYPE_WRITE_DESCRIPTOR_SET,
            .dstSet = set,
            .dstBinding = binding.slot,
            .dstArrayElement = 0,
            .descriptorCount = 1,
            .descriptorType = (VkDescriptorType) binding.type,
            .pBufferInfo = &bufferInfo,
        };

        vkUpdateDescriptorSets(handle, 1, &descriptorWrite, 0, nullptr);
    }

}
