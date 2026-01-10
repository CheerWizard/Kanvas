package com.cws.kanvas.rendering.backend

sealed interface Feature {
    data object Graphics : Feature
    data object Compute : Feature

    data object Tessellation : Feature
    data object GeometryShader : Feature
    data class MeshShader(
        val maxWorkgroups: Int,
        val supportsTaskShader: Boolean,
    ) : Feature

    data object SamplerAnisotropy : Feature
    data object DepthClamp : Feature
    data object DepthBiasClamp : Feature
    data object NonSolidFillMode : Feature

    data object Multisampling : Feature
    data object SampleRateShading : Feature

    data object TextureCompressionBC : Feature
    data object TextureCompressionETC2 : Feature
    data object TextureCompressionASTC : Feature

    data object Texture3D : Feature
    data object TextureCubeMapArray : Feature

    data object StorageBuffer : Feature
    data object UniformBufferDynamic : Feature
    data object StorageBufferDynamic : Feature

    data object Float64 : Feature
    data object Int64 : Feature
    data object AtomicOperations : Feature
    data object SubgroupOperations : Feature

    data object TimelineSemaphores : Feature

    data object Swapchain : Feature
    data object VsyncControl : Feature

    data object DebugMarkers : Feature
    data object GpuTimestamp : Feature
}
