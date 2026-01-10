//
// Created by cheerwizard on 18.10.25.
//

#ifndef INDIRECT_BUFFER_HPP
#define INDIRECT_BUFFER_HPP

#include "backend/Buffer.hpp"

namespace stc {

    struct IndirectIndexData {
        u32 indices = 0;
        u32 instances = 1;
        u32 indexOffset = 0;
        u32 vertexOffset = 0;
        u32 instanceOffset = 0;
    };

    struct IndirectIndexBuffer : Buffer {
        u32 count = 0;

        IndirectIndexBuffer(const Device& device, u32 count);

        void update(u32 frame, u32 offset, const IndirectIndexData& data) const;
        void update(u32 frame, u32 offset, IndirectIndexData* data, u32 count) const;
    };

}

#endif //INDIRECT_BUFFER_HPP