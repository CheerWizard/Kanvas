//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_HANDLE_METAL_HPP
#define STC_HANDLE_METAL_HPP

#include <Foundation/Foundation.hpp>
#include <QuartzCore/QuartzCore.hpp>
#include <Metal/Metal.hpp>

#define CALL(function) ASSERT(function != null, "Metal", #function)
#define null nullptr

#define ASSERT_HANDLE(HANDLE) ASSERT(handle, "Metal", "Failed to create " #HANDLE)

namespace stc {

    struct DeviceHandle {
        MTL::Device* handle = null;

        void New();
        void Delete();

        [[nodiscard]] MTL::Device* get() const {
            return handle;
        }

        operator MTL::Device*() const {
            return handle;
        }

    };

    struct BufferHandle {
        MTL::Buffer* handle = null;

        void New();
        void Delete();

        [[nodiscard]] MTL::Buffer* get() const {
            return handle;
        }

        operator MTL::Buffer*() const {
            return handle;
        }

    };

    struct FenceHandle {
        MTL::Fence* handle = null;

        void New();
        void Delete();

        [[nodiscard]] MTL::Buffer* get() const {
            return handle;
        }

        operator MTL::Buffer*() const {
            return handle;
        }

    };

    struct TextureHandle {
        MTL::Texture* handle = null;

        void New(const MTL::TextureDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::Texture* get() const {
            return handle;
        }

        operator MTL::Texture*() const {
            return handle;
        }

    };

    struct SamplerHandle {
        MTL::SamplerState* handle = null;

        void New(const MTL::SamplerDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::SamplerState* get() const {
            return handle;
        }

        operator MTL::SamplerState*() const {
            return handle;
        }

    };

    struct CommandBufferHandle {
        MTL::CommandBuffer* handle = null;

        void New(const MTL::CommandBufferDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::CommandBuffer* get() const {
            return handle;
        }

        operator MTL::CommandBuffer*() const {
            return handle;
        }

    };

    struct ShaderModuleHandle {
        MTL::Library* handle = null;

        void New(const MTL::CompileOptions& options);
        void Delete();

        [[nodiscard]] MTL::Library* get() const {
            return handle;
        }

        operator MTL::Library*() const {
            return handle;
        }

    };

    struct RenderPipelineHandle {
        MTL::RenderPipelineState* handle = null;

        void New(const MTL::RenderPipelineDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::RenderPipelineState* get() const {
            return handle;
        }

        operator MTL::RenderPipelineState*() const {
            return handle;
        }

    };

    struct ComputePipelineHandle {
        MTL::ComputePipelineState* handle = null;

        void New(MTL::ComputePipelineDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::ComputePipelineState* get() const {
            return handle;
        }

        operator MTL::ComputePipelineState*() const {
            return handle;
        }

    };

    struct SurfaceHandle {
        CA::MetalLayer* handle = null;

        void New();
        void Delete();

        [[nodiscard]] CA::MetalLayer* get() const {
            return handle;
        }

        operator CA::MetalLayer*() const {
            return handle;
        }

    };

    struct QueueHandle {
        MTL::CommandQueue* handle = null;

        void New(MTL::Device* device);
        void Delete();

        [[nodiscard]] MTL::CommandQueue* get() const {
            return handle;
        }

        operator MTL::CommandQueue*() const {
            return handle;
        }

    };

    struct RenderPassHandle {
        MTL::RenderCommandEncoder* handle = null;

        void New(const MTL::RenderPassDescriptor& descriptor);
        void Delete();

        [[nodiscard]] MTL::RenderCommandEncoder* get() const {
            return handle;
        }

        operator MTL::RenderCommandEncoder*() const {
            return handle;
        }

    };

}

#endif //STC_HANDLE_METAL_HPP