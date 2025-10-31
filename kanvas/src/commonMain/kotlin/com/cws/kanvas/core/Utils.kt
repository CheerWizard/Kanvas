package com.cws.kanvas.core

val Float.fps get() = 1 / (this / 1_000.0f)