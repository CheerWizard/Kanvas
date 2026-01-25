@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package com.cws.kanvas.wgpu.gpu

typealias UInt53 = Double

sealed external interface AllowSharedBufferSource

fun <T> unsafeCast(value: String): T = value.unsafeCast<T>()

inline fun <T> jsObject(builder: T.() -> Unit): T = js("{}").unsafeCast<T>().apply(builder)