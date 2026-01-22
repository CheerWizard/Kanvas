//
// Created by cheerwizard on 07.07.25.
//

#include "logger.hpp"

#include <thread>

vk::Logger::Logger() {
    std::thread thread([this]() { runLoop(); });
    thread.detach();
}

vk::Logger::~Logger() {
    running = false;
}

vk::Logger& vk::Logger::getInstance() {
    static Logger instance;
    return instance;
}

void vk::Logger::runLoop() {
    running = true;
    // todo: potentially insecure for CPU usage, maybe add small sleep duration
    while (running) {
        Log log;
        logQueue.pop(log);
        LogBridge_log(log.level, log.tag.data(), log.message.data(), log.exceptionMessage.data());
    }
}