//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef RESULT_BRIDGE_H
#define RESULT_BRIDGE_H

#include <vulkan/vulkan.h>

typedef void (*ResultBridgeFn) (VkResult result);

#ifdef __cplusplus
extern "C" {
#endif

    void ResultBridge_init(ResultBridgeFn callback);
    void ResultBridge_send(VkResult result);

#ifdef __cplusplus
}
#endif

#endif //RESULT_BRIDGE_H
