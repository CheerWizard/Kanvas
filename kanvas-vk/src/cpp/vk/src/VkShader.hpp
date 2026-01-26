//
// Created by cheerwizard on 20.10.25.
//

#ifndef STC_SHADER_HPP
#define STC_SHADER_HPP

#include "VkCommon.hpp"
#include "../api/Vk.h"

struct VkShader {
    VkDevice device = nullptr;
    VkShaderModule shader = nullptr;
    VkBindingLayout* binding_layout = nullptr;
    VkPipe* pipe = nullptr;
    VkShaderInfo info;

    VkShader(VkDevice device, const VkShaderInfo& info);
    ~VkShader();

    void update(const VkShaderInfo& newInfo);

private:
    static constexpr auto TAG = "VkShader";
};

#endif //STC_SHADER_HPP