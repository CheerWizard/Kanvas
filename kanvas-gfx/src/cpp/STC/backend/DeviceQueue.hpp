//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_DEVICEQUEUE_HPP
#define STC_DEVICEQUEUE_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    struct DeviceQueueBackend {
        QueueHandle handle = null;
        u32 family_index = 0;
        CommandPoolHandle pool;
    };

#elif METAL

#elif WEBGPU

    struct DeviceQueueBackend : QueueHandle {
        WGPUDevice device = null;
        u32 family_index = 0;
    };

#endif

    struct QueueFamilyIndices {
        u32 graphics = 0;
        u32 present = 0;
        u32 compute = 0;
        u32 transfer = 0;
    };

    struct Device;

    struct DeviceQueue : DeviceQueueBackend {

        DeviceQueue(const Device& device, u32 familyIndex);
        ~DeviceQueue();

        void reset();

    };

}

#endif //STC_DEVICEQUEUE_HPP