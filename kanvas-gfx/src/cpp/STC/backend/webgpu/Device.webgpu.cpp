//
// Created by cheerwizard on 25.10.25.
//

#include "../Device.hpp"

extern "C" {

    using namespace stc;

    EM_JS(void, wgpuSetupErrorCallbacks, (), {
      const device = globalThis.__wgpu_device;

      if (!device) {
        console.error("WGPU Device is not initialized!");
        return;
      }

      device.addEventListener('uncapturederror', (event) => {
        const err = event.error;
        const msg = err.message || "Unknown WebGPU error";
        const type = err.constructor.name;

        // Map to numeric enum for C side
        let errorType = 0;
        if (type.includes("ValidationError")) errorType = 1;
        else if (type.includes("OutOfMemoryError")) errorType = 2;
        else if (type.includes("Unknown")) errorType = 3;
        else if (type.includes("DeviceLost")) errorType = 4;

        // Call back into C
        const msgPtr = stringToUTF8OnStack(msg);
        Module.ccall('wgpuErrorCallback', null, ['number','string'], [errorType, msg]);
      });
    });

    EM_JS(void, wgpuSetupDeviceLostCallbacks, (), {
      const device = globalThis.__wgpu_device;
      if (!device) {
          console.error("WGPU Device is not initialized!");
          return;
      }

      device.lost.then((info) => {
        const msg = info.message || "Device lost";
        Module.ccall('wgpuErrorCallback', null, ['number','string'], [5, msg]);
      });
    });

    void wgpuErrorCallback(int errorType, const char* message) {
        switch ((WGPUErrorType) errorType) {
            case WGPUErrorType_NoError:
                LOG_WARNING("WGPUDebugger", "type=NoError message=%s", message);
                break;
            case WGPUErrorType_Validation:
                LOG_ERROR("WGPUDebugger", "type=Validation message=%s", message);
                break;
            case WGPUErrorType_OutOfMemory:
                LOG_ERROR("WGPUDebugger", "type=OutOfMemory message=%s", message);
                break;
            case WGPUErrorType_Unknown:
                LOG_ERROR("WGPUDebugger", "type=Unknown message=%s", message);
                break;
            case WGPUErrorType_DeviceLost:
                LOG_ERROR("WGPUDebugger", "type=DeviceLost message=%s", message);
                break;
            default:
                break;
        }
    }

}

namespace stc {

    Device::Device(const DeviceCreateInfo &create_info) {
        // wgpuAdapterGetProperties(adapter, &properties);
        // wgpuAdapterGetLimits(adapter, &supported_limits);
    }

    void Device::initialize() {
        handle = emscripten_webgpu_get_device();
        wgpuSetupErrorCallbacks();
        wgpuSetupDeviceLostCallbacks();
    }

    bool Device::checkExtension(const char *extension) {
        return false;
    }

    bool Device::checkLayer(const char *extension) {
        return false;
    }

    void Device::wait() {
        // no-op
    }

}