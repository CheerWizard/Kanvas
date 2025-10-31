#include "../LogBridge.hpp"

#include <emscripten.h>

extern "C" {
    EM_JS(void, LogBridge_log, (int level, const char* tag, const char* msg, const char* exceptionMsg), {
      const jsTag = UTF8ToString(tag);
      const jsMsg = UTF8ToString(msg);
      const jsExceptionMsg = exceptionMessage ? UTF8ToString(exceptionMsg) : null;
      if (typeof globalThis.__logBridge === "function") {
        try {
          globalThis.__kmpLog(level, t, m, e);
        } catch (error) {
          console.error("LogBridge error:", error);
        }
      } else {
        console.log("LogBridge is not installed:", level, jsTag, jsMsg, jsExceptionMsg);
      }
    });
}

namespace stc {

    void LogBridge::log(LogLevel level, const char *tag, const char *message, const char *exceptionMessage) {
        LogBridge_log((int) level, tag, message, exceptionMessage);
    }

}