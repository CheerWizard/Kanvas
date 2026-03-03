package com.cws.kanvas.assetc.ui

import androidx.compose.ui.Modifier

inline fun Modifier.applyIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        this.then(modifier(Modifier))
    } else {
        this
    }
}
