package com.cws.kanvas.shaderc

import kotlin.annotation.AnnotationRetention.BINARY

@DslMarker
@Target(AnnotationTarget.CLASS)
@Retention(BINARY)
@MustBeDocumented
annotation class ScopeDsl