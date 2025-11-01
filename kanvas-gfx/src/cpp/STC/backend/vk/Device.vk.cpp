//
// Created by cheerwizard on 18.10.25.
//

#ifdef VK

#include "../Device.hpp"

namespace stc {

    Device::Device(const DeviceCreateInfo &create_info) {
        physicalDevice = create_info.physical_device;

        uint32_t count = 0;

        vkEnumerateDeviceLayerProperties(physicalDevice, &count, nullptr);
        layers.resize(count);
        vkEnumerateDeviceLayerProperties(physicalDevice, &count, layers.data());

        vkEnumerateDeviceExtensionProperties(physicalDevice, nullptr, &count, nullptr);
        extensions.resize(count);
        vkEnumerateDeviceExtensionProperties(physicalDevice, nullptr, &count, extensions.data());

        vkGetPhysicalDeviceProperties(physicalDevice, &properties);

        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, &count, nullptr);
        queueFamilies.resize(count);
        vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, &count, queueFamilies.data());

        for (uint32_t i = 0 ; i < count ; i++) {
            auto queueFlags = queueFamilies[i].queueFlags;
            if (queueFlags & VK_QUEUE_GRAPHICS_BIT) {
                queue_family_indices.graphics = i;
                queue_family_indices.present = i;
            } else if (queueFlags & VK_QUEUE_COMPUTE_BIT) {
                queue_family_indices.compute = i;
            } else if (queueFlags & VK_QUEUE_TRANSFER_BIT) {
                queue_family_indices.transfer = i;
            }
        }
    }

    void Device::initialize() {
        float queuePriority = 1.0f;

        VkDeviceQueueCreateInfo queueCreateInfo {
            .sType = VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO,
            .queueFamilyIndex = queue_family_indices.graphics,
            .queueCount = 1,
            .pQueuePriorities = &queuePriority,
        };

        VkDeviceCreateInfo createInfo {
            .sType = VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO,
            .pNext = nullptr,
            .queueCreateInfoCount = 1,
            .pQueueCreateInfos = &queueCreateInfo,
        };

        std::array<const char*, 1> extensions = {
            VK_KHR_SWAPCHAIN_EXTENSION_NAME,
        };

        ASSERT(
            checkExtensions(extensions.data(), extensions.size()),
            TAG, "Failed to find required extensions for device"
        );

        createInfo.enabledExtensionCount = extensions.size();
        createInfo.ppEnabledExtensionNames = extensions.data();

        New(physicalDevice, createInfo);
    }

    bool Device::checkExtension(const char *extension) {
        for (const auto& entry : extensions) {
            if (strcmp(extension, entry.extensionName) == 0) {
                return true;
            }
        }
        return false;
    }

    bool Device::checkLayer(const char *layer) {
        for (const auto& entry : layers) {
            if (strcmp(layer, entry.layerName) == 0) {
                return true;
            }
        }
        return false;
    }

    void Device::wait() {
        vkDeviceWaitIdle(handle);
    }

}

#endif