//
// Created by cheerwizard on 05.11.25.
//

#ifndef STC_DESCRIPTORPOOLS_HPP
#define STC_DESCRIPTORPOOLS_HPP

#include "backend/Device.hpp"

namespace stc {

    struct DescriptorPools {

        static void initPool(const Device& device);
        static void releasePool();

        static VkDescriptorSet initSet();
        static void releaseSet(VkDescriptorSet set);

    private:
        inline static std::unordered_map<VkDescriptorType, DescriptorPoolHandle> pools;
    };

}

#endif //STC_DESCRIPTORPOOLS_HPP