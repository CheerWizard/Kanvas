//
// Created by cheerwizard on 01.11.25.
//

#include "LogBridge.hpp"

using namespace stc;

void web_log_verbose(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_VERBOSE, tag, msg);
}

void web_log_info(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_INFO, tag, msg);
}

void web_log_debug(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_DEBUG, tag, msg);
}

void web_log_warning(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_WARNING, tag, msg);
}

void web_log_error(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_ERROR, tag, msg);
}

void web_log_assert(const char *tag, const char *msg) {
    Logger::getInstance().log(LOG_LEVEL_FATAL, tag, msg);
}