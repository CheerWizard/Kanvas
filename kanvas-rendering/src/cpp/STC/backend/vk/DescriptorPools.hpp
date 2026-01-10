//
// Created by cheerwizard on 05.11.25.
//

#ifndef STC_DESCRIPTORPOOLS_HPP
#define STC_DESCRIPTORPOOLS_HPP

#include "backend/Device.hpp"

namespace stc {

    struct DescriptorPools {

        static void New(const Device& device);
        static void Delete();

        static DescriptorPoolHandle newPool(VkDescriptorType type, u32 size);
        static void deletePool(VkDescriptorType type);

        static VkDescriptorSet newSet(VkDescriptorType type, VkDescriptorSetLayout layout);
        static void deleteSet(VkDescriptorType type);

    private:
        inline static const Device* device = nullptr;
        inline static std::unordered_map<VkDescriptorType, DescriptorPoolHandle> pools;
        inline static std::unordered_map<VkDescriptorType, VkDescriptorSet> sets;
    };

}

#endif //STC_DESCRIPTORPOOLS_HPP