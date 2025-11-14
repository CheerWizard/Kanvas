//
// Created by cheerwizard on 05.11.25.
//

#include "DescriptorPools.hpp"

namespace stc {

    void DescriptorPools::New(const Device &device) {
        DescriptorPools::device = &device;
    }

    void DescriptorPools::Delete() {
        for (auto& pool : pools | std::views::values) {
            pool.Delete();
        }
        pools.clear();
        // sets delete automatically if delete pools
        sets.clear();
    }

    DescriptorPoolHandle DescriptorPools::newPool(VkDescriptorType type, u32 size) {
        if (pools.contains(type)) {
            // create only if type doesn't exist
            return pools[type];
        }

        VkDescriptorPoolSize poolSize = {
            .type = type,
            .descriptorCount = size
        };

        VkDescriptorPoolCreateInfo poolInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_POOL_CREATE_INFO,
            .maxSets = 1,
            .poolSizeCount = 1,
            .pPoolSizes = &poolSize,
        };

        DescriptorPoolHandle pool;
        pool.New(device->handle, poolInfo);
        pools[type] = pool;
    }

    void DescriptorPools::deletePool(VkDescriptorType type) {
        if (pools.contains(type)) {
            pools[type].Delete();
            pools.erase(type);
        }
    }

    VkDescriptorSet DescriptorPools::newSet(VkDescriptorType type, VkDescriptorSetLayout layout) {
        if (sets.contains(type)) {
            // create only if type doesn't exist
            return sets[type];
        }

        if (!pools.contains(type)) {
            newPool(type, 10);
        }

        VkDescriptorSetAllocateInfo allocInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO,
            .descriptorPool = pools[type],
            .descriptorSetCount = 1,
            .pSetLayouts = &layout,
        };
        VkDescriptorSet set;
        CALL(vkAllocateDescriptorSets(device->handle, &allocInfo, &set));

        sets[type] = set;
    }

    void DescriptorPools::deleteSet(VkDescriptorType type) {
        if (pools.contains(type) && sets.contains(type)) {
            CALL(vkFreeDescriptorSets(device->handle, pools[type], 1, &sets[type]));
            sets.erase(type);
        }
    }

}
