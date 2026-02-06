//
// Created by cheerwizard on 18.10.25.
//

#ifndef PIPELINE_HPP
#define PIPELINE_HPP

#include "../api/Vk.h"

struct VkPipe {
    VkDevice device = nullptr;
    VkPipeline pipeline = nullptr;
    VkPipelineLayout pipelineLayout = nullptr;
    VkPipeInfo info;

    VkPipe(VkDevice device, const VkPipeInfo& info);
    ~VkPipe();

    void update(const VkPipeInfo& newInfo);

private:
    static constexpr auto TAG = "VkPipe";
};

struct VkComputePipe {
    VkDevice device = nullptr;
    VkPipeline pipeline = nullptr;
    VkPipelineLayout pipelineLayout = nullptr;
    VkComputePipeInfo info;

    VkComputePipe(VkDevice device, const VkComputePipeInfo& info);
    ~VkComputePipe();

    void update(const VkComputePipeInfo& newInfo);

private:
    static constexpr auto TAG = "VkComputePipe";
};

#endif //PIPELINE_HPP