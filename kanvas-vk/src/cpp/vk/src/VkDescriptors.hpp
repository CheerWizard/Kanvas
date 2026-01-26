//
// Created by cheerwizard on 05.11.25.
//

#ifndef DESCRIPTORPOOLS_HPP
#define DESCRIPTORPOOLS_HPP

#include "VkCommon.hpp"
#include "VkBindingLayout.hpp"

struct VkDescriptors {

    static void New(VkContext* context);
    static void Delete();

    static void newPool(VkBindingLayout* layout);
    static void deletePool(VkBindingLayout* layout);

    static void newSet(VkBindingLayout* layout);
    static void deleteSet(VkBindingLayout* layout);

    static VkDescriptorPool getPool(VkBindingLayout* layout, int frame);
    static VkDescriptorSet getSet(VkBindingLayout* layout, int frame);

private:
    static VkDescriptorPool createPool(VkBindingLayout* layout, int frame);
    static VkDescriptorSet createSet(VkBindingLayout* layout, int frame);

    inline static VkContext* context = nullptr;
    inline static std::mutex mutex;
    inline static std::unordered_map<VkBindingLayout*, std::vector<VkDescriptorPool>> pools;
    inline static std::unordered_map<VkBindingLayout*, std::vector<VkDescriptorSet>> sets;
};

#endif //DESCRIPTORPOOLS_HPP