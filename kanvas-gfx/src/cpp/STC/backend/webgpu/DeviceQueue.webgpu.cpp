//
// Created by cheerwizard on 25.10.25.
//

#include "../DeviceQueue.hpp"
#include "backend/Device.hpp"

namespace stc {

    DeviceQueue::DeviceQueue(const Device &device, u32 familyIndex) {
        this->device = device.handle;
        New(device.handle);
    }

    DeviceQueue::~DeviceQueue() {
        Delete();
    }

    void DeviceQueue::reset() {
        handle = wgpuDeviceGetQueue(device);
        ASSERT_HANDLE(handle);
    }

}
