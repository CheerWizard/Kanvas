//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_CONTEXT_HPP
#define VK_CONTEXT_HPP

#include "../core/types.hpp"
#include "VkSurface.hpp"
#include "VkDeviceQueue.hpp"

#include <vector>
#include <unordered_map>
#include <thread>

#define CONTEXT reinterpret_cast<VkContext*>(context)

struct VkContext {
    VkInstance instance = nullptr;
    VkDebugUtilsMessengerEXT debug_utils = nullptr;
    std::vector<VkExtensionProperties> extensions;
    std::vector<VkLayerProperties> layers;

    VkPhysicalDevice physical_device = nullptr;
    VkPhysicalDeviceProperties properties = {};

    std::vector<VkExtensionProperties> deviceExtensions;
    std::vector<VkLayerProperties> deviceLayers;
    VkDevice device = nullptr;

    std::vector<VkQueueFamilyProperties> queueFamilies;
    VkQueueFamilyIndices queue_family_indices;

    VkSurface* surface = nullptr;

    VkContext(const VkContextInfo& info);

    ~VkContext();

    void wait();

    VkDeviceQueue* getQueue();

private:
    bool checkExtensions(const char** extension, u32 count);
    bool checkLayers(const char** extension, u32 count);

    void initInstance();
    bool checkExtension(const char* extension);
    bool checkLayer(const char* extension);

    void findDevice();
    void initDevice(VkPhysicalDevice physicalDevice);
    bool checkDeviceExtensions(const char** extensions, u32 count);
    bool checkDeviceLayers(const char** layers, u32 count);
    bool checkDeviceExtension(const char* extension);
    bool checkDeviceLayer(const char* layer);

    VkSurfaceKHR findSurface(void* native_window);

    static constexpr auto TAG = "VkContext";

    std::mutex queueMutex;
    std::unordered_map<std::thread::id, VkDeviceQueue*> queues;
};

#endif //VK_CONTEXT_HPP