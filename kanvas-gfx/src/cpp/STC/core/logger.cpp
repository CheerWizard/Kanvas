//
// Created by cheerwizard on 07.07.25.
//

#include "logger.hpp"

stc::Logger::Logger() {
    std::thread thread([this]() { runLoop(); });
    thread.detach();
}

stc::Logger::~Logger() {
    running = false;
}

stc::Logger& stc::Logger::getInstance() {
    static Logger instance;
    return instance;
}

void stc::Logger::runLoop() {
    running = true;
    // todo: potentially insecure for CPU usage, maybe add small sleep duration
    while (running) {
        Log log;
        logQueue.pop(log);
        LogBridge::log(log.level, log.tag.data(), log.message.data(), log.exceptionMessage.data());
    }
}