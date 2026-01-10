//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_HANDLE_VK_HPP
#define STC_HANDLE_VK_HPP

#include <vulkan/vulkan_core.h>
#include "VulkanAllocator.hpp"

#define CALL(function) ASSERT((function) == VK_SUCCESS, "Vulkan", #function)
#define null VK_NULL_HANDLE

namespace stc {

    using Nothing = std::nullptr_t;

    #define VK_HANDLE(PARENT, parent, HANDLE, ...)                                                                              \
    template<typename T, typename Parent>                                                                   \
    struct HANDLE##Handle##Template {                                                                                   \
        Parent parent = null;                                                                             \
        T handle = null;                                                                                         \
                                                                                                                            \
        void New(Parent parent, const Vk##HANDLE##CreateInfo##__VA_ARGS__& createInfo) {                                    \
            this->parent = parent;                                                                                          \
            if constexpr (std::is_same_v<Parent, Nothing>) {                                             \
                CALL(vkCreate##HANDLE##__VA_ARGS__(&createInfo, &VulkanAllocator::getInstance().callbacks, &handle));                    \
            } else {                                                                                                               \
                if (parent != null) {                                                                             \
                    CALL(vkCreate##HANDLE##__VA_ARGS__(parent, &createInfo, &VulkanAllocator::getInstance().callbacks, &handle)); \
                }                                                                                                           \
            }                                                                                                   \
        }                                                                                                       \
                                                                                                                \
        void Delete() {                                                                                       \
            if constexpr (std::is_same_v<Parent, VkDevice>) {                                                   \
                if (parent != null && handle != null) {                                     \
                    vkDestroy##HANDLE##__VA_ARGS__(parent, handle, &VulkanAllocator::getInstance().callbacks);                         \
                    handle = null;                                                                    \
                }                                                                                               \
            } else if constexpr (std::is_same_v<Parent, VkPhysicalDevice>) {                                    \
                if (handle != null) {                                                                 \
                    vkDestroy##HANDLE##__VA_ARGS__(handle, &VulkanAllocator::getInstance().callbacks);                                 \
                    handle = null;                                                                    \
                }                                                                                               \
            } else if constexpr (std::is_same_v<Parent, VkInstance> && std::is_same_v<T, VkDebugUtilsMessengerEXT>) {                                          \
                if (parent != null && handle != null) {                                                                 \
                    vkDestroy##HANDLE##__VA_ARGS__(parent, handle, &VulkanAllocator::getInstance().callbacks);                                 \
                    handle = null;                                                                    \
                }                                                                                               \
            } else if constexpr (std::is_same_v<Parent, VkInstance>) {                                                  \
                if (handle != null) {                                                                 \
                    vkDestroy##HANDLE##__VA_ARGS__(handle, &VulkanAllocator::getInstance().callbacks);                                 \
                    handle = null;                                                                    \
                }                                                                                               \
            } else if constexpr (std::is_same_v<Parent, Nothing>) {                                             \
                if (handle != null) {                                                                 \
                    vkDestroy##HANDLE##__VA_ARGS__(handle, &VulkanAllocator::getInstance().callbacks);                                 \
                    handle = null;                                                                    \
                }                                                                                               \
            } else {                                                                                            \
                static_assert(sizeof(Parent) == 0, "Unsupported Parent type for VulkanHandle");                 \
            }                                                                                                   \
        }                                                                                                       \
                                                                                                                \
        T get() const {                                                                                         \
            return handle;                                                                                      \
        }                                                                                                       \
                                                                                                                \
        operator T() const {                                                                                    \
            return handle;                                                                                      \
        }                                                                                                              \
    };                                                                                                          \
                                                                                                                \
    using HANDLE##Handle = HANDLE##Handle##Template<Vk##HANDLE##__VA_ARGS__, PARENT>;              \

#define VK_DEVICE_HANDLE(HANDLE, ...) VK_HANDLE(VkDevice, device, HANDLE, ##__VA_ARGS__)

    VK_HANDLE(Nothing, nothing, Instance)
    VK_HANDLE(VkInstance, instance, DebugUtilsMessenger, EXT)
    VK_HANDLE(VkPhysicalDevice, physicalDevice, Device)
    VK_DEVICE_HANDLE(Swapchain, KHR)
    VK_DEVICE_HANDLE(Image)
    VK_DEVICE_HANDLE(ImageView)
    VK_DEVICE_HANDLE(RenderPass)
    VK_DEVICE_HANDLE(CommandPool)
    VK_DEVICE_HANDLE(ShaderModule)
    VK_DEVICE_HANDLE(DescriptorSetLayout)
    VK_DEVICE_HANDLE(DescriptorPool)
    VK_DEVICE_HANDLE(PipelineLayout)
    VK_DEVICE_HANDLE(Fence)
    VK_DEVICE_HANDLE(Semaphore)
    VK_DEVICE_HANDLE(Framebuffer)
    VK_DEVICE_HANDLE(Sampler)

    using QueueHandle = VkQueue;
    using CommandBufferHandle = VkCommandBuffer;
    using SurfaceHandle = VkSurfaceKHR;
    using MemoryHandle = VkDeviceMemory;
    using BufferHandle = VkBuffer;
    using DescriptorSetHandle = VkDescriptorSet;
    using TextureHandle = ImageHandle;
    using TextureViewHandle = ImageViewHandle;

}

#endif //STC_HANDLE_VK_HPP