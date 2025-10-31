//
// Created by cheerwizard on 25.10.25.
//

#include "../Device.hpp"

namespace stc {

    Device::Device(const DeviceCreateInfo &create_info) {

    }

    Device::~Device() {
        if (handle) {
            wgpuDeviceRelease(handle);
            handle = null;
        }
    }

    void Device::wait() {
        // no-op
    }

    void Device::initialize() {

    }

    bool Device::checkExtension(const char *extension) {
        for (auto& entry : features) {
            if (strcmp(extension, entry) == 0) {
                return true;
            }
        }
        return false;
    }

    bool Device::checkExtensions(const char **extensions, u32 count) {
        for (int i = 0 ; i < count ; i++) {
            if (!checkExtension(extensions[i])) {
                return false;
            }
        }
        return true;
    }

    bool Device::checkLayer(const char *layer) {
        return false;
    }

    bool Device::checkLayers(const char **layers, u32 count) {
        return false;
    }

}