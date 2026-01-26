//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_CHECK_HPP
#define VK_CHECK_HPP

#include "../core/ResultBridge.hpp"
#include "../core/logger.hpp"

#include <vulkan/vulkan_core.h>
#include <vulkan/vulkan.h>

#include <sstream>
#include <ostream>

#define VK_CHECK(fn)                                        \
do {                                                        \
    VkResult _r = (fn);                                    \
    if (_r != VK_SUCCESS) {                                \
        LOG_ERROR("VK call failed: %s (%d) at %s:%d", #fn, _r, __FILE__, __LINE__);                  \
        ResultBridge_send(_r);                             \
    }                                                       \
} while (0)

#define VK_DEBUG_NAME(device, type, handle, name)                     \
do {                                                                        \
    VkDebugUtilsObjectNameInfoEXT nameInfo {                                \
        .sType = VK_STRUCTURE_TYPE_DEBUG_UTILS_OBJECT_NAME_INFO_EXT,        \
        .objectType = type,                                           \
        .objectHandle = reinterpret_cast<uint64_t>(handle),                 \
        .pObjectName = name,                                                \
    };                                                                      \
    vkSetDebugUtilsObjectNameEXT(device, &nameInfo);                        \
} while (0)

#define VK_DEBUG_NAME_FORMAT(device, type, handle, ...)                     \
do {                                                                        \
    std::ostringstream ss;                                                  \
    ss << __VA_ARGS__;                                                      \
    auto debugName = ss.str();                                              \
    VK_DEBUG_NAME(device, type, handle, debugName.c_str());                 \
} while (0)

#endif //VK_CHECK_HPP
