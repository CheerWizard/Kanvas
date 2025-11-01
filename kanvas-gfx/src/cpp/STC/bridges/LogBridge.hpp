//
// Created by cheerwizard on 17.10.25.
//

#ifndef STC_LOG_BRIDGE_HPP
#define STC_LOG_BRIDGE_HPP

extern "C" {

    void web_log_verbose(const char* tag, const char* msg);
    void web_log_info(const char* tag, const char* msg);
    void web_log_debug(const char* tag, const char* msg);
    void web_log_warning(const char* tag, const char* msg);
    void web_log_error(const char* tag, const char* msg);
    void web_log_assert(const char* tag, const char* msg);

}

namespace stc {

    enum LogLevel {
        LOG_LEVEL_NONE = 0,
        LOG_LEVEL_VERBOSE,
        LOG_LEVEL_INFO,
        LOG_LEVEL_DEBUG,
        LOG_LEVEL_WARNING,
        LOG_LEVEL_ERROR,
        LOG_LEVEL_FATAL,
    };

    struct LogBridge {
        static void log(LogLevel level, const char* tag, const char* message, const char* exceptionMessage = nullptr);
    };

}

#endif //STC_LOG_BRIDGE_HPP