//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_SYNC_HPP
#define STC_SYNC_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    struct FenceBackend {
        FenceHandle handle;
    };

    struct SemaphoreBackend {
        SemaphoreHandle handle;
    };

#elif METAL

    struct FenceBackend {
        FenceHandle handle;
    };

    struct SemaphoreBackend {};

#elif WEBGPU

    struct FenceBackend {};

    struct SemaphoreBackend {};

#endif

    struct Device;

    struct Fence : FenceBackend {

        Fence(const Device& device, bool signaled);
        ~Fence();

        void wait(u64 timeout = UINT64_MAX);
        void reset();

    };

    struct Semaphore : SemaphoreBackend {
        Semaphore(const Device& device);
        ~Semaphore();
    };

}

#endif //STC_SYNC_HPP