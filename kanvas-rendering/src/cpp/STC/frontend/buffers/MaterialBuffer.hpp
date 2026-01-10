//
// Created by cheerwizard on 18.10.25.
//

#ifndef STC_MATERIALBUFFER_HPP
#define STC_MATERIALBUFFER_HPP

#include "StorageBuffer.hpp"

namespace stc {

    struct MaterialData {
        // albedo mapping
        float4 albedo;
        bool enableAlbedoMap;
        // normal mapping
        bool enableNormalMap;
        // metal mapping
        float metalness;
        bool enableMetalMap;
        // roughness mapping
        float roughness;
        bool enableRoughnessMap;
        // ambient occlusion mapping
        float ao;
        bool enableAOMap;
        // emission mapping
        float3 emission;
        bool enableEmissionMap;
    };

    static constexpr Binding MATERIAL_BINDING = {
        .type = BINDING_TYPE_STORAGE_BUFFER,
        .slot = 2,
        .shader_stages = SHADER_STAGE_FRAGMENT,
    };

    struct MaterialBuffer : StorageBuffer {
        MaterialBuffer(const Device& device) : StorageBuffer(device, StorageBufferCreateInfo {
            .binding = MATERIAL_BINDING,
            .itemSize = sizeof(MaterialData),
            .itemCount = 100,
        }) {}
    };

}

#endif //STC_MATERIALBUFFER_HPP