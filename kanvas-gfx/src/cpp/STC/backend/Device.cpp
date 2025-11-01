//
// Created by cheerwizard on 25.10.25.
//

#include "Device.hpp"

namespace stc {

    Device::~Device() {
        queues.clear();
        Delete();
    }

    Scope<DeviceQueue>& Device::getQueue(u32 family_index) {
        std::lock_guard lock(queueMutex);

        auto thread_id = std::this_thread::get_id();

        const auto it = queues.find(thread_id);
        if (it != queues.end()) {
            return it->second;
        }

        queues.emplace(
            std::piecewise_construct,
            std::forward_as_tuple(thread_id),
            std::forward_as_tuple(*this, family_index)
        );

        return queues[thread_id];
    }

    bool Device::checkExtensions(const char **extensions, u32 count) {
        for (int i = 0 ; i < count ; i++) {
            if (!checkExtension(extensions[i])) {
                return false;
            }
        }
        return true;
    }

    bool Device::checkLayers(const char **layers, u32 count) {
        for (int i = 0 ; i < count ; i++) {
            if (!checkLayer(layers[i])) {
                return false;
            }
        }
        return true;
    }

}