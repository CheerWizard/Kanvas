//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#include "VkContext.hpp"
#include "VkCheck.hpp"
#include "DescriptorPools.hpp"
#include "VulkanAllocator.hpp"
#include "../core/logger.hpp"
#include "DescriptorPools.hpp"

#ifdef ANDROID

#include <vulkan/vulkan_android.h>

#define SURFACE_EXTENSION_NAME VK_KHR_ANDROID_SURFACE_EXTENSION_NAME

#elif IOS

#include <vulkan/vulkan_ios.h>

#elif LINUX

#include <vulkan/vulkan_xlib.h>

#define SURFACE_EXTENSION_NAME VK_KHR_XLIB_SURFACE_EXTENSION_NAME

#elif WINDOWS

#include <vulkan/vulkan_win32.h>

#define SURFACE_EXTENSION_NAME VK_KHR_WIN32_SURFACE_EXTENSION_NAME

#endif

VkContext* VkContext_create(
        void* native_window,
        const char* application_name,
        const char* engine_name
) {
    return new VkContext(native_window, application_name, engine_name);
}

void VkContext_destroy(VkContext* context) {
    delete CONTEXT;
}

void VkContext_wait(VkContext* context) {
    CONTEXT->wait();
}

VkDeviceQueue* VkContext_getQueue(VkContext* context) {
    return CONTEXT->getQueue();
}

VkResult vkCreateDebugUtilsMessengerEXT(VkInstance instance, const VkDebugUtilsMessengerCreateInfoEXT* pCreateInfo, const VkAllocationCallbacks* pAllocator, VkDebugUtilsMessengerEXT* pDebugMessenger) {
    auto func = reinterpret_cast<PFN_vkCreateDebugUtilsMessengerEXT>(vkGetInstanceProcAddr(instance, "vkCreateDebugUtilsMessengerEXT"));
    if (func != nullptr) {
        return func(instance, pCreateInfo, pAllocator, pDebugMessenger);
    } else {
        return VK_ERROR_EXTENSION_NOT_PRESENT;
    }
}

void vkDestroyDebugUtilsMessengerEXT(VkInstance instance, VkDebugUtilsMessengerEXT debugMessenger, const VkAllocationCallbacks* pAllocator) {
    auto func = reinterpret_cast<PFN_vkDestroyDebugUtilsMessengerEXT>(vkGetInstanceProcAddr(instance, "vkDestroyDebugUtilsMessengerEXT"));
    if (func != nullptr) {
        func(instance, debugMessenger, pAllocator);
    }
}

static VKAPI_ATTR VkBool32 VKAPI_CALL onDebugCallback(
        VkDebugUtilsMessageSeverityFlagBitsEXT messageSeverity,
        VkDebugUtilsMessageTypeFlagsEXT messageType,
        const VkDebugUtilsMessengerCallbackDataEXT* callbackData,
        void* userData
) {
    switch (messageSeverity) {
        case VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT:
            LOG_WARNING("VkDebugger", "type=%d message=%s", messageType, callbackData->pMessage);
            break;
        case VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT:
            LOG_WARNING("VkDebugger", "type=%d message=%s", messageType, callbackData->pMessage);
            break;
        default: break;
    }
    return VK_FALSE;
}

VkContext::VkContext(
        void* native_window,
        const char* application_name,
        const char* engine_name
) {
    VkApplicationInfo appInfo { VK_STRUCTURE_TYPE_APPLICATION_INFO };
    appInfo.pApplicationName = application_name;
    appInfo.applicationVersion = VK_MAKE_VERSION(1, 0, 0);
    appInfo.pEngineName = engine_name;
    appInfo.engineVersion = VK_MAKE_VERSION(1, 0, 0);
    appInfo.apiVersion = VK_API_VERSION_1_3;

    VkInstanceCreateInfo createInfo { VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO };
    createInfo.pApplicationInfo = &appInfo;

    std::vector<const char*> extension_names;

    if (native_window) {
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

        VK_CHECK(vkCreateDebugUtilsMessengerEXT(instance, &debugCreateInfo, VK_CALLBACKS, &debug_utils));

        createInfo.pNext = &debugCreateInfo;

#endif

    VK_CHECK(vkCreateInstance(&createInfo, VK_CALLBACKS, &instance));

    findDevice();

    VkSurfaceKHR surface = ;
    initSurface();
    surface = new VkSurface(this, findSurface(native_window), )
}

VkContext::~VkContext() {
    DescriptorPools::Delete();
    if (device) {
        vkDestroyDevice(device, VK_CALLBACKS);
        device = nullptr;
    }
    if (debug_utils) {
        vkDestroyDebugUtilsMessengerEXT(instance, debug_utils, VK_CALLBACKS);
        debug_utils = nullptr;
    }
    if (instance) {
        vkDestroyInstance(instance, VK_CALLBACKS);
        instance = nullptr;
    }
    VulkanAllocator::getInstance().Delete();
}

void VkContext::findDevice() {
    u32 count = 0;
    vkEnumeratePhysicalDevices(instance, &count, nullptr);

    ASSERT(count > 0, TAG, "Failed to find any GPU card");

    std::vector<VkPhysicalDevice> physical_devices(count);
    vkEnumeratePhysicalDevices(instance, &count, physical_devices.data());

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
                initDevice(physicalDevice);
            }
        }
    }

    ASSERT(device, TAG, "Failed to find suitable GPU with graphics queue!");
}

