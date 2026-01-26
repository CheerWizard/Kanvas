//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef CATCH_VKDEVICEQUEUE_HPP
#define CATCH_VKDEVICEQUEUE_HPP

#include "VkCommon.hpp"

struct VkQueueFamilyIndices {
    u32 graphics = 0;
    u32 present = 0;
    u32 compute = 0;
    u32 transfer = 0;
};

struct VkDeviceQueue {
    VkDevice device = nullptr;
    VkQueue queue = nullptr;
    VkCommandPool pool = nullptr;
    u32 family_index = 0;

    VkDeviceQueue(VkDevice device, const char* name, u32 family_index);
    ~VkDeviceQueue();

    void reset();
};

#endif //CATCH_VKDEVICEQUEUE_HPP
