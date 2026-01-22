//
// Created by cheerwizard on 18.10.25.
//

#ifndef SYNC_HPP
#define SYNC_HPP

#include "VkCommon.hpp"

#define FENCE reinterpret_cast<VkFenceSync*>(fence)
#define SEMAPHORE reinterpret_cast<VkSemaphoreSync*>(semaphore)

struct VkContext;

struct VkFenceSync {
    VkDevice device = nullptr;
    VkFence fence = nullptr;

    VkFenceSync(VkDevice device, bool signaled);
    ~VkFenceSync();

    void wait(u64 timeout = UINT64_MAX);
    void reset();
};

struct VkSemaphoreSync {
    VkDevice device = nullptr;
    VkSemaphore semaphore = nullptr;

    VkSemaphoreSync(VkDevice device);
    ~VkSemaphoreSync();
};

#endif // SYNC_HPP