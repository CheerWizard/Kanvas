//
// Created by cheerwizard on 01.11.25.
//

#include "Surface.hpp"
#include "Device.hpp"

namespace stc {

    Surface::Surface(Device &device, void* surface, u32 width, u32 height)
    : device(device), width(width), height(height) {
        if (!surface) return;
        initSurface(surface);
        swapchain = initSwapChain(width, height);
        initImages(width, height);
    }

    Surface::~Surface() {
        releaseImages();
        releaseSwapChain();
        releaseSurface();
    }

    void Surface::recreateSwapChain() {
        device.wait();
        u32 width = this->width;
        u32 height = this->height;
        auto newSwapchain = initSwapChain(width, height);
        releaseImages();
        releaseSwapChain();
        swapchain = newSwapchain;
        initImages(width, height);
        needsResize = false;
    }

}
