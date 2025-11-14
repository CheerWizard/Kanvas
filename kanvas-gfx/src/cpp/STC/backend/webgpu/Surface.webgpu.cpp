//
// Created by cheerwizard on 18.10.25.
//

#include "../Surface.hpp"

extern "C" {

    EM_JS(int, wgpuGetCurrentTexture, (int surfaceHandle), {
        const context = Module.emscripten_webgpu_import_surface(surfaceHandle);
        const texture = context.getCurrentTexture();
        return Module.emscripten_webgpu_export_texture(texture);
    });

}

namespace stc {

    void Surface::initSurface(void* surface) {
        handle = (WGPUSurface) surface;
    }

    void Surface::releaseSurface() {
        if (handle) {
            wgpuSurfaceRelease(handle);
            handle = null;
        }
    }

    SwapchainHandle Surface::initSwapChain(u32 width, u32 height) const {
        // no-op
        return {};
    }

    void Surface::releaseSwapChain() {
        // no-op
    }

    void Surface::resize(int width, int height) {
        this->width = width;
        this->height = height;
        needsResize = true;
    }

    bool Surface::getImage(const Semaphore &semaphore) {
        WGPUTexture texture = emscripten_webgpu_import_texture(wgpuGetCurrentTexture((int) handle));
        texture_view = wgpuTextureCreateView(texture, nullptr);
        return textureView != null;
    }

    void Surface::initImages(u32 width, u32 height) {
        // no-op
    }

    void Surface::releaseImages() {
        // no-op
    }

    void Surface::present(const CommandBuffer &command_buffer) {
        // no-op
    }

}