package com.cws.kanvas.rendering.backend

expect val SURFACE_EXTENSION_NAME: String?

expect fun RenderContext.provideNativeWindow(): Long