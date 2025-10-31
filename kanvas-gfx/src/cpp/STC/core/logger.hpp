//
// Created by cheerwizard on 07.07.25.
//

#ifndef LOGGER_HPP
#define LOGGER_HPP

#include "thread_pool.hpp"
#include "../bridges/LogBridge.hpp"

namespace stc {

#define ASSERT(condition, tag, msg, ...) \
if (!(condition)) { \
    LOG_ASSERT(tag, msg, ##__VA_ARGS__); \
}

#if defined(DEBUG)

#if defined(ANDROID)

#define LOG_VERBOSE(tag, msg, ...) Logger::getInstance().log(LogLevel::VERBOSE, tag, msg, 0, ##__VA_ARGS__)
#define LOG_INFO(tag, msg, ...) Logger::getInstance().log(LogLevel::INFO, tag, msg, 0, ##__VA_ARGS__)
#define LOG_DEBUG(tag, msg, ...) Logger::getInstance().log(LogLevel::DBG, tag, msg, 0, ##__VA_ARGS__)
#define LOG_WARNING(tag, msg, ...) Logger::getInstance().log(LogLevel::WARNING, tag, msg, 0, ##__VA_ARGS__)
#define LOG_ERROR(tag, msg, ...) Logger::getInstance().log(LogLevel::ERROR, tag, msg, 0, ##__VA_ARGS__)
#define LOG_ASSERT(tag, msg, ...) Logger::getInstance().log(LogLevel::FATAL, tag, msg, 0, ##__VA_ARGS__)

#else

#define LOG_VERBOSE(tag, msg, ...) Logger::getInstance().log(LogLevel::VERBOSE, tag, msg, ##__VA_ARGS__)
#define LOG_INFO(tag, msg, ...) Logger::getInstance().log(LogLevel::INFO, tag, msg, ##__VA_ARGS__)
#define LOG_DEBUG(tag, msg, ...) Logger::getInstance().log(LogLevel::DBG, tag, msg, ##__VA_ARGS__)
#define LOG_WARNING(tag, msg, ...) Logger::getInstance().log(LogLevel::WARNING, tag, msg, ##__VA_ARGS__)
#define LOG_ERROR(tag, msg, ...) Logger::getInstance().log(LogLevel::ERROR, tag, msg, ##__VA_ARGS__)
#define LOG_ASSERT(tag, msg, ...) Logger::getInstance().log(LogLevel::FATAL, tag, msg, ##__VA_ARGS__)

#endif

#else

#define LOG_VERB(tag,  msg, ...)
#define LOG_INFO(tag, msg, ...)
#define LOG_DBG(tag, msg, ...)
#define LOG_WARN(tag, msg, ...)
#define LOG_ERR(tag, msg, ...)
#define LOG_ASSERT(tag, msg, ...) std::abort()

#endif

    using LogBuffer = std::array<char, 256>;

    struct Log {
        LogLevel level;
        LogBuffer tag;
        LogBuffer message;
        LogBuffer exceptionMessage;
    };

    struct Logger {

        Logger();
        ~Logger();

        static Logger& getInstance();

        template<typename... Args>
        void log(
            LogLevel level,
            const char* tag,
            const char* msg,
            Args &&... args
        );

    private:
        void runLoop();

        ConcurrentQueue<Log, 100> logQueue;
        std::mutex mutex;
        bool running = false;
    };

    template<typename ... Args>
    void Logger::log(
        LogLevel level,
        const char* tag,
        const char* msg,
        Args &&... args
    ) {
        std::unique_lock lock(mutex);
        Log log;
#if defined(ANDROID)
        std::snprintf(log.message.data(), log.message.size(), "%s", msg, args...);
        std::snprintf(log.tag.data(), log.tag.size(), "%s", tag);
#else
        std::snprintf(log.message.data(), log.message.size(), msg, args...);
        std::snprintf(log.tag.data(), log.tag.size(), tag);
#endif
        log.level = level;
        if (level >= LogLevel::ERROR) {
            log.exceptionMessage = log.message;
        }
        logQueue.push(log);
    }

}

#endif //LOGGER_HPP
