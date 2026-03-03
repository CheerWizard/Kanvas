//
// Created by Vitalii Andrusyshyn on 06.02.2026.
//

#include "../api/ResultBridge.h"

inline ResultBridgeFn g_callback = nullptr;

void ResultBridge_init(ResultBridgeFn callback) {
    g_callback = callback;
}

void ResultBridge_send(VkResult result) {
    if (g_callback) {
        g_callback(result);
    }
}