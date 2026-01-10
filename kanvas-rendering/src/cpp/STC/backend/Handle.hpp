//
// Created by cheerwizard on 22.07.25.
//

#ifndef HANDLE_HPP
#define HANDLE_HPP

#ifdef VK

#include "vk/Handle.vk.hpp"

#elif METAL

#include "metal/Handle.metal.hpp"

#elif WEBGPU

#include "webgpu/Handle.webgpu.hpp"

#else

#error "Unknown GPU Backend!"

#endif

namespace stc {

    enum MemoryType {
        MEMORY_TYPE_HOST,
        MEMORY_TYPE_DEVICE_LOCAL
    };

}

#endif //HANDLE_HPP