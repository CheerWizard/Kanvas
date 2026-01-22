//
// Created by cheerwizard on 18.10.25.
//

#include "VkSync.hpp"
#include "VkContext.hpp"

VkFenceSync* VkFenceSync_create(VkContext* context, int signaled) {
    return new VkFenceSync(CONTEXT->device, signaled > 0);
}

void VkFenceSync_destroy(VkFenceSync* fence) {
    delete FENCE;
}

void VkFenceSync_wait(VkFenceSync* fence, uint64_t timeout) {
    FENCE->wait(timeout);
}

void VkFenceSync_reset(VkFenceSync* fence) {
    FENCE->reset();
}

VkSemaphoreSync* VkSemaphoreSync_create(VkContext* context) {
    return new VkSemaphoreSync(CONTEXT->device);
}

void VkSemaphoreSync_destroy(VkSemaphoreSync* semaphore) {
    delete SEMAPHORE;
}

VkFenceSync::VkFenceSync(VkDevice device, bool signaled) {
    VkFenceCreateInfo createInfo = { VK_STRUCTURE_TYPE_FENCE_CREATE_INFO };
    if (signaled) {
        createInfo.flags = VK_FENCE_CREATE_SIGNALED_BIT;
    }
    VK_CHECK(vkCreateFence(device, &createInfo, VK_CALLBACKS, &fence));
    this->device = device;
}

VkFenceSync::~VkFenceSync() {
    if (fence) {
        vkDestroyFence(device, fence, VK_CALLBACKS);
        fence = nullptr;
    }
}

void VkFenceSync::wait(u64 timeout) {
    VK_CHECK(vkWaitForFences(device, 1, &fence, VK_TRUE, timeout));
}

void VkFenceSync::reset() {
    VK_CHECK(vkResetFences(device, 1, &fence));
}

VkSemaphoreSync::VkSemaphoreSync(VkDevice device) {
    VkSemaphoreCreateInfo createInfo = { VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO };
    VK_CHECK(vkCreateSemaphore(device, &createInfo, VK_CALLBACKS, &semaphore));
}

VkSemaphoreSync::~VkSemaphoreSync() {
    if (semaphore) {
        vkDestroySemaphore(device, semaphore, VK_CALLBACKS);
        semaphore = nullptr;
    }
}
