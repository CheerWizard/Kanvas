//
// Created by cheerwizard on 01.11.25.
//

#include "Context.hpp"

namespace stc {

    Context::Context(const ContextCreateInfo& create_info) {
        initInstance(create_info);
        initDevices();
        selectDevice();
        initSurface(create_info.render_config.nativeWindow);
    }

    Context::~Context() {
        releaseInstance();
    }

    bool Context::checkExtensions(const char **extensions, uint32_t count) {
        for (int i = 0 ; i < count ; i++) {
            if (!checkExtension(extensions[i])) {
                return false;
            }
        }
        return true;
    }

    bool Context::checkLayers(const char **layers, uint32_t count) {
        for (int i = 0 ; i < count ; i++) {
            if (!checkLayer(layers[i])) {
                return false;
            }
        }
        return true;
    }

}