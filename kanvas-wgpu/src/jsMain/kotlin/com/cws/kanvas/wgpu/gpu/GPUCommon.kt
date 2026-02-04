@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)
@file:OptIn(ExperimentalJsCollectionsApi::class)

package com.cws.kanvas.wgpu.gpu

import kotlin.js.collections.JsReadonlyArray
import kotlin.js.unsafeCast

typealias UInt53 = Double

sealed external interface AllowSharedBufferSource

fun <T> unsafeCast(value: String): T = value.unsafeCast<T>()

inline fun <T> jsObject(builder: T.() -> Unit): T = js("{}").unsafeCast<T>().apply(builder)

fun <T> jsArrayOf(vararg elements: T) = arrayOf(elements).unsafeCast<JsReadonlyArray<T>>()

fun Number.toGPUSize32(): GPUSize32 = toInt()
fun Number.toGPUSize64(): GPUSize64 = toDouble()