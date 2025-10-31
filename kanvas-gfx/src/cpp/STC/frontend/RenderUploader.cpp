//
// Created by cheerwizard on 15.07.25.
//

#include "RenderUploader.hpp"

#include "Frame.hpp"

stc::RenderUploader::RenderUploader(Renderer &renderer) : renderer(renderer) {
    threadPool.New(4);
}

stc::RenderUploader::~RenderUploader() {
    commandBuffers.clear();
}

void stc::RenderUploader::uploadMesh(const Mesh &mesh, bool& ready, const std::function<void(const Command&)>& onDone) {
    ready = false;
    threadPool->submit({
        .task = [&, this, mesh]() {
            Buffer stagingVertexBuffer = {
                MEMORY_TYPE_HOST,
                BUFFER_USAGE_TRANSFER_SRC,
                sizeof(Vertex) * mesh.vertices.size(),
            };
            void* stageVertexMemory = stagingVertexBuffer.map();
            memcpy(stageVertexMemory, mesh.vertices.data(), sizeof(Vertex) * mesh.vertices.size());
            stagingVertexBuffer.unmap();

            Buffer stagingIndexBuffer = {
                MEMORY_TYPE_HOST,
                BUFFER_USAGE_TRANSFER_SRC,
                sizeof(u32) * mesh.indices.size(),
            };
            void* stageIndexMemory = stagingIndexBuffer.map();
            memcpy(stageIndexMemory, mesh.indices.data(), sizeof(u32) * mesh.indices.size());
            stagingIndexBuffer.unmap();

            MeshRegion meshRegion = renderer.meshBuffer->allocate(mesh);

            auto& commandBuffer = getCommandBuffer(std::this_thread::get_id());
            commandBuffer.begin();
            commandBuffer.copyBuffer({
                .srcBuffer = stagingVertexBuffer,
                .srcOffset = 0,
                .dstBuffer = renderer.meshBuffer->vertexBuffer,
                .dstOffset = meshRegion.indirectData.vertexOffset,
                .size = meshRegion.vertices
            });
            commandBuffer.copyBuffer({
                .srcBuffer = stagingIndexBuffer,
                .srcOffset = 0,
                .dstBuffer = renderer.meshBuffer->indexBuffer,
                .dstOffset = meshRegion.indirectData.indexOffset,
                .size = meshRegion.indirectData.indices
            });
            commandBuffer.end();

            onDone({
                .buffer = &commandBuffer,
                .onDone = [&] {
                    ready = true;
                }
            });
        }
    });
}

stc::CommandBuffer& stc::RenderUploader::getCommandBuffer(std::thread::id threadId) {
    auto commandBuffer = commandBuffers.find(threadId);
    if (commandBuffer == commandBuffers.end()) {
        auto& queue = renderer.context->device->getQueue(
            renderer.context->device->queue_family_indices.transfer
        );
        auto newCommandBuffer = commandBuffers.insert({ threadId, Scope<CommandBuffer>() }).first->second;
        newCommandBuffer.New(*queue, false);
        return *newCommandBuffer;
    }
    return *commandBuffer->second;
}