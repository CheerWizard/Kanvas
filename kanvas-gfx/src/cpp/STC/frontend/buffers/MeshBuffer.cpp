//
// Created by cheerwizard on 15.07.25.
//

#include "MeshBuffer.hpp"

#include "../Frame.hpp"

namespace stc {

    MeshBuffer::MeshBuffer(const Device& device, size_t vertices, size_t indices) :
    vertexBuffer(device, MEMORY_TYPE_DEVICE_LOCAL, BUFFER_USAGE_TRANSFER_DST | BUFFER_USAGE_VERTEX_BUFFER, sizeof(Vertex) * vertices),
    indexBuffer(device, MEMORY_TYPE_DEVICE_LOCAL, BUFFER_USAGE_TRANSFER_DST | BUFFER_USAGE_INDEX_BUFFER, sizeof(u32) * indices) {}

    MeshRegion MeshBuffer::allocate(const Mesh &mesh) {
        std::unique_lock lock(mutex);
        uint32_t vertexSize = mesh.vertices.size() * sizeof(Vertex);
        uint32_t vertexOffset = vertexBufferHeap.allocate(vertexSize);
        uint32_t indexSize = mesh.indices.size() * sizeof(uint32_t);
        uint32_t indexOffset = indexBufferHeap.allocate(indexSize);
        return MeshRegion {
            .vertices = vertexSize,
            .indirectData = {
                .indices = indexSize,
                .indexOffset = indexOffset,
                .vertexOffset = vertexOffset,
            }
        };
    }

    void MeshBuffer::free(const MeshRegion &region) {
        std::unique_lock lock(mutex);
        vertexBufferHeap.free(region.indirectData.vertexOffset, region.vertices);
        indexBufferHeap.free(region.indirectData.indexOffset, region.indirectData.indices);
    }

}