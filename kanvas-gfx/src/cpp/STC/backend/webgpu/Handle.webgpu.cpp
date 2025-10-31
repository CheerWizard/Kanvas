//
// Created by cheerwizard on 25.10.25.
//

#include "Handle.webgpu.hpp"
#include "core/logger.hpp"

namespace stc {

    void SwapchainHandle::New(WGPUDevice device, WGPUSurface surface, const WGPUSwapChainDescriptor &descriptor) {
        handle = wgpuDeviceCreateSwapChain(device, surface, &descriptor);
        ASSERT_HANDLE(SwapchainHandle);
    }

    void SwapchainHandle::Delete() {
        if (handle) {
            wgpuSwapChainRelease(handle);
            handle = null;
        }
    }

    void TextureViewHandle::New(WGPUTexture texture, const WGPUTextureViewDescriptor &descriptor) {
        handle = wgpuTextureCreateView(texture, &descriptor);
        ASSERT_HANDLE(TextureViewHandle);
    }

    void TextureViewHandle::Delete() {
        if (handle) {
            wgpuTextureViewRelease(handle);
            handle = null;
        }
    }

    void SurfaceHandle::New(WGPUInstance instance, const WGPUSurfaceDescriptor &descriptor) {
        handle = wgpuInstanceCreateSurface(instance, &descriptor);
        ASSERT_HANDLE(SurfaceHandle);
    }

    void SurfaceHandle::Delete() {
        if (handle) {
            wgpuSurfaceRelease(handle);
            handle = null;
        }
    }

    void QueueHandle::New(WGPUDevice device) {
        handle = wgpuDeviceGetQueue(device);
        ASSERT_HANDLE(QueueHandle);
    }

    void QueueHandle::Delete() {
        if (handle) {
            wgpuQueueRelease(handle);
            handle = null;
        }
    }

    void RenderPassHandle::New(WGPUCommandEncoder command_encoder, const WGPURenderPassDescriptor &descriptor) {
        handle = wgpuCommandEncoderBeginRenderPass(command_encoder, &descriptor);
        ASSERT_HANDLE(RenderPassHandle);
    }

    void RenderPassHandle::Delete() {
        if (handle) {
            wgpuRenderPassEncoderRelease(handle);
            handle = null;
        }
    }

}
