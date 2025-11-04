//
// Created by cheerwizard on 15.07.25.
//

#ifndef MESH_BUFFER_HPP
#define MESH_BUFFER_HPP

#include "IndirectBuffer.hpp"
#include "frontend/Vertex.hpp"

namespace stc {

    struct Mesh {
        std::vector<Vertex> vertices;
        std::vector<u32> indices;
    };

    struct MeshRegion {
        u32 vertices = 0;
        IndirectIndexData indirectData;
    };

    struct MeshBuffer {
        Buffer vertexBuffer;
        Buffer indexBuffer;

        MeshBuffer(const Device& device, size_t vertices, size_t indices);

        MeshRegion allocate(const Mesh& mesh);
        void free(const MeshRegion& region);

    private:
        std::mutex mutex;
        MemoryHeap vertexBufferHeap;
        MemoryHeap indexBufferHeap;
    };

}

#endif //MESH_BUFFER_HPP
