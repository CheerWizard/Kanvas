@file:OptIn(ExperimentalJsCollectionsApi::class, ExperimentalJsExport::class)

package com.cws.kanvas.rendering.backend

import com.cws.kanvas.wgpu.gpu.GPUBlendComponent
import com.cws.kanvas.wgpu.gpu.GPUBlendState
import com.cws.kanvas.wgpu.gpu.GPUColorTargetState
import com.cws.kanvas.wgpu.gpu.GPUColorWrite
import com.cws.kanvas.wgpu.gpu.GPUDepthStencilState
import com.cws.kanvas.wgpu.gpu.GPUFragmentState
import com.cws.kanvas.wgpu.gpu.GPUMultisampleState
import com.cws.kanvas.wgpu.gpu.GPUPipelineLayoutDescriptor
import com.cws.kanvas.wgpu.gpu.GPUPrimitiveState
import com.cws.kanvas.wgpu.gpu.GPURenderPipelineDescriptor
import com.cws.kanvas.wgpu.gpu.GPUTextureFormat
import com.cws.kanvas.wgpu.gpu.GPUVertexAttribute
import com.cws.kanvas.wgpu.gpu.GPUVertexBufferLayout
import com.cws.kanvas.wgpu.gpu.GPUVertexState
import com.cws.kanvas.wgpu.gpu.GPUVertexStepMode
import com.cws.kanvas.wgpu.gpu.depth24plusStencil8
import com.cws.kanvas.wgpu.gpu.instance
import com.cws.kanvas.wgpu.gpu.jsArrayOf
import com.cws.kanvas.wgpu.gpu.toGPUSize64
import com.cws.kanvas.wgpu.gpu.vertex

actual class RenderPipeline actual constructor(
    private val context: RenderContext,
    info: PipelineInfo
) : Resource<RenderPipelineHandle, PipelineInfo>(info) {

    actual override fun onCreate() {
        context.call { device ->
            val vertexState = createVertexState() ?: return@call

            val vertexBindingLayouts = info.vertexShader?.info?.bindingLayouts?.list
                ?.filter { it.value != null }
                ?.map { it.value!! }
                .orEmpty()

            val fragmentBindingLayouts = info.fragmentShader?.info?.bindingLayouts?.list
                ?.filter { it.value != null }
                ?.map { it.value!! }
                .orEmpty()

            val geometryBindingLayouts = info.geometryShader?.info?.bindingLayouts?.list
                ?.filter { it.value != null }
                ?.map { it.value!! }
                .orEmpty()

            val bindingLayouts = (vertexBindingLayouts + fragmentBindingLayouts + geometryBindingLayouts)
                .distinct()
                .asJsReadonlyArrayView()

            val pipelineLayout = device.createPipelineLayout(GPUPipelineLayoutDescriptor(
                label = "${info.name.value}-PipelineLayout",
                bindGroupLayouts = bindingLayouts,
            ))

            device.createRenderPipelineAsync(GPURenderPipelineDescriptor(
                label = "${info.name.value}-Pipeline",
                layout = pipelineLayout,
                vertex = vertexState,
                fragment = createFragmentState(),
                primitive = createPrimitiveState(),
                depthStencil = createDepthStencilState(),
                multisample = createMultisampleState(),
            )).then { pipeline ->
                handle = RenderPipelineHandle(pipeline)
            }
        }
    }

    private fun createVertexState(): GPUVertexState? {
        val vertexShader = info.vertexShader ?: return null

        var stride = 0
        val attributes = info.vertexAttributes.list.map { attribute ->
            val gpuAttribute = GPUVertexAttribute(
                shaderLocation = attribute.location,
                format = attribute.format.jsValue,
                offset = stride.toGPUSize64(),
            )
            stride += attribute.type.value * 4
            gpuAttribute
        }

        return GPUVertexState(
            module = vertexShader.handle?.value ?: return null,
            entryPoint = vertexShader.info.entryPoint.value,
            buffers = listOf(GPUVertexBufferLayout(
                arrayStride = stride.toGPUSize64(),
                attributes = attributes,
                stepMode = if (info.instanced) GPUVertexStepMode.instance else GPUVertexStepMode.vertex,
            ))
        )
    }

    private fun createFragmentState(): GPUFragmentState? {
        val fragmentShader = info.fragmentShader ?: return null

        val colorStates = info.renderTarget?.info?.colorAttachments?.list
            ?.filter { colorAttachment -> colorAttachment.texture != null }
            ?.map { colorAttachment ->
                GPUColorTargetState(
                    format = colorAttachment.texture!!.info.format.jsValue,
                    blend = colorAttachment.blend.toGPUBlendState(),
                    writeMask = GPUColorWrite.ALL,
                )
            }
            .orEmpty()
            .asJsReadonlyArrayView()

        return GPUFragmentState(
            module = fragmentShader.handle?.value ?: return null,
            entryPoint = fragmentShader.info.entryPoint.value,
            targets = colorStates,
        )
    }

    private fun createPrimitiveState(): GPUPrimitiveState {
        return GPUPrimitiveState(
            cullMode = info.cullMode.jsValue,
            frontFace = info.frontFace.jsValue,
            topology = info.primitiveTopology.jsValue,
        )
    }

    private fun createDepthStencilState(): GPUDepthStencilState? {
        val depthAttachment = info.renderTarget?.info?.depthAttachment ?: return null
        if (!depthAttachment.enabled) return null
        return GPUDepthStencilState(
            format = depthAttachment.texture?.info?.format?.jsValue ?: GPUTextureFormat.depth24plusStencil8,
            depthWriteEnabled = depthAttachment.depthWriteEnabled,
            depthCompare = depthAttachment.depthCompareOp.jsValue,
        )
    }

    private fun createMultisampleState(): GPUMultisampleState? {
        if (info.sampleCount == 1) return null
        return GPUMultisampleState(
            alphaToCoverageEnabled = false,
            count = info.sampleCount,
            mask = 0xFFFFFF,
        )
    }

    private fun Blend.toGPUBlendState(): GPUBlendState? {
        if (!enable) return null
        return GPUBlendState(
            alpha = GPUBlendComponent(
                srcFactor = srcFactorAlpha.jsValue,
                dstFactor = dstFactorAlpha.jsValue,
                operation = blendOpAlpha.jsValue,
            ),
            color = GPUBlendComponent(
                srcFactor = srcFactorColor.jsValue,
                dstFactor = dstFactorColor.jsValue,
                operation = blendOpColor.jsValue,
            ),
        )
    }

    actual override fun onDestroy() {
        // no-op
    }

    actual override fun setInfo() {
        onCreate()
    }

}