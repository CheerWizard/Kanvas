//
// Created by cheerwizard on 01.11.25.
//

#include "../bridges/LogBridge.hpp"

LogBridgeFn g_callback = nullptr;

void LogBridge_init(LogBridgeFn callback) {
    g_callback = callback;
}

void LogBridge_log(LogLevel level, const char *tag, const char *message, const char* exceptionMessage) {
    if (g_callback) {
        g_callback((int) level, tag, message, exceptionMessage);
    }
}
