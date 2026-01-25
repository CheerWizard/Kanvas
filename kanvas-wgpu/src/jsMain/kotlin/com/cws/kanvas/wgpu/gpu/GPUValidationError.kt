package com.cws.kanvas.wgpu.gpu

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/GPUValidationError)
 */
open external class GPUValidationError(
    message: String,
) : GPUError
