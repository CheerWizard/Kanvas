//
// Created by cheerwizard on 20.10.25.
//

#include "../Context.hpp"
#include "core/logger.hpp"

extern "C" {

    EM_JS(int, wgpuCreateAsync, (const char* selector), {
        return Asyncify.handleAsync(async () => {
            const canvas = document.querySelector(UTF8ToString(selector));
            if (!canvas) {
                web_log_error("WebGPU", allocateUTF8(`Failed to find canvas by id ${selector}`));
                return 0;
            }

            const adapter = await navigator.gpu.requestAdapter();
            if (!adapter) {
                web_log_error("WebGPU", allocateUTF8(`Failed to find suitable device adapter for this browser`));
                return 0;
            }

            const device = await adapter.requestDevice();
            if (!device) {
                web_log_error("WebGPU", allocateUTF8(`Failed to create device for this browser`));
                return 0;
            }

            const context = canvas.getContext('webgpu');

            context.configure({
              device: device,
              format: navigator.gpu.getPreferredCanvasFormat(),
              usage: GPUTextureUsage.RENDER_ATTACHMENT,
            });

            globalThis.__wgpu_device = device;
            globalThis.__wgpu_context = context;

            return 1;
        });
    });

    EM_JS(int, wgpuCreateSurfaceFromCanvas, (const char* selector), {
        const canvas = document.querySelector(UTF8ToString(selector));

        if (!canvas) {
            web_log_error("WebGPU", allocateUTF8(`Failed to find canvas by id ${selector}`));
            return 0;
        }

        const context = canvas.getContext("webgpu");
        const handle = Module.emscripten_webgpu_export_surface(context);
        return handle;
    });

}

namespace stc {

    void Context::initInstance(const ContextCreateInfo& create_info) {
        CALL(wgpuCreateAsync(create_info.render_config.canvasID.c_str()));
    }

    void Context::releaseInstance() {
        // no-op
    }

    void Context::initDevices() {
        // no-op
    }

    void Context::findDevice() {
        device.New(DeviceCreateInfo {});
    }

    void* Context::findSurface(void* native_window, const RenderApiCreateInfo& render_api_create_info) {
        int result = wgpuCreateSurfaceFromCanvas(render_api_create_info.canvas_id().c_str());
        CALL(result);
        return emscripten_webgpu_import_surface(result);
    }

    bool Context::checkExtension(const char *extension) {
        return false;
    }

    bool Context::checkLayer(const char *layer) {
        return false;
    }

}
