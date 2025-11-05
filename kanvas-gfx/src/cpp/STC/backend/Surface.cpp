//
// Created by cheerwizard on 01.11.25.
//

#include "Surface.hpp"
#include "Device.hpp"

namespace stc {

    Surface::Surface(Device &device, const SurfaceCreateInfo& create_info)
    : device(device) {
        if (!create_info.surface) return;
        initSurface(create_info.surface);
        swapchain = initSwapChain(create_info.width, create_info.height);
        initImages(create_info.width, create_info.height);
    }

    Surface::~Surface() {
        releaseImages();
        releaseSwapChain();
        releaseSurface();
    }

    void Surface::recreateSwapChain() {
        device.wait();
        u32 width = render_target->info.width;
        u32 height = render_target->info.height;
        auto newSwapchain = initSwapChain(width, height);
        releaseImages();
        releaseSwapChain();
        swapchain = newSwapchain;
        initImages(width, height);
        needsResize = false;
    }

}
