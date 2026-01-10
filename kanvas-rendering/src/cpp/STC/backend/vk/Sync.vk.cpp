//
// Created by cheerwizard on 18.10.25.
//

#include "../Sync.hpp"
#include "backend/Device.hpp"

stc::Fence::Fence(const Device &device, bool signaled) {
    VkFenceCreateInfo createInfo = { VK_STRUCTURE_TYPE_FENCE_CREATE_INFO };
    if (signaled) {
        createInfo.flags = VK_FENCE_CREATE_SIGNALED_BIT;
    }
    handle.New(device.handle, createInfo);
}

stc::Fence::~Fence() {
    handle.Delete();
}

void stc::Fence::wait(u64 timeout) {
    CALL(vkWaitForFences(handle.device, 1, &handle.handle, VK_TRUE, timeout));
}

void stc::Fence::reset() {
    CALL(vkResetFences(handle.device, 1, &handle.handle));
}

stc::Semaphore::Semaphore(const Device& device) {
    VkSemaphoreCreateInfo createInfo = { VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO };
    handle.New(device.handle, createInfo);
}

stc::Semaphore::~Semaphore() {
    handle.Delete();
}