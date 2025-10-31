#include "../RenderBridge.hpp"

static stc::RenderBridge* render_bridge = nullptr;

extern "C" void RenderBridge_nativeInit(
        void* nativeWindow,
        int width,
        int height
) {
    render_bridge = new stc::RenderBridge({
        .nativeWindow = nativeWindow,
        .width = width,
        .height = height,
    });
}

extern "C" void RenderBridge_nativeFree() {
    delete render_bridge;
    render_bridge = nullptr;
}

extern "C" void RenderBridge_nativeResize(int width, int height) {
    render_bridge->resize(width, height);
}

extern "C" void RenderBridge_nativeBeginFrame() {
    render_bridge->beginFrame();
}

extern "C" void RenderBridge_nativeEndFrame() {
    render_bridge->endFrame();
}