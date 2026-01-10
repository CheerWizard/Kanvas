package com.cws.print

enum class LogLevel {
    NONE,
    VERBOSE,
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    FATAL;

    override fun toString(): String {
        return when (this) {
            NONE -> ""
            VERBOSE -> "[VERBOSE]"
            DEBUG -> "[DEBUG]"
            INFO -> "[INFO]"
            WARNING -> "[WARNING]"
            ERROR -> "[ERROR]"
            FATAL -> "[FATAL]"
        }
    }

    fun toColorCode(): String {
        return when (this) {
            NONE, VERBOSE -> "\u001B[0m"
            DEBUG, INFO -> "\u001B[32m"
            WARNING -> "\u001B[33m"
            ERROR, FATAL -> "\u001B[31m"
        }
    }
}