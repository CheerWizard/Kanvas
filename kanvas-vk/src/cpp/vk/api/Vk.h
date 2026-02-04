//
// Created by Vitalii Andrusyshyn on 22.01.2026.
//

#ifndef VK_API_H
#define VK_API_H

#include "VkInfo.h"

typedef struct VkContext VkContext;
typedef struct VkDeviceQueue VkDeviceQueue;
typedef struct VkBufferResource VkBufferResource;
typedef struct VkSamplerResource VkSamplerResource;
typedef struct VkTextureResource VkTextureResource;
typedef struct VkFenceResource VkFenceResource;
typedef struct VkSemaphoreResource VkSemaphoreResource;
typedef struct VkShader VkShader;
typedef struct VkRenderTarget VkRenderTarget;
typedef struct VkBindingLayout VkBindingLayout;
typedef struct VkPipe VkPipe;
typedef struct VkCommandBufferResource VkCommandBufferResource;

extern "C" {

    VkContext* VkContext_create(void* native_window, VkContextInfo* info);
    void VkContext_destroy(VkContext* context);
    void VkContext_setInfo(VkContext* context, VkContextInfo* info);
    void VkContext_wait(VkContext* context);
    void VkContext_resize(VkContext* context, u32 width, u32 height);
    void VkContext_setSurface(VkContext* context, void* surface);
    VkRenderTarget* VkContext_getRenderTarget(VkContext* context);
    VkCommandBufferResource* VkContext_getPrimaryCommandBuffer(VkContext* context, u32 frame);
    VkCommandBufferResource* VkContext_getSecondaryCommandBuffer(VkContext* context);
    void VkContext_beginFrame(VkContext* context, u32 frame);
    void VkContext_endFrame(VkContext* context, u32 frame);

    VkShader* VkShader_create(VkContext* context, VkShaderInfo* info);
    void VkShader_destroy(VkShader* shader);
    void VkShader_setInfo(VkShader* shader, VkShaderInfo* info);

    VkBindingLayout* VkBindingLayout_create(VkContext* context, VkBindingInfo* info);
    void VkBindingLayout_destroy(VkBindingLayout* layout);
    void VkBindingLayout_setInfo(VkBindingLayout* layout, VkBindingInfo* info);

    VkRenderTarget* VkRenderTarget_create(VkContext* context, VkRenderTargetInfo* info);
    void VkRenderTarget_destroy(VkRenderTarget* render_target);
    void VkRenderTarget_setInfo(VkRenderTarget* render_target, VkRenderTargetInfo* info);
    void VkRenderTarget_resize(VkRenderTarget* render_target, u32 width, u32 height);

    VkBufferResource* VkBufferResource_create(VkContext* context, VkBufferInfo* info);
    void VkBufferResource_destroy(VkBufferResource* buffer_resource);
    void VkBufferResource_setInfo(VkBufferResource* buffer_resource, VkBufferInfo* info);
    void* VkBufferResource_map(VkBufferResource* buffer_resource, u32 frame);
    void VkBufferResource_unmap(VkBufferResource* buffer_resource);

    VkSamplerResource* VkSamplerResource_create(VkContext* context, VkSamplerInfo* info);
    void VkSamplerResource_destroy(VkSamplerResource* sampler_resource);
    void VkSamplerResource_setInfo(VkSamplerResource* sampler_resource, VkSamplerInfo* info);

    VkTextureResource* VkTextureResource_create(VkContext* context, VkTextureInfo* info);
    void VkTextureResource_destroy(VkTextureResource* texture_resource);
    void VkTextureResource_setInfo(VkTextureResource* texture_resource, VkTextureInfo* info);
    void* VkTextureResource_map(VkTextureResource* texture_resource, u32 frame);
    void VkTextureResource_unmap(VkTextureResource* texture_resource);

    VkPipe* VkPipe_create(VkContext* context, VkPipeInfo* info);
    void VkPipe_destroy(VkPipe* pipe);
    void VkPipe_setInfo(VkPipe* pipe, VkPipeInfo* info);

    void VkCommandBufferResource_reset(VkCommandBufferResource* command_buffer_resource);
    void VkCommandBufferResource_begin(VkCommandBufferResource* command_buffer_resource);
    void VkCommandBufferResource_end(VkCommandBufferResource* command_buffer_resource);
    void VkCommandBufferResource_beginRenderPass(
        VkCommandBufferResource* command_buffer_resource,
        VkRenderTarget* render_target,
        u32 colorAttachmentIndex
    );
    void VkCommandBufferResource_endRenderPass(VkCommandBufferResource* command_buffer_resource);
    void VkCommandBufferResource_setPipe(
        VkCommandBufferResource* command_buffer_resource,
        VkPipe* pipe
    );
    void VkCommandBufferResource_setViewport(
        VkCommandBufferResource* command_buffer_resource,
        float x, float y, float width, float height, float minDepth, float maxDepth
    );
    void VkCommandBufferResource_setScissor(
        VkCommandBufferResource* command_buffer_resource,
        int x, int y, u32 w, u32 h
    );
    void VkCommandBufferResource_addSecondaryBuffer(
        VkCommandBufferResource* command_buffer_resource,
        VkCommandBufferResource* secondary_buffer
    );
    void VkCommandBufferResource_addSecondaryBuffers(
        VkCommandBufferResource* command_buffer_resource,
        VkCommandBufferResource** secondary_buffers,
        size_t secondary_buffers_count
    );
    void VkCommandBufferResource_draw(
        VkCommandBufferResource* command_buffer_resource,
        u32 vertices, u32 vertexOffset, u32 instances, u32 instanceOffset
    );
    void VkCommandBufferResource_drawIndexed(
        VkCommandBufferResource* command_buffer_resource,
        u32 vertices, u32 vertexOffset, u32 indices, u32 indexOffset, u32 instances, u32 instanceOffset
    );
    void VkCommandBufferResource_drawIndexedIndirect(
        VkCommandBufferResource* command_buffer_resource,
        VkBufferResource* indirectBuffer, size_t offset, u32 drawCount
    );
    void VkCommandBufferResource_copyBufferToBuffer(
        VkCommandBufferResource* command_buffer_resource,
        VkBufferResource* srcBuffer, VkBufferResource* dstBuffer, size_t srcOffset, size_t dstOffset, size_t size
    );
    void VkCommandBufferResource_copyBufferToImage(
        VkCommandBufferResource* command_buffer_resource,
        VkBufferResource* srcBuffer, VkTextureResource* dstImage, u32 dstMipLevel, u32 dstWidth, u32 dstHeight, u32 dstDepth
    );
    void VkCommandBufferResource_copyImageToImage(
        VkCommandBufferResource* command_buffer_resource,
        VkTextureResource* srcImage, VkTextureResource* dstImage,
        int srcX, int srcY, int srcZ,
        int dstX, int dstY, int dstZ,
        u32 width, u32 height, u32 depth
    );

}

#endif //VK_API_H
