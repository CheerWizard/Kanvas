package com.cws.kanvas.wgpu.gpu

object GPUTextureUsage {
    const val TEXTURE_BINDING: Int = 1 shl 0
    const val COPY_SRC: Int = 1 shl 1
    const val COPY_DST: Int = 1 shl 2
    const val RENDER_ATTACHMENT: Int = 1 shl 3
    const val STORAGE_BINDING: Int = 1 shl 4
}