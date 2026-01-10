//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_CONTEXT_HPP
#define STC_CONTEXT_HPP

#include "Device.hpp"
#include "Surface.hpp"
#include "generated/render_api.pb.h"

namespace stc {

#ifdef VK

    struct ContextBackend : InstanceHandle {
        DebugUtilsMessengerHandle debug_utils;
        std::vector<VkExtensionProperties> extensions;
        std::vector<VkLayerProperties> layers;
        VkPhysicalDevice physical_device = null;
    };

#elif METAL

    struct ContextBackend {};

#elif WEBGPU

    struct ContextBackend {};

#endif

    struct ContextCreateInfo {
        void* native_window = nullptr;
        RenderApiCreateInfo render_api_create_info;
    };

    struct Context : ContextBackend {
        Scope<Device> device;
        Scope<Surface> surface;

        Context(const ContextCreateInfo& create_info);
        ~Context();

    private:
        bool checkExtensions(const char** extension, u32 count);
        bool checkLayers(const char** extension, u32 count);

        // platform implementation
        void initInstance(const ContextCreateInfo& create_info);
        void releaseInstance();
        void findDevice();
        void* findSurface(void* native_window, const RenderApiCreateInfo& render_api_create_info);
        bool checkExtension(const char* extension);
        bool checkLayer(const char* extension);

        static constexpr auto TAG = "Context";
    };

}

#endif //STC_CONTEXT_HPP