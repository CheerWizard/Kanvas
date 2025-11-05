//
// Created by cheerwizard on 05.11.25.
//

#include "DescriptorPools.hpp"

namespace stc {

    void DescriptorPools::initPool(const Device &device) {


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

    void DescriptorPools::releasePool() {
    }

    VkDescriptorSet DescriptorPools::initSet() {
    }

    void DescriptorPools::releaseSet(VkDescriptorSet set) {
    }

}
