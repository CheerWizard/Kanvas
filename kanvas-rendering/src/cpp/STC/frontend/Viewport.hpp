//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_VIEWPORT_HPP
#define STC_VIEWPORT_HPP

namespace stc {

    struct Viewport {
        float x = 0.0f;
        float y = 0.0f;
        u32 width = 0;
        u32 height = 0;
        float minDepth = 0.0f;
        float maxDepth = 1.0f;

        Viewport() = default;
        Viewport(u32 width, u32 height) : width(width), height(height) {}
    };

}

#endif //STC_VIEWPORT_HPP