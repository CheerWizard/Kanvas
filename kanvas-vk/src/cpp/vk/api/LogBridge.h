//
// Created by cheerwizard on 17.10.25.
//

#ifndef LOG_BRIDGE_H
#define LOG_BRIDGE_H

typedef void (*LogBridgeFn) (int, const char*, const char*, const char*);

typedef enum LogLevel {
    LOG_LEVEL_NONE = 0,
    LOG_LEVEL_VERBOSE,
    LOG_LEVEL_INFO,
    LOG_LEVEL_DEBUG,
    LOG_LEVEL_WARNING,
    LOG_LEVEL_ERROR,
    LOG_LEVEL_FATAL,
} LogLevel;

#ifdef __cplusplus
extern "C" {
#endif

    void LogBridge_init(LogBridgeFn callback);
    void LogBridge_log(LogLevel level, const char* tag, const char* message, const char* exceptionMessage);

#ifdef __cplusplus
}
#endif

#endif //LOG_BRIDGE_H