package com.cws.kanvas.rendering.backend

expect enum class BlendOp {
    ADD,
    SUBTRACT,
    REVERSE_SUBTRACT,
    MIN,
    MAX,
}

expect enum class BlendFactor {
    ZERO,
    ONE,
    SRC,
    ONE_MINUS_SRC,
    SRC_ALPHA,
    ONE_MINUS_SRC_ALPHA,
    DST,
    ONE_MINUS_DST,
    DST_ALPHA,
    ONE_MINUS_DST_ALPHA,
    SRC_ALPHA_SATURATED,
    CONSTANT,
    ONE_MINUS_CONSTANT,
}

data class Blend(
    val enable: Boolean = false,
    val srcFactorColor: BlendFactor = BlendFactor.ONE,
    val dstFactorColor: BlendFactor = BlendFactor.ZERO,
    val blendOpColor: BlendOp = BlendOp.ADD,
    val srcFactorAlpha: BlendFactor = BlendFactor.ONE,
    val dstFactorAlpha: BlendFactor = BlendFactor.ZERO,
    val blendOpAlpha: BlendOp = BlendOp.ADD,
)
