//
// Created by cheerwizard on 18.10.25.
//

#ifndef PIPELINE_HPP
#define PIPELINE_HPP

#include "Vk.h"

struct VkPipe {
    VkDevice device = nullptr;
    VkPipeline pipeline = nullptr;
    VkPipelineLayout pipelineLayout = nullptr;

    VkPipe(VkDevice device, const VkPipelineInfo& info);
    ~VkPipe();

private:
    static constexpr auto TAG = "VkPipe";
};

#endif //PIPELINE_HPP