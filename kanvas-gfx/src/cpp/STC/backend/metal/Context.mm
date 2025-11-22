#include "../Context.hpp"
#import <Metal/Metal.h>

namespace stc {

    void Context::initInstance(const ContextCreateInfo& create_info) {
        // no-op
    }

    void Context::releaseInstance() {
        // no-op
    }

    void findDevice() {
        device.New(DeviceCreateInfo {});
    }

    void* findSurface(void* native_window, const RenderApiCreateInfo& render_api_create_info) {
        // no-op
    }

    bool checkExtension(const char* extension) {
        // no-op
    }

    bool checkLayer(const char* extension) {
        // no-op
    }

}