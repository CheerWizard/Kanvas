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
    SRC_COLOR,
    ONE_MINUS_SRC_COLOR,
    DST_COLOR,
    ONE_MINUS_DST_COLOR,
    SRC_ALPHA,
    ONE_MINUS_SRC_ALPHA,
    DST_ALPHA,
    ONE_MINUS_DST_ALPHA;
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
