package com.cws.kanvas.wgpu.gpu

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUOutOfMemoryError)
 */
open external class GPUOutOfMemoryError(
    message: String,
) : GPUError
