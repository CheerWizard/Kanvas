//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_API_H
#define VK_API_H

// expose all headers
#include "VkInfo.h"
#include "ResultBridge.h"
#include "LogBridge.h"

typedef struct VkContext* VkContextPtr;
typedef struct VkDeviceQueue* VkDeviceQueuePtr;
typedef struct VkBufferResource* VkBufferResourcePtr;
typedef struct VkSamplerResource* VkSamplerResourcePtr;
typedef struct VkTextureResource* VkTextureResourcePtr;
typedef struct VkFenceResource* VkFenceResourcePtr;
typedef struct VkSemaphoreResource* VkSemaphoreResourcePtr;
typedef struct VkShader* VkShaderPtr;
typedef struct VkRenderTarget* VkRenderTargetPtr;
typedef struct VkBindingLayout* VkBindingLayoutPtr;
typedef struct VkPipe* VkPipePtr;
typedef struct VkComputePipe* VkComputePipePtr;
typedef struct VkCommandBufferResource* VkCommandBufferResourcePtr;

#ifdef __cplusplus
extern "C" {
#endif

    VkContextPtr VkContext_create(void* native_window, VkContextInfo* info);
    void VkContext_destroy(VkContextPtr context);
    void VkContext_setInfo(VkContextPtr context, VkContextInfo* info);
    void VkContext_wait(VkContextPtr context);
    void VkContext_resize(VkContextPtr context, u32 width, u32 height);
    void VkContext_setSurface(VkContextPtr context, void* surface);
    VkRenderTargetPtr VkContext_getRenderTarget(VkContextPtr context);
    VkCommandBufferResourcePtr VkContext_getPrimaryCommandBuffer(VkContextPtr context, u32 frame);
    VkCommandBufferResourcePtr VkContext_getSecondaryCommandBuffer(VkContextPtr context);
    void VkContext_beginFrame(VkContextPtr context, u32 frame);
    void VkContext_endFrame(VkContextPtr context, u32 frame);

    VkShaderPtr VkShader_create(VkContextPtr context, VkShaderInfo* info);
    void VkShader_destroy(VkShaderPtr shader);
    void VkShader_setInfo(VkShaderPtr shader, VkShaderInfo* info);

    VkBindingLayoutPtr VkBindingLayout_create(VkContextPtr context, VkBindingInfo* info);
    void VkBindingLayout_destroy(VkBindingLayoutPtr layout);
    void VkBindingLayout_setInfo(VkBindingLayoutPtr layout, VkBindingInfo* info);

    VkRenderTargetPtr VkRenderTarget_create(VkContextPtr context, VkRenderTargetInfo* info);
    void VkRenderTarget_destroy(VkRenderTargetPtr render_target);
    void VkRenderTarget_setInfo(VkRenderTargetPtr render_target, VkRenderTargetInfo* info);
    void VkRenderTarget_resize(VkRenderTargetPtr render_target, u32 width, u32 height);

    VkBufferResourcePtr VkBufferResource_create(VkContextPtr context, VkBufferInfo* info);
    void VkBufferResource_destroy(VkBufferResourcePtr buffer_resource);
    void VkBufferResource_setInfo(VkBufferResourcePtr buffer_resource, VkBufferInfo* info);
    void* VkBufferResource_map(VkBufferResourcePtr buffer_resource, u32 frame);
    void VkBufferResource_unmap(VkBufferResourcePtr buffer_resource);

    VkSamplerResourcePtr VkSamplerResource_create(VkContextPtr context, VkSamplerInfo* info);
    void VkSamplerResource_destroy(VkSamplerResourcePtr sampler_resource);
    void VkSamplerResource_setInfo(VkSamplerResourcePtr sampler_resource, VkSamplerInfo* info);

    VkTextureResourcePtr VkTextureResource_create(VkContextPtr context, VkTextureInfo* info);
    void VkTextureResource_destroy(VkTextureResourcePtr texture_resource);
    void VkTextureResource_setInfo(VkTextureResourcePtr texture_resource, VkTextureInfo* info);
    void* VkTextureResource_map(VkTextureResourcePtr texture_resource, u32 frame);
    void VkTextureResource_unmap(VkTextureResourcePtr texture_resource);

    VkPipePtr VkPipe_create(VkContextPtr context, VkPipeInfo* info);
    void VkPipe_destroy(VkPipePtr pipe);
    void VkPipe_setInfo(VkPipePtr pipe, VkPipeInfo* info);

    VkComputePipePtr VkComputePipe_create(VkContextPtr context, VkComputePipeInfo* info);
    void VkComputePipe_destroy(VkComputePipePtr pipe);
    void VkComputePipe_setInfo(VkComputePipePtr pipe, VkComputePipeInfo* info);

    void VkCommandBufferResource_reset(VkCommandBufferResourcePtr command_buffer_resource);
    void VkCommandBufferResource_begin(VkCommandBufferResourcePtr command_buffer_resource);
    void VkCommandBufferResource_end(VkCommandBufferResourcePtr command_buffer_resource);
    void VkCommandBufferResource_beginRenderPass(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkRenderTargetPtr render_target,
        u32 surfaceImageIndex
    );
    void VkCommandBufferResource_endRenderPass(VkCommandBufferResourcePtr command_buffer_resource);
    void VkCommandBufferResource_setPipe(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkPipePtr pipe,
        u32 frame
    );
    void VkCommandBufferResource_setComputePipe(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkComputePipePtr pipe,
        u32 frame
    );
    void VkCommandBufferResource_setViewport(
        VkCommandBufferResourcePtr command_buffer_resource,
        float x, float y, float width, float height, float minDepth, float maxDepth
    );
    void VkCommandBufferResource_setScissor(
        VkCommandBufferResourcePtr command_buffer_resource,
        int x, int y, u32 w, u32 h
    );
    void VkCommandBufferResource_addSecondaryBuffer(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkCommandBufferResourcePtr secondary_buffer
    );
    void VkCommandBufferResource_addSecondaryBuffers(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkCommandBufferResourcePtr* secondary_buffers,
        size_t secondary_buffers_count
    );
    void VkCommandBufferResource_draw(
        VkCommandBufferResourcePtr command_buffer_resource,
        u32 vertices, u32 vertexOffset, u32 instances, u32 instanceOffset
    );
    void VkCommandBufferResource_drawIndexed(
        VkCommandBufferResourcePtr command_buffer_resource,
        u32 vertices, u32 vertexOffset, u32 indices, u32 indexOffset, u32 instances, u32 instanceOffset
    );
    void VkCommandBufferResource_drawIndexedIndirect(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkBufferResourcePtr indirectBuffer, size_t offset, u32 drawCount
    );
    void VkCommandBufferResource_copyBufferToBuffer(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkBufferResourcePtr srcBuffer, VkBufferResourcePtr dstBuffer, size_t srcOffset, size_t dstOffset, size_t size
    );
    void VkCommandBufferResource_copyBufferToImage(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkBufferResourcePtr srcBuffer, VkTextureResourcePtr dstImage, u32 dstMipLevel, u32 dstWidth, u32 dstHeight, u32 dstDepth
    );
    void VkCommandBufferResource_copyImageToImage(
        VkCommandBufferResourcePtr command_buffer_resource,
        VkTextureResourcePtr srcImage, VkTextureResourcePtr dstImage,
        int srcX, int srcY, int srcZ,
        int dstX, int dstY, int dstZ,
        u32 width, u32 height, u32 depth
    );

    void VkCommandBufferResource_dispatch(
        VkCommandBufferResourcePtr command_buffer_resource,
        u32 groupsX, u32 groupsY, u32 groupsZ
    );

    void VkCommandBufferResource_pipelineBarrier(
        VkCommandBufferResourcePtr command_buffer_resource,
        u32 srcStages, u32 dstStages,
        u32 srcAccessFlags, u32 dstAccessFlags
    );

#ifdef __cplusplus
}
#endif

#endif //VK_API_H
