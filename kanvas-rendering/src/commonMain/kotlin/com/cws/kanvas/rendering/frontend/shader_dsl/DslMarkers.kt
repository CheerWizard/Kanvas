package com.cws.kanvas.rendering.frontend.shader_dsl

import kotlin.annotation.AnnotationRetention.BINARY

@DslMarker
@Target(AnnotationTarget.CLASS)
@Retention(BINARY)
@MustBeDocumented
annotation class ScopeDsl