package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUSamplerDescriptor

actual class Sampler actual constructor(
    private val context: RenderContext,
    info: SamplerInfo
) : Resource<SamplerHandle, SamplerInfo>(info) {

    actual override fun onCreate() {
        context.call { device ->
            handle = SamplerHandle(device.createSampler(GPUSamplerDescriptor(
                label = info.name.value,
                addressModeU = info.addressModeU.jsValue,
                addressModeV = info.addressModeV.jsValue,
                addressModeW = info.addressModeW.jsValue,
                magFilter = info.magFilter.jsValue,
                minFilter = info.minFilter.jsValue,
                mipmapFilter = info.mipmapMode.jsValue,
                compare = info.compareOp.jsValue,
                lodMinClamp = info.minLod,
                lodMaxClamp = info.maxLod,
                maxAnisotropy = info.maxAnisotropy.toInt().toShort(),
            )))
        }
    }

    actual override fun onDestroy() {
    }

    actual override fun setInfo() {
        onCreate()
    }

}