//
// Created by cheerwizard on 05.11.25.
//

#include "DescriptorPools.hpp"

void DescriptorPools::New(VkDevice device) {
    DescriptorPools::device = device;
}

void DescriptorPools::Delete() {
    for (auto& pool : pools) {
        vkDestroyDescriptorPool(device, pool.second, VK_CALLBACKS);
    }
    pools.clear();
    // sets delete automatically if delete pools
    sets.clear();
}

VkDescriptorPool DescriptorPools::newPool(VkDescriptorType type, u32 size) {
    if (pools[type]) {
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

    VkDescriptorPool pool;
    VK_CHECK(vkCreateDescriptorPool(device, &poolInfo, VK_CALLBACKS, &pool));
    pools[type] = pool;

    return pool;
}

void DescriptorPools::deletePool(VkDescriptorType type) {
    if (pools[type]) {
        vkDestroyDescriptorPool(device, pools[type], VK_CALLBACKS);
        pools.erase(type);
    }
}

VkDescriptorSet DescriptorPools::newSet(VkDescriptorType type, VkDescriptorSetLayout layout) {
    if (sets[type]) {
        // create only if type doesn't exist
        return sets[type];
    }

    if (!pools[type]) {
        newPool(type, 10);
    }

    VkDescriptorSetAllocateInfo allocInfo = {
            .sType = VK_STRUCTURE_TYPE_DESCRIPTOR_SET_ALLOCATE_INFO,
            .descriptorPool = pools[type],
            .descriptorSetCount = 1,
            .pSetLayouts = &layout,
    };

    VkDescriptorSet set;
    VK_CHECK(vkAllocateDescriptorSets(device, &allocInfo, &set));
    sets[type] = set;

    return set;
}

void DescriptorPools::deleteSet(VkDescriptorType type) {
    if (pools[type] && sets[type]) {
        VK_CHECK(vkFreeDescriptorSets(device, pools[type], 1, &sets[type]));
        sets.erase(type);
    }
}
