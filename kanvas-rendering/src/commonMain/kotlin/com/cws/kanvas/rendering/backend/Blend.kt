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
    val enabled: Boolean = false,
    val srcFactor: BlendFactor = BlendFactor.ONE,
    val dstFactor: BlendFactor = BlendFactor.ZERO,
    val operation: BlendOp = BlendOp.ADD,
)
