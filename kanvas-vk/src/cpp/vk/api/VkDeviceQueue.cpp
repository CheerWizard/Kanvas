//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#include "VkDeviceQueue.hpp"

void VkDeviceQueue_reset(VkDeviceQueue* device_queue) {
    device_queue->reset();
}

VkDeviceQueue::VkDeviceQueue(VkDevice device, u32 familyIndex) {
    vkGetDeviceQueue(device, familyIndex, 0, &queue);
    VkCommandPoolCreateInfo poolInfo = {
            .sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO,
            .flags = VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT,
            .queueFamilyIndex = familyIndex,
    };
    VK_CHECK(vkCreateCommandPool(device, &poolInfo, VK_CALLBACKS, &pool));
}

VkDeviceQueue::~VkDeviceQueue() {
    queue = nullptr;
    if (pool) {
        vkDestroyCommandPool(device, pool, VK_CALLBACKS);
        pool = nullptr;
    }
}

void VkDeviceQueue::reset() {
    VK_CHECK(vkResetCommandPool(device, pool, 0));
}