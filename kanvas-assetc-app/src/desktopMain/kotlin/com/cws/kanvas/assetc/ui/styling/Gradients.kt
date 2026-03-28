package com.cws.kanvas.assetc.ui.styling

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

sealed interface Gradient {

    val colors: List<Color>
    fun toBrush(): Brush
    fun lightness(lightness: Float): Gradient

    data class Linear(
        override val colors: List<Color>,
        val start: Offset = Offset.Zero,
        val end: Offset = Offset.Infinite,
        val tileMode: TileMode = TileMode.Clamp
    ) : Gradient {

        override fun toBrush(): Brush = Brush.linearGradient(
            colors = colors,
            start = start,
            end = end,
            tileMode = tileMode,
        )

        override fun lightness(lightness: Float): Gradient = copy(colors = colors.map { it.lightness(lightness) })

    }

    data class Horizontal(
        override val colors: List<Color>,
        val start: Float = 0f,
        val end: Float = Float.POSITIVE_INFINITY,
        val tileMode: TileMode = TileMode.Clamp
    ) : Gradient {

        override fun toBrush(): Brush = Brush.horizontalGradient(
            colors = colors,
            startX = start,
            endX = end,
            tileMode = tileMode,
        )

        override fun lightness(lightness: Float): Gradient = copy(colors = colors.map { it.lightness(lightness) })

    }

    data class Vertical(
        override val colors: List<Color>,
        val start: Float = 0f,
        val end: Float = Float.POSITIVE_INFINITY,
        val tileMode: TileMode = TileMode.Clamp
    ) : Gradient {

        override fun toBrush(): Brush = Brush.verticalGradient(
            colors = colors,
            startY = start,
            endY = end,
            tileMode = tileMode,
        )

        override fun lightness(lightness: Float): Gradient = copy(colors = colors.map { it.lightness(lightness) })

    }

    data class Radial(
        override val colors: List<Color>,
        val center: Offset = Offset.Unspecified,
        val radius: Float = Float.POSITIVE_INFINITY,
        val tileMode: TileMode = TileMode.Clamp
    ) : Gradient {

        override fun toBrush(): Brush = Brush.radialGradient(
            colors = colors,
            center = center,
            radius = radius,
            tileMode = tileMode,
        )

        override fun lightness(lightness: Float): Gradient = copy(colors = colors.map { it.lightness(lightness) })

    }

    data class Sweep(
        override val colors: List<Color>,
        val center: Offset = Offset.Unspecified,
    ) : Gradient {

        override fun toBrush(): Brush = Brush.sweepGradient(
            colors = colors,
            center = center,
        )

        override fun lightness(lightness: Float): Gradient = copy(colors = colors.map { it.lightness(lightness) })

    }

}

// Top bar
val TopBarBackground: Gradient = Gradient.Linear(
    listOf(
        Color(0xFF2F3238),
        Color(0xFF2A2D33),
        Color(0xFF24272C)
    )
)
val TopBarAccent: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFFFF7A1A),
        Color(0xFFF4C14B)
    )
)

// Tabs
val TabInactive: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFF2D2F35),
        Color(0xFF232429)
    )
)
val TabActive: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFFFF7A1A),
        Color(0xFFD93800)
    )
)

// Buttons
val ButtonPrimary: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFFFF8A2A),
        Color(0xFFFF5A0A)
    )
)

// Panels
val PanelBackground: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFF2F3238),
        Color(0xFF2A2D33),
        Color(0xFF24272C)
    )
)

// Scrolls
val ScrollThumb: Gradient = Gradient.Vertical(
    listOf(
        Color(0xFF6E6E6E),
        Color(0xFF3F3F3F)
    )
)

// Menus
val ItemGradient: Gradient = Gradient.Vertical(
    colors = listOf(
        Color(0xFF353842),
        Color(0xFF26282F)
    )
)
val ItemSelectedGradient: Gradient = Gradient.Vertical(
    colors = listOf(
        Color(0xFFFF7A1A),
        Color(0xFFD93800)
    )
)