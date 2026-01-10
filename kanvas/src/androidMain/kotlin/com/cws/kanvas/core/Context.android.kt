package com.cws.kanvas.core

fun Context.toAndroidContext(): android.content.Context {
    return this as? android.content.Context ?: error("Invalid Context type, must be android.content.Context!")
}