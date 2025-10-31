//
// Created by cheerwizard on 18.10.25.
//

#include "IndirectBuffer.hpp"

namespace stc {

    IndirectIndexBuffer::IndirectIndexBuffer(u32 count) : count(count), Buffer(
        MEMORY_TYPE_HOST,
        BUFFER_USAGE_INDIRECT_BUFFER,
        sizeof(IndirectIndexData) * count * MAX_FRAMES
    ) {}

    void IndirectIndexBuffer::update(u32 frame, u32 offset, const IndirectIndexData &data) const {
        memcpy(PTR_OFFSET(IndirectIndexData), &data, sizeof(IndirectIndexData));
    }

    void IndirectIndexBuffer::update(u32 frame, u32 offset, IndirectIndexData *data, u32 count) const {
        memcpy(PTR_OFFSET(IndirectIndexData), data, sizeof(IndirectIndexData) * count);
    }

}
