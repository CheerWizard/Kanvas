//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#include "VkDeviceQueue.hpp"

void VkDeviceQueue_reset(VkDeviceQueue* device_queue) {
    device_queue->reset();
}

VkDeviceQueue::VkDeviceQueue(VkDevice device, const char* name, u32 familyIndex) {
    vkGetDeviceQueue(device, familyIndex, 0, &queue);
    VkCommandPoolCreateInfo poolInfo = {
            .sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO,
            .flags = VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT,
            .queueFamilyIndex = familyIndex,
    };
    VK_CHECK(vkCreateCommandPool(device, &poolInfo, VK_CALLBACKS, &pool));

    char debugName[64];
    sprintf(debugName, "VkCommandPool-%s", name);
    VK_DEBUG_NAME(device, VK_OBJECT_TYPE_COMMAND_POOL, pool, debugName);
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