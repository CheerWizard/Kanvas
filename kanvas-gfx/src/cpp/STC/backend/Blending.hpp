//
// Created by cheerwizard on 04.11.25.
//

#ifndef STC_BLENDING_HPP
#define STC_BLENDING_HPP

#include "Handle.hpp"

namespace stc {

#ifdef VK

    enum BlendOp {
        BLEND_OP_ADD = VK_BLEND_OP_ADD,
        BLEND_OP_SUBTRACT = VK_BLEND_OP_SUBTRACT,
        BLEND_OP_REVERSE_SUBTRACT = VK_BLEND_OP_REVERSE_SUBTRACT,
        BLEND_OP_MIN = VK_BLEND_OP_MIN,
        BLEND_OP_MAX = VK_BLEND_OP_MAX,
    };

    enum BlendFactor {
        BLEND_FACTOR_ZERO = VK_BLEND_FACTOR_ZERO,
        BLEND_FACTOR_ONE = VK_BLEND_FACTOR_ONE,
        BLEND_FACTOR_SRC = VK_BLEND_FACTOR_SRC_COLOR,
        BLEND_FACTOR_ONE_MINUS_SRC = VK_BLEND_FACTOR_ONE_MINUS_SRC_COLOR,
        BLEND_FACTOR_SRC_ALPHA = VK_BLEND_FACTOR_SRC_ALPHA,
        BLEND_FACTOR_ONE_MINUS_SRC_ALPHA = VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA,
        BLEND_FACTOR_DST = VK_BLEND_FACTOR_DST_COLOR,
        BLEND_FACTOR_ONE_MINUS_DST = VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR,
        BLEND_FACTOR_DST_ALPHA = VK_BLEND_FACTOR_DST_ALPHA,
        BLEND_FACTOR_ONE_MINUS_DST_ALPHA = VK_BLEND_FACTOR_ONE_MINUS_DST_ALPHA,
        BLEND_FACTOR_SRC_ALPHA_SATURATED = VK_BLEND_FACTOR_SRC_ALPHA_SATURATE,
        BLEND_FACTOR_CONSTANT = VK_BLEND_FACTOR_CONSTANT_COLOR,
        BLEND_FACTOR_ONE_MINUS_CONSTANT = VK_BLEND_FACTOR_ONE_MINUS_CONSTANT_COLOR,
    };

#elif WEBGPU

    enum BlendOp {
        BLEND_OP_ADD = WGPUBlendOperation_Add,
        BLEND_OP_SUBTRACT = WGPUBlendOperation_Subtract,
        BLEND_OP_REVERSE_SUBTRACT = WGPUBlendOperation_ReverseSubtract,
        BLEND_OP_MIN = WGPUBlendOperation_Min,
        BLEND_OP_MAX = WGPUBlendOperation_Max,
    };

    enum BlendFactor {
        BLEND_FACTOR_ZERO = WGPUBlendFactor_Zero,
        BLEND_FACTOR_ONE = WGPUBlendFactor_One,
        BLEND_FACTOR_SRC = WGPUBlendFactor_Src,
        BLEND_FACTOR_ONE_MINUS_SRC = WGPUBlendFactor_OneMinusSrc,
        BLEND_FACTOR_SRC_ALPHA = WGPUBlendFactor_SrcAlpha,
        BLEND_FACTOR_ONE_MINUS_SRC_ALPHA = WGPUBlendFactor_OneMinusSrcAlpha,
        BLEND_FACTOR_DST = WGPUBlendFactor_Dst,
        BLEND_FACTOR_ONE_MINUS_DST = WGPUBlendFactor_OneMinusDst,
        BLEND_FACTOR_DST_ALPHA = WGPUBlendFactor_DstAlpha,
        BLEND_FACTOR_ONE_MINUS_DST_ALPHA = WGPUBlendFactor_OneMinusDstAlpha,
        BLEND_FACTOR_SRC_ALPHA_SATURATED = WGPUBlendFactor_SrcAlphaSaturated,
        BLEND_FACTOR_CONSTANT = WGPUBlendFactor_Constant,
        BLEND_FACTOR_ONE_MINUS_CONSTANT = WGPUBlendFactor_OneMinusConstant,
    };

#elif METAL

    enum BlendOp {
        BLEND_OP_ADD = WGPUBlendOperation_Add,
        BLEND_OP_SUBTRACT = WGPUBlendOperation_Subtract,
        BLEND_OP_REVERSE_SUBTRACT = WGPUBlendOperation_ReverseSubtract,
        BLEND_OP_MIN = WGPUBlendOperation_Min,
        BLEND_OP_MAX = WGPUBlendOperation_Max,
    };

    enum BlendFactor {
        BLEND_FACTOR_ZERO = WGPUBlendFactor_Zero,
        BLEND_FACTOR_ONE = WGPUBlendFactor_One,
        BLEND_FACTOR_SRC = WGPUBlendFactor_Src,
        BLEND_FACTOR_ONE_MINUS_SRC = WGPUBlendFactor_OneMinusSrc,
        BLEND_FACTOR_SRC_ALPHA = WGPUBlendFactor_SrcAlpha,
        BLEND_FACTOR_ONE_MINUS_SRC_ALPHA = WGPUBlendFactor_OneMinusSrcAlpha,
        BLEND_FACTOR_DST = WGPUBlendFactor_Dst,
        BLEND_FACTOR_ONE_MINUS_DST = WGPUBlendFactor_OneMinusDst,
        BLEND_FACTOR_DST_ALPHA = WGPUBlendFactor_DstAlpha,
        BLEND_FACTOR_ONE_MINUS_DST_ALPHA = WGPUBlendFactor_OneMinusDstAlpha,
        BLEND_FACTOR_SRC_ALPHA_SATURATED = WGPUBlendFactor_SrcAlphaSaturated,
        BLEND_FACTOR_CONSTANT = WGPUBlendFactor_Constant,
        BLEND_FACTOR_ONE_MINUS_CONSTANT = WGPUBlendFactor_OneMinusConstant,
    };

#endif

    struct Blending {
        bool enabled = false;
        BlendFactor srcFactor;
        BlendFactor dstFactor;
        BlendOp operation;
    };

}

#endif //STC_BLENDING_HPP