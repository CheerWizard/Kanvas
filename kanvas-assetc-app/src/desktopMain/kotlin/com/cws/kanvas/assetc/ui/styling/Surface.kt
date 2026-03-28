package com.cws.kanvas.assetc.ui.styling

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.ui.LocalTheme
import kanvas.kanvas_assetc_app.generated.resources.Res
import kanvas.kanvas_assetc_app.generated.resources.texture_lava
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource

@Composable
fun Modifier.surface(
    elevation: Dp,
    baseGradient: Gradient = PanelBackground,
    texture: DrawableResource? = Res.drawable.texture_lava,
): Modifier {
    val textureBitmap = if (texture != null) imageResource(texture) else null

    return this
        .shadow(
            elevation = elevation,
            ambientColor = LocalTheme.current.shadowAmbient,
            spotColor = LocalTheme.current.shadowSpot,
        )
        .background(baseGradient.toBrush())
        .drawWithContent {
            drawContent()

            // ambient light
            drawRect(Gradient.Linear(
                colors = listOf(
                    Color.White.copy(alpha = 0.05f),
                    Color.Transparent,
                    Color.Black.copy(alpha = 0.15f)
                ),
                start = Offset.Zero,
                end = Offset(size.width, size.height)
            ).toBrush())

            // texture
            if (textureBitmap != null) {
                drawImage(textureBitmap, alpha = 0.012f)
            }

            // top corner light
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.05f),
                        Color.Transparent
                    ),
                    center = Offset.Zero,
                    radius = 40f
                )
            )

            // top edge highlight
            drawLine(
                color = Color.White.copy(alpha = 0.10f),
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = Color.White.copy(alpha = 0.10f),
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 1.dp.toPx()
            )

            // bottom shadow edge
            drawLine(
                color = Color.Black.copy(alpha = 0.35f),
                start = Offset(size.width, 0f),
                end = Offset(size.width, size.height),
                strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = Color.Black.copy(alpha = 0.35f),
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 2.dp.toPx()
            )
        }
}

@Composable
fun Modifier.surfaceButton(
    background: Gradient,
    shape: Shape = RectangleShape,
    stroke: Dp = 0.dp,
    radius: Dp = 0.dp,
    shadowColor: Color = ShadowBorder,
    baseColor: Color = BaseBorder,
    innerBottomHighlight: Color = InnerBottomHighLight,
    innerTopHighlight: Color = InnerTopHighlight,
): Modifier {
    return this
        .clip(shape)
        .background(background.toBrush())
        .drawBehind {
            val strokePx = stroke.toPx()
            val radiusPx = radius.toPx()

            // shadow border
            drawRoundRect(
                color = shadowColor,
                size = size,
                cornerRadius = CornerRadius(radiusPx),
                style = Stroke(strokePx)
            )

            // base border
            drawRoundRect(
                color = baseColor,
                topLeft = Offset(strokePx, strokePx),
                size = Size(
                    size.width - strokePx * 2,
                    size.height - strokePx * 2
                ),
                cornerRadius = CornerRadius(radiusPx - strokePx),
                style = Stroke(strokePx)
            )

            // inner top highlight
            drawLine(
                color = innerTopHighlight,
                start = Offset(strokePx * 2, strokePx * 2),
                end = Offset(size.width - strokePx * 2, strokePx * 2),
                strokeWidth = strokePx
            )

            // inner bottom highlight
            drawLine(
                color = innerBottomHighlight,
                start = Offset(strokePx * 2, size.height - strokePx * 2),
                end = Offset(size.width - strokePx * 2, size.height - strokePx * 2),
                strokeWidth = strokePx
            )
        }
}
