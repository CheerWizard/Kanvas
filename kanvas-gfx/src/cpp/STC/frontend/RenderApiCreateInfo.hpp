//
// Created by cheerwizard on 17.11.25.
//

#ifndef STC_RENDERAPICREATEINFO_HPP
#define STC_RENDERAPICREATEINFO_HPP

namespace stc {

    struct RenderApiCreateInfo {
        void* nativeWindow = nullptr;
        u32 width = 800;
        u32 height = 600;
        std::string canvasID;
    };

}

#endif //STC_RENDERAPICREATEINFO_HPP