//
// Created by cheerwizard on 17.10.25.
//

#include "../LogBridge.hpp"

typedef void (*LogBridgeFn) (int, const char*, const char*, const char*);

LogBridgeFn g_callback = nullptr;

extern "C" void LogBridge_init(LogBridgeFn callback) {
    g_callback = callback;
}

namespace stc {

    void LogBridge::log(LogLevel level, const char *tag, const char *message, const char* exceptionMessage) {
        if (g_callback) {
            g_callback((int) level, tag, message, exceptionMessage);
        }
    }

}