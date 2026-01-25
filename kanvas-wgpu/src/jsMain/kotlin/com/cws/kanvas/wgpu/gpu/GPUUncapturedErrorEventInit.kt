package com.cws.kanvas.wgpu.gpu

import org.w3c.dom.EventInit

/**
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUUncapturedErrorEvent/GPUUncapturedErrorEvent#options)
 */
external interface GPUUncapturedErrorEventInit :
    EventInit {
    var error: GPUError
}

fun GPUUncapturedErrorEvent(
    error: GPUError,
    bubbles: Boolean = false,
    cancelable: Boolean = false
): GPUUncapturedErrorEvent {
    val init = jsObject<GPUUncapturedErrorEventInit> {
        this.error = error
        this.bubbles = bubbles
        this.cancelable = cancelable
    }
    return GPUUncapturedErrorEvent(init)
}
