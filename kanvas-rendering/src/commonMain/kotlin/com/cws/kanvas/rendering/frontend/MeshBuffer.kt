package com.cws.kanvas.rendering.frontend

//struct MeshRegion {
//    u32 vertices = 0;
//    IndirectIndexData indirectData;
//};
//
//struct MeshBuffer {
//    Buffer vertexBuffer;
//    Buffer indexBuffer;
//
//    MeshBuffer(const Device& device, size_t vertices, size_t indices);
//
//    MeshRegion allocate(const Mesh& mesh);
//    void free(const MeshRegion& region);
//
//    private:
//    std::mutex mutex;
//    MemoryHeap vertexBufferHeap;
//    MemoryHeap indexBufferHeap;
//};

data class MeshRegion(
    val vertexCount: Int,
    val indirectIndexData: IndirectIndexData,
)