//
// Created by cheerwizard on 17.10.25.
//

#ifndef STC_LOG_BRIDGE_HPP
#define STC_LOG_BRIDGE_HPP

namespace stc {

    enum class LogLevel {
        NONE = 0,
        VERBOSE,
        INFO,
        DBG,
        WARNING,
        ERROR,
        FATAL
    };

    struct LogBridge {
        static void log(LogLevel level, const char* tag, const char* message, const char* exceptionMessage = nullptr);
    };

}

#endif //STC_LOG_BRIDGE_HPP