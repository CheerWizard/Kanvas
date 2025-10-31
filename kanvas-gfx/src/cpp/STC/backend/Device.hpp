//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_DEVICE_HPP
#define STC_DEVICE_HPP

#include "DeviceQueue.hpp"

namespace stc {

#ifdef VK

    struct DeviceBackend : DeviceHandle {
        VkPhysicalDeviceProperties properties = {};
        std::vector<VkExtensionProperties> extensions;
        std::vector<VkLayerProperties> layers;
        std::vector<VkQueueFamilyProperties> queueFamilies;
    };

    struct DeviceCreateInfo {
        VkPhysicalDevice physical_device = null;
    };

#elif METAL



#elif WEBGPU

    struct DeviceBackend {
        WGPUDevice handle = null;
        WGPUAdapterProperties properties = {};
        std::vector<WGPUFeatureName> features;
    };

    struct DeviceCreateInfo {
        WGPUAdapter adapter = null;
    };

#endif

    struct Device : DeviceBackend {
        QueueFamilyIndices queue_family_indices;

        Device(const DeviceCreateInfo& create_info);
        ~Device();

        void initialize();

        bool checkExtension(const char* extension);
        bool checkExtensions(const char** extensions, u32 count);
        bool checkLayer(const char* layer);
        bool checkLayers(const char** layers, u32 count);

        Scope<DeviceQueue>& getQueue(u32 family_index);
        void wait();

    private:
        std::mutex queueMutex;
        std::unordered_map<std::thread::id, Scope<DeviceQueue>> queues;

        static constexpr auto TAG = "Device";
    };

}

#endif //STC_DEVICE_HPP