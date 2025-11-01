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

    void Context::selectDevice() {
        device.New(DeviceCreateInfo {});
    }

    void Context::initSurface(const RenderConfig& render_config) {
        int result = wgpuCreateSurfaceFromCanvas(render_config.canvasID.c_str());
        CALL(result);
        surface = emscripten_webgpu_import_surface(result);
    }

    bool Context::checkExtension(const char *extension) {
        return false;
    }

    bool Context::checkLayer(const char *layer) {
        return false;
    }

}
