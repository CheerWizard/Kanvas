//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_CONTEXT_HPP
#define STC_CONTEXT_HPP

#include "Device.hpp"
#include "bridges/RenderConfig.hpp"

namespace stc {

#ifdef VK

    struct ContextBackend : InstanceHandle {
        DebugUtilsMessengerHandle debug_utils;
        std::vector<VkExtensionProperties> extensions;
        std::vector<VkLayerProperties> layers;
    };

#elif METAL



#elif WEBGPU

    struct ContextBackend {
        WGPUInstance handle = null;
    };

#endif

    struct ContextCreateInfo {
        RenderConfig render_config;
    };

    struct Context : ContextBackend {
        std::vector<Ptr<Device>> devices;
        Ptr<Device> device;
        void* surface = nullptr;

        Context(const ContextCreateInfo& create_info);
        ~Context();

    private:
        void initDevices();
        void selectDevice();
        void initSurface(void* nativeWindow);
        bool checkExtension(const char* extension);
        bool checkExtensions(const char** extension, u32 count);
        bool checkLayer(const char* extension);
        bool checkLayers(const char** extension, u32 count);

        static constexpr auto TAG = "Context";
    };

}

#endif //STC_CONTEXT_HPP