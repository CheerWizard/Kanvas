package com.cws.std.tracer

import com.cws.print.Print
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

class LogProfiler : Profiler {

    companion object {
        private const val TAG = "LogProfiler"
    }

    override fun open() {
        // no-op
    }

    override fun close() {
        // no-op
    }

    override fun profile(
        scope: TraceScope,
        phase: TracePhase,
        color: TraceColor,
        category: String,
        functionName: String,
        startTime: Duration,
        endTime: Duration,
        duration: Duration,
        expectedDuration: Duration,
    ) {
        if (duration > expectedDuration && expectedDuration != 0.nanoseconds) {
            Print.w(TAG, "Scope=$scope Phase=$phase Function=$functionName() - spent ${duration.inWholeMilliseconds} ms, expected ${expectedDuration.inWholeMilliseconds} ms")
        } else {
            Print.d(TAG, "Scope=$scope Phase=$phase Function=$functionName() - spent ${duration.inWholeMilliseconds} ms")
        }
    }

}