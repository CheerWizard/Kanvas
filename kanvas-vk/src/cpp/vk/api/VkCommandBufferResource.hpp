//
// Created by cheerwizard on 18.10.25.
//

#ifndef COMMAND_BUFFER_HPP
#define COMMAND_BUFFER_HPP

#include "VkRenderTarget.hpp"
#include "VkSurface.hpp"

struct VkCommandBufferResource {
    VkCommandBuffer command_buffer = nullptr;
    VkDeviceQueue& device_queue;

    VkCommandBufferResource(VkDeviceQueue& device_queue, bool isPrimary);
    ~VkCommandBufferResource();

    void reset() const;
    void begin() const;
    void end();

    void beginRenderPass(VkRenderTarget* render_target, u32 colorAttachmentIndex);
    void endRenderPass() const;

    void setPipe(VkPipe* pipe) const;
    void setPipeline(VkPipelineBindPoint pipeline_bind_point, VkPipeline pipeline) const;
    void setVertexBuffer(VkBufferResource* buffer) const;
    void setIndexBuffer(VkBufferResource* buffer) const;
    void setDescriptorSet(
        VkPipelineBindPoint pipeline_bind_point,
        VkPipelineLayout pipeline_layout,
        VkDescriptorSet descriptorSet,
        uint32_t set
    ) const;

    void setViewport(float x, float y, float width, float height, float minDepth, float maxDepth) const;
    void setScissor(int x, int y, u32 w, u32 h) const;

    void draw(u32 vertices, u32 vertexOffset, u32 instances, u32 instanceOffset) const;
    void drawIndexed(u32 vertices, u32 vertexOffset, u32 indices, u32 indexOffset, u32 instances, u32 instanceOffset) const;
    void drawIndexedIndirect(VkBufferResource* indirectBuffer, size_t offset, u32 drawCount) const;

    void addSecondaryBuffer(VkCommandBufferResource* secondaryBuffer) const;
    void addSecondaryBuffers(VkCommandBufferResource** secondaryBuffers, size_t secondaryBuffersCount) const;
    void submit(VkSemaphoreResource* waitSemaphore, VkSemaphoreResource* signalSemaphore, VkFenceResource* fence) const;

    bool present(VkSurface* surface, VkSemaphoreResource* waitSemaphore) const;

    void copyBufferToBuffer(VkBufferResource* srcBuffer, VkBufferResource* dstBuffer, size_t srcOffset, size_t dstOffset, size_t size) const;
    void copyBufferToImage(VkBufferResource* srcBuffer, VkTextureResource* dstImage, u32 dstMipLevel, u32 dstWidth, u32 dstHeight, u32 dstDepth) const;
    void copyImageToImage(
        VkTextureResource* srcImage,
        VkTextureResource* dstImage,
        int srcX, int srcY, int srcZ,
        int dstX, int dstY, int dstZ,
        u32 width, u32 height, u32 depth
    ) const;

private:
    static constexpr auto TAG = "VkCommandBufferResource";
};

#endif //COMMAND_BUFFER_HPP