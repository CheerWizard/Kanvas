package com.cws.std.tracer

import kotlin.time.Duration

interface Profiler {
    fun open()
    fun close()
    fun profile(
        scope: TraceScope,
        phase: TracePhase,
        color: TraceColor,
        category: String,
        functionName: String,
        startTime: Duration,
        endTime: Duration,
        duration: Duration,
        expectedDuration: Duration,
    )
}