//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_RENDERCONFIG_HPP
#define STC_RENDERCONFIG_HPP

namespace stc {

    // must be reflected in Kotlin
    struct RenderConfig {
        void* nativeWindow = nullptr;
        int width = 800;
        int height = 600;
    };

}

#endif //STC_RENDERCONFIG_HPP