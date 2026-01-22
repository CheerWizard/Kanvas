//
// Created by cheerwizard on 05.11.25.
//

#ifndef DESCRIPTORPOOLS_HPP
#define DESCRIPTORPOOLS_HPP

#include "VkCommon.hpp"

struct DescriptorPools {

    static void New(VkDevice device);
    static void Delete();

    static VkDescriptorPool newPool(VkDescriptorType type, u32 size);
    static void deletePool(VkDescriptorType type);

    static VkDescriptorSet newSet(VkDescriptorType type, VkDescriptorSetLayout layout);
    static void deleteSet(VkDescriptorType type);

private:
    inline static VkDevice device = nullptr;
    inline static std::unordered_map<VkDescriptorType, VkDescriptorPool> pools;
    inline static std::unordered_map<VkDescriptorType, VkDescriptorSet> sets;
};

#endif //DESCRIPTORPOOLS_HPP