//
// Created by cheerwizard on 17.10.25.
//

#ifndef LOG_BRIDGE_HPP
#define LOG_BRIDGE_HPP

typedef void (*LogBridgeFn) (int, const char*, const char*, const char*);

enum LogLevel {
    LOG_LEVEL_NONE = 0,
    LOG_LEVEL_VERBOSE,
    LOG_LEVEL_INFO,
    LOG_LEVEL_DEBUG,
    LOG_LEVEL_WARNING,
    LOG_LEVEL_ERROR,
    LOG_LEVEL_FATAL,
};

void LogBridge_init(LogBridgeFn callback);
void LogBridge_log(LogLevel level, const char* tag, const char* message, const char* exceptionMessage = nullptr);

#endif //LOG_BRIDGE_HPP