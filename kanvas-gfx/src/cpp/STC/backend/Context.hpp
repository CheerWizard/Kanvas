//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_CONTEXT_HPP
#define STC_CONTEXT_HPP

#include "Device.hpp"
#include "Surface.hpp"
#include "../bridges/RenderConfig.hpp"

namespace stc {

#ifdef VK

    struct ContextBackend : InstanceHandle {
        DebugUtilsMessengerHandle debug_utils;
        std::vector<VkExtensionProperties> extensions;
        std::vector<VkLayerProperties> layers;
        VkPhysicalDevice physical_device = null;
    };

#elif METAL



#elif WEBGPU

    struct ContextBackend {};

#endif

    struct ContextCreateInfo {
        RenderConfig render_config;
    };

    struct Context : ContextBackend {
        Scope<Device> device;
        Scope<Surface> surface;

        Context(const ContextCreateInfo& create_info);
        ~Context();

    private:
        void initInstance(const ContextCreateInfo& create_info);
        void releaseInstance();
        void findDevice();
        void* findSurface(const RenderConfig& render_config);
        bool checkExtension(const char* extension);
        bool checkExtensions(const char** extension, u32 count);
        bool checkLayer(const char* extension);
        bool checkLayers(const char** extension, u32 count);

        static constexpr auto TAG = "Context";
    };

}

#endif //STC_CONTEXT_HPP