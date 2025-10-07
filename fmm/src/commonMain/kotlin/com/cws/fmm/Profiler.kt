package com.cws.fmm

import com.cws.fmm.Profiler.TAG
import com.cws.printer.Printer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.ExperimentalTime

object Profiler {

    const val TAG = "Profiler"

    private var scope: CoroutineScope? = null

    @OptIn(DelicateCoroutinesApi::class)
    fun start() {
        if (scope?.isActive == true) return
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    }

    fun stop() {
        scope?.cancel()
        scope = null
    }

}

@OptIn(ExperimentalTime::class)
inline fun profile(function: () -> Unit): Duration {
    val startDuration = Clock.System.now().toEpochMilliseconds().nanoseconds
    function()
    val endDuration = Clock.System.now().toEpochMilliseconds().nanoseconds
    return endDuration - startDuration
}

@OptIn(ExperimentalTime::class)
inline fun record(name: String, expectedDuration: Duration = 0.nanoseconds, function: () -> Unit) {
    val actualDuration = profile(function)
    if (actualDuration > expectedDuration && expectedDuration != 0.nanoseconds) {
        Printer.w(TAG, "$name() - spent ${actualDuration.inWholeMilliseconds} ms, expected ${expectedDuration.inWholeMilliseconds} ms")
    } else {
        Printer.d(TAG, "$name() - spent ${actualDuration.inWholeMilliseconds} ms")
    }
}