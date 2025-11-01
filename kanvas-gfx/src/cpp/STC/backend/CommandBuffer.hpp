//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_COMMAND_BUFFER_HPP
#define STC_COMMAND_BUFFER_HPP

#include "Binding.hpp"
#include "Buffer.hpp"
#include "RenderTarget.hpp"
#include "Handle.hpp"
#include "Surface.hpp"

namespace stc {

#ifdef VK

    struct CommandBufferBackend {
        VkCommandBuffer handle = null;
    };

#elif METAL



#elif WEBGPU

    struct CommandBufferBackend {
        WGPUCommandBuffer handle = null;
    };

#endif

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
        BufferBackend indirectBuffer;
        size_t offset = 0;
        u32 drawCount = 0;
    };

    struct RenderPassCommand {
        RenderTargetBackend renderTarget;
        vec4<float> clearColor = { 0, 0, 0, 1 };
        float clearDepth = 0;
        int renderAreaX = 0;
        int renderAreaY = 0;
        u32 renderAreaW = 0;
        u32 renderAreaH = 0;
    };

    struct AddToSubmitCommand {
        CommandBufferHandle* secondary = nullptr;
        u32 secondaryCount = 0;
    };

    struct SubmitCommand {
        SemaphoreBackend waitSemaphore;
        SemaphoreBackend signalSemaphore;
        FenceBackend fence;
    };

    struct PresentCommand {
        SwapchainHandle swapchain;
        u32 imageIndex = 0;
        SemaphoreBackend waitSemaphore;
    };

    struct CopyBufferCommand {
        BufferBackend srcBuffer;
        size_t srcOffset = 0;
        BufferBackend dstBuffer;
        size_t dstOffset = 0;
        size_t size = 0;
    };

    struct CopyBufferToImageCommand {
        BufferBackend srcBuffer;
        TextureBackend dstImage;
        u32 dstMipLevel = 0;
        u32 dstWidth = 0;
        u32 dstHeight = 0;
        u32 dstDepth = 0;
    };

    struct Viewport;
    struct DeviceQueue;
    struct Buffer;
    struct Pipeline;

    struct CommandBuffer : CommandBufferBackend {
        DeviceQueue& device_queue;

        CommandBuffer(DeviceQueue& device_queue, bool isPrimary);
        ~CommandBuffer();

        void reset() const;
        void begin() const;
        void end() const;

        void beginRenderPass(const RenderPassCommand& command) const;
        void endRenderPass() const;

        void setPipeline(const Pipeline& pipeline) const;
        void setVertexBuffer(const Buffer& buffer) const;
        void setIndexBuffer(const Buffer& buffer) const;
        void setPipelineBinding(const Pipeline& pipeline, const BindingSet& binding_set) const;

        void setViewport(const Viewport& viewport) const;
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
        static constexpr auto TAG = "CommandBuffer";
    };

}

#endif //STC_COMMAND_BUFFER_HPP