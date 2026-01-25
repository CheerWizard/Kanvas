package com.cws.kanvas.wgpu.gpu

/**
 * The **`GPUError`** interface of the WebGPU API is the base interface for errors surfaced by GPUDevice.popErrorScope and the uncapturederror event.
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUError)
 */
open external class GPUError
private constructor() {
    /**
     * The **`message`** read-only property of the GPUError interface provides a human-readable message that explains why the error occurred.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUError/message)
     */
    val message: String
}
