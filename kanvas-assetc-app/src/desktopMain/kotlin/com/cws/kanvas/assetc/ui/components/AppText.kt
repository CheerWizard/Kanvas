package com.cws.kanvas.assetc.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.cws.kanvas.assetc.ui.styling.ColorLightness
import com.cws.kanvas.assetc.ui.styling.lightness

data class AppTextStyle(
    val style: TextStyle,
    val primaryColor: Color,
    val secondaryColor: Color,
    val lightness: ColorLightness,
)

@Stable
data class TextSpan(
    val text: String,
    val style: AppTextStyle? = null,
    val onClick: (() -> Unit)? = null,
)

fun AppTextStyle.toTextSpanStyle(color: Color): SpanStyle = SpanStyle(
    color = color,
    fontFamily = style.fontFamily,
    fontStyle = style.fontStyle,
    fontSize = style.fontSize,
    fontWeight = style.fontWeight,
    textDecoration = style.textDecoration,
    letterSpacing = style.letterSpacing,
)

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    text: String,
    style: AppTextStyle,
    textAlign: TextAlign = TextAlign.Unspecified,
    enabled: Boolean = true,
    hovered: Boolean = false,
    pressed: Boolean = false,
    selectable: Boolean = false,
    spans: List<TextSpan> = emptyList(),
    autoSize: TextAutoSize? = null,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
) {
    val color = when {
        !enabled -> style.primaryColor.lightness(style.lightness.disabled)
        hovered -> style.primaryColor.lightness(style.lightness.hovered)
        pressed -> style.primaryColor.lightness(style.lightness.pressed)
        else -> style.primaryColor
    }

    val content: @Composable () -> Unit = {
        if (spans.isEmpty()) {
            BasicText(
                modifier = modifier,
                text = text,
                style = style.style.copy(
                    color = color,
                    textAlign = textAlign,
                ),
                minLines = minLines,
                maxLines = maxLines,
                autoSize = autoSize,
            )
        } else {
            val annotatedText = remember(text, spans, style) {
                buildAnnotatedString {
                    var beginIndex = 0
                    for (span in spans) {
                        val spanIndex = text.indexOf(span.text, beginIndex)
                        val spanStyle = span.style?.toTextSpanStyle(color) ?: style.toTextSpanStyle(color)

                        if (spanIndex == -1) continue

                        append(text.substring(beginIndex, spanIndex))

                        if (span.onClick == null) {
                            withStyle(spanStyle) { append(span.text) }
                        } else {
                            withLink(
                                link = LinkAnnotation.Clickable(
                                    tag = span.text,
                                    styles = TextLinkStyles(spanStyle),
                                    linkInteractionListener = {
                                        span.onClick()
                                    },
                                ),
                            ) {
                                withStyle(spanStyle) {
                                    append(span.text)
                                }
                            }
                        }

                        beginIndex = spanIndex + span.text.length
                    }
                    append(text.substring(beginIndex, text.length))
                }
            }

            BasicText(
                modifier = modifier,
                text = annotatedText,
                style = style.style.copy(
                    color = color,
                    textAlign = textAlign,
                ),
                minLines = minLines,
                maxLines = maxLines,
                autoSize = autoSize,
            )
        }
    }

    if (selectable) {
        SelectionContainer {
            content()
        }
    } else {
        content()
    }
}
