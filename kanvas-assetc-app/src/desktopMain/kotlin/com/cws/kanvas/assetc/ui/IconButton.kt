package com.cws.kanvas.assetc.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

data class IconButtonStyle(
    val backgroundColor: Color,
    val backgroundHoverColor: Color,
    val backgroundPressedColor: Color,
    val iconColor: Color,
    val iconHoverColor: Color,
    val iconPressedColor: Color,
    val borderColor: Color,
)

@Composable
internal fun IconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    enabled: Boolean,
    style: IconButtonStyle,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    val pressed by interactionSource.collectIsPressedAsState()

    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.38f,
    )

    val color = when {
        hovered -> style.backgroundHoverColor
        pressed -> style.backgroundPressedColor
        else -> style.backgroundColor
    }

    val iconColor = when {
        hovered -> style.iconHoverColor
        pressed -> style.iconPressedColor
        else -> style.iconColor
    }

    Box(
        modifier = modifier
            .alpha(alpha)
            .background(color)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            colorFilter = ColorFilter.tint(iconColor)
        )
    }
}
