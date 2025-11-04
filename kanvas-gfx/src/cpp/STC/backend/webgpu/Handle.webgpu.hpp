//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_HANDLE_WEBGPU_HPP
#define STC_HANDLE_WEBGPU_HPP

#include <webgpu/webgpu.h>
#include <emscripten/html5_webgpu.h>
#include <emscripten/emscripten.h>

#define CALL(function) ASSERT((function) == 1, "WebGPU", #function)
#define null nullptr

#define ASSERT_HANDLE(HANDLE) ASSERT(handle, "WebGPU", "Failed to create " #HANDLE)

namespace stc {

    using Nothing = std::nullptr_t;

    #define WGPU_HANDLE(HANDLE)                                                                              \
    template<typename T>                                                                   \
    struct HANDLE##Handle##Template {                                                                                   \
        T handle = null;                                                                                         \
                                                                                                                            \
        void New(WGPUDevice device, const WGPU##HANDLE##Descriptor& descriptor) {                                    \
            handle = wgpuDeviceCreate##HANDLE(device, &descriptor);              \
            ASSERT_HANDLE(HANDLE);                                                                                                    \
        }                                                                                                       \
                                                                                                                \
        void Delete() {                                                                                       \
            if (handle != null) {                                     \
                wgpu##HANDLE##Release(handle);                         \
                handle = null;                                                                    \
            }                                                                                       \
        }                                                                                                       \
                                                                                                                \
        T get() const {                                                                                         \
            return handle;                                                                                      \
        }                                                                                                       \
                                                                                                                \
        operator T() const {                                                                                    \
            return handle;                                                                                      \
        }                                                                                                              \
    };                                                                                                          \
                                                                                                                \
    using HANDLE##Handle = HANDLE##Handle##Template<WGPU##HANDLE>;              \

    WGPU_HANDLE(Buffer)
    WGPU_HANDLE(Texture)
    WGPU_HANDLE(Sampler)
    WGPU_HANDLE(ShaderModule)
    WGPU_HANDLE(RenderPipeline)
    WGPU_HANDLE(ComputePipeline)
    WGPU_HANDLE(BindGroupLayout)
    WGPU_HANDLE(BindGroup)
    WGPU_HANDLE(PipelineLayout)
    WGPU_HANDLE(CommandEncoder)

    using CommandBufferHandle = WGPUCommandBuffer;

    struct InstanceHandle {
        void New();
        void Delete();
    };

    struct DeviceHandle {
        WGPUDevice handle = null;
        void New();
        void Delete();
    };

    struct SwapchainHandle {
        void New();
        void Delete();
    };

    struct TextureViewHandle {
        WGPUTextureView handle = null;

        void New(WGPUTexture texture, const WGPUTextureViewDescriptor& descriptor);
        void Delete();

        [[nodiscard]] WGPUTextureView get() const {
            return handle;
        }

        operator WGPUTextureView() const {
            return handle;
        }

    };

    struct SurfaceHandle {
        WGPUSurface handle = null;

        void New(WGPUInstance instance, const WGPUSurfaceDescriptor& descriptor);
        void Delete();

        [[nodiscard]] WGPUSurface get() const {
            return handle;
        }

        operator WGPUSurface() const {
            return handle;
        }

    };

    struct QueueHandle {
        WGPUQueue handle = null;

        void New(WGPUDevice device);
        void Delete();

        [[nodiscard]] WGPUQueue get() const {
            return handle;
        }

        operator WGPUQueue() const {
            return handle;
        }

    };

    struct RenderPassHandle {
        WGPURenderPassEncoder handle = null;

        void New(WGPUCommandEncoder command_encoder, const WGPURenderPassDescriptor& descriptor);
        void Delete();

        [[nodiscard]] WGPURenderPassEncoder get() const {
            return handle;
        }

        operator WGPURenderPassEncoder() const {
            return handle;
        }

    };

}

#endif //STC_HANDLE_WEBGPU_HPP