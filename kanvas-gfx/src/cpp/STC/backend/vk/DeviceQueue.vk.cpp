//
// Created by cheerwizard on 20.10.25.
//

#include "../DeviceQueue.hpp"
#include "backend/Device.hpp"

stc::DeviceQueue::DeviceQueue(const Device &device, u32 familyIndex) {
    vkGetDeviceQueue(device.handle, familyIndex, 0, &handle);
    VkCommandPoolCreateInfo poolInfo = {
        .sType = VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO,
        .flags = VK_COMMAND_POOL_CREATE_RESET_COMMAND_BUFFER_BIT,
        .queueFamilyIndex = familyIndex,
    };
    pool.New(device.handle, poolInfo);
}

stc::DeviceQueue::~DeviceQueue() {
    handle = null;
    pool.Delete();
}

void stc::DeviceQueue::reset() const {
    CALL(vkResetCommandPool(pool.device, pool.handle, 0));
}