//
// Created by cheerwizard on 18.10.25.
//

#ifndef COMMAND_BUFFER_HPP
#define COMMAND_BUFFER_HPP

#include "VkPipe.hpp"
#include "VkBufferResource.hpp"
#include "VkRenderTarget.hpp"
#include "VkSurface.hpp"
#include "VkDeviceQueue.hpp"

struct DrawCommand {
    u32 vertices = 0;
    u32 vertexOffset = 0;
    u32 instances = 1;
    u32 instanceOffset = 0;
};

struct DrawIndexedCommand {
    u32 vertices = 0;
    u32 vertexOffset = 0;
    u32 indices = 0;
    u32 indexOffset = 0;
    u32 instances = 1;
    u32 instanceOffset = 0;
};

struct DrawIndexedIndirectCommand {
    VkBuffer indirectBuffer;
    size_t offset = 0;
    u32 drawCount = 0;
};

struct RenderPassCommand {
    VkRenderTarget* renderTarget;
    u32 colorAttachmentIndex = 0;
};

struct AddToSubmitCommand {
    VkRenderTarget* secondary = nullptr;
    u32 secondaryCount = 0;
};

struct SubmitCommand {
    VkSemaphore waitSemaphore;
    VkSemaphore signalSemaphore;
    VkFence fence;
};

struct PresentCommand {
    VkSurface* surface = nullptr;
    VkSemaphore waitSemaphore;
};

struct CopyBufferCommand {
    VkBuffer srcBuffer;
    VkBuffer dstBuffer;
    size_t srcOffset = 0;
    size_t dstOffset = 0;
    size_t size = 0;
};

struct CopyBufferToImageCommand {
    VkBuffer srcBuffer;
    VkImage dstImage;
    u32 dstMipLevel = 0;
    u32 dstWidth = 0;
    u32 dstHeight = 0;
    u32 dstDepth = 0;
};

struct VkCommandBufferResource {
    VkCommandBuffer command_buffer = nullptr;
    VkDeviceQueue& device_queue;

    VkCommandBufferResource(VkDeviceQueue& device_queue, bool isPrimary);
    ~VkCommandBufferResource();

    void reset() const;
    void begin() const;
    void end();

    void beginRenderPass(const RenderPassCommand& command);
    void endRenderPass() const;

    void setPipeline(const VkPipeline& pipeline) const;
    void setVertexBuffer(const VkBufferResource& buffer) const;
    void setIndexBuffer(const VkBufferResource& buffer) const;
    void setResource(const VkPipeline& pipeline, const Resource& resource) const;

    void setViewport(const VkViewport& viewport) const;
    void setScissor(int x, int y, u32 w, u32 h) const;

    void draw(const DrawCommand& command) const;
    void drawIndexed(const DrawIndexedCommand& command) const;
    void drawIndexedIndirect(const DrawIndexedIndirectCommand& command) const;

    void addToSubmit(const AddToSubmitCommand& command) const;
    void submit(const SubmitCommand& command) const;

    bool present(const PresentCommand& command) const;

    void copyBuffer(const CopyBufferCommand& command) const;
    void copyBufferToImage(const CopyBufferToImageCommand& command) const;

private:
    static constexpr auto TAG = "VkCommandBufferResource";
};

#endif //COMMAND_BUFFER_HPP