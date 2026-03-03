//
// Created by cheerwizard on 18.10.25.
//

#ifndef PIPELINE_HPP
#define PIPELINE_HPP

#include <unordered_map>

#include "../api/Vk.h"

struct VkPipe {
    VkDevice device = nullptr;
    VkPipeline pipeline = nullptr;
    VkPipelineLayout pipelineLayout = nullptr;
    VkPipeInfo info;

    VkPipe(VkDevice device, const VkPipeInfo& info);
    ~VkPipe();

    void update(const VkPipeInfo& newInfo);

    static void onShaderUpdated(VkShader* shader);

private:
    static constexpr auto TAG = "VkPipe";
    static std::unordered_map<VkShader*, VkPipe*> shadersWithPipes;
};

struct VkComputePipe {
    VkDevice device = nullptr;
    VkPipeline pipeline = nullptr;
    VkPipelineLayout pipelineLayout = nullptr;
    VkComputePipeInfo info;

    VkComputePipe(VkDevice device, const VkComputePipeInfo& info);
    ~VkComputePipe();

    void update(const VkComputePipeInfo& newInfo);

    static void onShaderUpdated(VkShader* shader);

private:
    static constexpr auto TAG = "VkComputePipe";
    static std::unordered_map<VkShader*, VkComputePipe*> shadersWithPipes;
};

#endif //PIPELINE_HPP