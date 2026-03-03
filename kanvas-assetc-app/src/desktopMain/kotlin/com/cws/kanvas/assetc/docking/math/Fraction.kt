package com.cws.kanvas.assetc.docking.math

internal fun Float.fraction(fraction: Float) = (this + fraction).coerceIn(0.01f, 0.99f)