bool VkContext::checkExtensions(const char **extensions, u32 count) {
    for (int i = 0 ; i < count ; i++) {
        if (checkExtension(extensions[i])) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkLayers(const char **layers, u32 count) {
    for (int i = 0 ; i < count ; i++) {
        if (checkLayer(layers[i])) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkExtension(const char *extension) {
    for (const auto& entry : extensions) {
        if (strcmp(extension, entry.extensionName) == 0) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkLayer(const char *layer) {
    for (const auto& entry : layers) {
        if (strcmp(layer, entry.layerName) == 0) {
            return true;
        }
    }
    return false;
}

void VkContext::initDevice(VkPhysicalDevice physicalDevice) {
    u32 count = 0;

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

    for (u32 i = 0 ; i < count ; i++) {
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

    std::array extensions = {
            VK_KHR_SWAPCHAIN_EXTENSION_NAME,
    };

    ASSERT(
            checkDeviceExtensions(extensions.data(), extensions.size()),
            TAG, "Failed to find required extensions for device"
    );

    createInfo.enabledExtensionCount = extensions.size();
    createInfo.ppEnabledExtensionNames = extensions.data();

    VK_CHECK(vkCreateDevice(physicalDevice, &createInfo, VK_CALLBACKS, &device));

    VulkanAllocator::getInstance().New(instance, physicalDevice, device);

    DescriptorPools::New(device);
}

bool VkContext::checkDeviceExtensions(const char **extensions, u32 count) {
    for (int i = 0 ; i < count ; i++) {
        if (checkDeviceExtension(extensions[i])) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkDeviceLayers(const char **layers, u32 count) {
    for (int i = 0 ; i < count ; i++) {
        if (checkDeviceLayer(layers[i])) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkDeviceExtension(const char *extension) {
    for (const auto& entry : extensions) {
        if (strcmp(extension, entry.extensionName) == 0) {
            return true;
        }
    }
    return false;
}

bool VkContext::checkDeviceLayer(const char *layer) {
    for (const auto& entry : layers) {
        if (strcmp(layer, entry.layerName) == 0) {
            return true;
        }
    }
    return false;
}

VkDeviceQueue* VkContext::getQueue() {
    // TODO: need to consider how to store it
    u32 family_index = queue_family_indices.graphics;

    queueMutex.lock();

    auto thread_id = std::this_thread::get_id();

    if (queues[thread_id]) {
        return queues[thread_id];
    }

    VkDeviceQueue* queue = new VkDeviceQueue(device, family_index);
    queues[thread_id] = queue;
    return queue;
}

void VkContext::wait() {
    if (device) {
        vkDeviceWaitIdle(device);
    }
}

#ifdef ANDROID

VkSurfaceKHR VkContext::findSurface(void* native_window) {
    VkAndroidSurfaceCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR,
            .window = (ANativeWindow*) native_window,
    };
    VkSurfaceKHR foundSurface;
    VK_CHECK(vkCreateAndroidSurfaceKHR(instance, &createInfo, &VulkanAllocator::getInstance().callbacks, &foundSurface));
    return foundSurface;
}

#elif IOS

VkSurfaceKHR VkContext::findSurface(void* native_window) {
    VkAndroidSurfaceCreateInfoKHR createInfo {
            .sType = VK_STRUCTURE_TYPE_ANDROID_SURFACE_CREATE_INFO_KHR,
            .window = (ANativeWindow*) native_window,
    };
    VkSurfaceKHR foundSurface;
    VK_CHECK(vkCreateAndroidSurfaceKHR(instance, &createInfo, &VulkanAllocator::getInstance().callbacks, &foundSurface));
    return foundSurface;
}

#else

VkSurfaceKHR VkContext::findSurface(void* native_window) {
    // no-op
    return nullptr;
}

#endif
