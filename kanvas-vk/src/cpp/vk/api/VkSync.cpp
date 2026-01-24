//
// Created by cheerwizard on 18.10.25.
//

#include "VkSync.hpp"
#include "VkContext.hpp"

VkFenceResource* VkFenceResource_create(VkContext* context, int signaled) {
    return new VkFenceResource(context->device, signaled > 0);
}

void VkFenceResource_destroy(VkFenceResource* fence) {
    delete fence;
}

void VkFenceResource_wait(VkFenceResource* fence, uint64_t timeout) {
    fence->wait(timeout);
}

void VkFenceResource_reset(VkFenceResource* fence) {
    fence->reset();
}

VkSemaphoreResource* VkSemaphoreResource_create(VkContext* context) {
    return new VkSemaphoreResource(context->device);
}

void VkSemaphoreResource_destroy(VkSemaphoreResource* semaphore) {
    delete semaphore;
}

VkFenceResource::VkFenceResource(VkDevice device, bool signaled) {
    VkFenceCreateInfo createInfo = { VK_STRUCTURE_TYPE_FENCE_CREATE_INFO };
    if (signaled) {
        createInfo.flags = VK_FENCE_CREATE_SIGNALED_BIT;
    }
    VK_CHECK(vkCreateFence(device, &createInfo, VK_CALLBACKS, &fence));
    this->device = device;
}

VkFenceResource::~VkFenceResource() {
    if (fence) {
        vkDestroyFence(device, fence, VK_CALLBACKS);
        fence = nullptr;
    }
}

void VkFenceResource::wait(u64 timeout) {
    VK_CHECK(vkWaitForFences(device, 1, &fence, VK_TRUE, timeout));
}

void VkFenceResource::reset() {
    VK_CHECK(vkResetFences(device, 1, &fence));
}

VkSemaphoreResource::VkSemaphoreResource(VkDevice device) {
    VkSemaphoreCreateInfo createInfo = { VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO };
    VK_CHECK(vkCreateSemaphore(device, &createInfo, VK_CALLBACKS, &semaphore));
}

VkSemaphoreResource::~VkSemaphoreResource() {
    if (semaphore) {
        vkDestroySemaphore(device, semaphore, VK_CALLBACKS);
        semaphore = nullptr;
    }
}
