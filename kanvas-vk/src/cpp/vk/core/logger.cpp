//
// Created by cheerwizard on 07.07.25.
//

#include "logger.hpp"

#include <thread>

Logger::Logger() {
    std::thread thread([this]() { runLoop(); });
    thread.detach();
}

Logger::~Logger() {
    running = false;
}

Logger& Logger::getInstance() {
    static Logger instance;
    return instance;
}

void Logger::runLoop() {
    running = true;
    // todo: potentially insecure for CPU usage, maybe add small sleep duration
    while (running) {
        Log log;
        logQueue.pop(log);
        LogBridge_log(log.level, log.tag.data(), log.message.data(), log.exceptionMessage.data());
    }
}