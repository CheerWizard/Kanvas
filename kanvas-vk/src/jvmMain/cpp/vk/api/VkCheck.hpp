//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_CHECK_HPP
#define VK_CHECK_HPP

#include "../bridges/ResultBridge.hpp"
#include "../core/logger.hpp"

#include <vulkan/vulkan.h>

#define VK_CHECK(fn)                                        \
do {                                                        \
    VkResult _r = (fn);                                    \
    if (_r != VK_SUCCESS) {                                \
        LOG_ERROR("VK call failed: %s (%d) at %s:%d", #fn, _r, __FILE__, __LINE__);                  \
        ResultBridge_send(_r);                             \
    }                                                       \
} while (0)

#endif //VK_CHECK_HPP
