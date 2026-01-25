package com.cws.kanvas.wgpu.gpu

import org.w3c.dom.events.Event

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUUncapturedErrorEvent)
 */
external interface GPUUncapturedErrorEventInit {
    var error: GPUError
}

open external class GPUUncapturedErrorEvent(
    init: GPUUncapturedErrorEventInit,
) : Event {
    val error: GPUError

    companion object
}

inline val GPUUncapturedErrorEvent.Companion.UNCAPTURED_ERROR: String
    get() = "uncapturederror"