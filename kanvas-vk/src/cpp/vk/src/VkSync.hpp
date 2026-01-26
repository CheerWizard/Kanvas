//
// Created by cheerwizard on 18.10.25.
//

#ifndef SYNC_HPP
#define SYNC_HPP

#include "VkCommon.hpp"

struct VkContext;

struct VkFenceResource {
    VkDevice device = nullptr;
    VkFence fence = nullptr;

    VkFenceResource(VkDevice device, const char* name, bool signaled);
    ~VkFenceResource();

    void wait(u64 timeout = UINT64_MAX);
    void reset();
};

struct VkSemaphoreResource {
    VkDevice device = nullptr;
    VkSemaphore semaphore = nullptr;

    VkSemaphoreResource(VkDevice device, const char* name);
    ~VkSemaphoreResource();
};

#endif // SYNC_HPP