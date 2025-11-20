//
// Created by cheerwizard on 20.10.25.
//

#include "../Context.hpp"

#include <vulkan/vulkan_xlib.h>

#include "DescriptorPools.hpp"

#ifdef ANDROID

#define SURFACE_EXTENSION_NAME VK_KHR_ANDROID_SURFACE_EXTENSION_NAME

#elif LINUX

#define SURFACE_EXTENSION_NAME VK_KHR_XLIB_SURFACE_EXTENSION_NAME

#elif WINDOWS

#define SURFACE_EXTENSION_NAME VK_KHR_WIN32_SURFACE_EXTENSION_NAME

#endif

namespace stc {

    static VKAPI_ATTR VkBool32 VKAPI_CALL onDebugCallback(
        VkDebugUtilsMessageSeverityFlagBitsEXT messageSeverity,
        VkDebugUtilsMessageTypeFlagsEXT messageType,
        const VkDebugUtilsMessengerCallbackDataEXT* callbackData,
        void* userData
    ) {
        switch (messageSeverity) {
            case VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT:
                LOG_WARNING("VulkanDebugger", "type=%d message=%s", messageType, callbackData->pMessage);
                break;
            case VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT:
                LOG_WARNING("VulkanDebugger", "type=%d message=%s", messageType, callbackData->pMessage);
                break;
            default: break;
        }
        return VK_FALSE;
    }

    void Context::initInstance(const ContextCreateInfo &create_info) {
        VkApplicationInfo appInfo { VK_STRUCTURE_TYPE_APPLICATION_INFO };
        appInfo.pApplicationName = "Kanvas";
        appInfo.applicationVersion = VK_MAKE_VERSION(1, 0, 0);
        appInfo.pEngineName = "Kanvas";
        appInfo.engineVersion = VK_MAKE_VERSION(1, 0, 0);
        appInfo.apiVersion = VK_API_VERSION_1_0;

        VkInstanceCreateInfo createInfo { VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO };
        createInfo.pApplicationInfo = &appInfo;

        std::vector<const char*> extension_names;

        if (create_info.render_api_create_info.nativeWindow) {
            extension_names.emplace_back(VK_KHR_SURFACE_EXTENSION_NAME);
            extension_names.emplace_back(SURFACE_EXTENSION_NAME);
        }

#if defined(DEBUG)
        extension_names.emplace_back(VK_EXT_DEBUG_UTILS_EXTENSION_NAME);
        extension_names.emplace_back(VK_KHR_GET_PHYSICAL_DEVICE_PROPERTIES_2_EXTENSION_NAME);
#endif

        createInfo.enabledExtensionCount = static_cast<uint32_t>(extension_names.size());
        createInfo.ppEnabledExtensionNames = extension_names.data();

        uint32_t count = 0;

        vkEnumerateInstanceLayerProperties(&count, nullptr);
        layers.resize(count);
        vkEnumerateInstanceLayerProperties(&count, layers.data());

        vkEnumerateInstanceExtensionProperties(nullptr, &count, nullptr);
        extension_names.resize(count);
        vkEnumerateInstanceExtensionProperties(nullptr, &count, extensions.data());

#ifdef DEBUG
        std::array<const char*, 1> validationLayers = {
            "VK_LAYER_KHRONOS_validation"
        };

        if (checkLayers(validationLayers.data(), validationLayers.size())) {
            createInfo.enabledLayerCount = static_cast<u32>(validationLayers.size());
            createInfo.ppEnabledLayerNames = validationLayers.data();
        } else {
            LOG_WARNING(TAG, "Validation layers are not found!");
        }

        VkDebugUtilsMessengerCreateInfoEXT debugCreateInfo = {
            .sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT,
            .messageSeverity = VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT | VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT,
            .messageType = VK_DEBUG_UTILS_MESSAGE_TYPE_GENERAL_BIT_EXT | VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT | VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT,
            .pfnUserCallback = onDebugCallback,
            .pUserData = nullptr,
        };

        debug_utils.New(handle, debugCreateInfo);

        createInfo.pNext = &debugCreateInfo;

#endif

        New(nullptr, createInfo);
    }

    void Context::releaseInstance() {
        debug_utils.Delete();
        Delete();
        VulkanAllocator::getInstance().Delete();
    }

    void Context::findDevice() {
        u32 count = 0;
        vkEnumeratePhysicalDevices(handle, &count, nullptr);

        ASSERT(count > 0, TAG, "Failed to find any GPU card");

        std::vector<VkPhysicalDevice> physical_devices(count);
        vkEnumeratePhysicalDevices(handle, &count, physical_devices.data());

        std::vector<VkQueueFamilyProperties> queue_families;

        // find a single device that supports graphics
        for (int i = 0 ; i < count ; i++) {
            auto physicalDevice = physical_devices[i];

            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, &count, nullptr);
            queue_families.resize(count);
            vkGetPhysicalDeviceQueueFamilyProperties(physicalDevice, &count, queue_families.data());

            for (u32 j = 0 ; j < count ; j++) {
                auto queueFlags = queue_families[j].queueFlags;
                if (queueFlags & VK_QUEUE_GRAPHICS_BIT) {
                    device.New(DeviceCreateInfo {
                        .instance = handle,
                        .physical_device = physicalDevice,
                    });
                }
            }
        }

        ASSERT(device, TAG, "Failed to find suitable GPU with graphics queue!");
    }

    bool Context::checkExtension(const char *extension) {
        for (const auto& entry : extensions) {
            if (strcmp(extension, entry.extensionName) == 0) {
                return true;
            }
        }
        return false;
    }

    bool Context::checkLayer(const char *layer) {
        for (const auto& entry : layers) {
            if (strcmp(layer, entry.layerName) == 0) {
                return true;
            }
        }
        return false;
    }

}

#ifdef ANDROID

#include <vulkan/vulkan_android.h>

namespace stc {

    void* Context::findSurface(const RenderApiCreateInfo& render_api_create_info) {
        VkAndroidSurfaceCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR,
            .window = (ANativeWindow*) render_api_create_info.nativeWindow,
        };
        VkSurfaceKHR foundSurface;
        CALL(vkCreateAndroidSurfaceKHR(handle, &createInfo, &VulkanAllocator::getInstance().callbacks, &foundSurface));
        return foundSurface;
    }

}

#else

namespace stc {

    void* Context::findSurface(const RenderApiCreateInfo& render_api_create_info) {
        // no-op
        return nullptr;
    }

}

#endif