//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_RENDERCONFIG_HPP
#define STC_RENDERCONFIG_HPP

namespace stc {

    // must be reflected in Kotlin
    struct RenderConfig {
        void* nativeWindow = nullptr;
        u32 width = 800;
        u32 height = 600;
        std::string canvasID;
    };

}

#endif //STC_RENDERCONFIG_HPP