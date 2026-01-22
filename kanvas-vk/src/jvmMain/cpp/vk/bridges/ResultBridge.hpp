//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef _RESULTBRIDGE_HPP
#define _RESULTBRIDGE_HPP

#include <vulkan/vulkan.h>

typedef void (*ResultBridgeFn) (VkResult result);

void ResultBridge_init(ResultBridgeFn callback);
void ResultBridge_send(VkResult result);

#endif //_RESULTBRIDGE_HPP
