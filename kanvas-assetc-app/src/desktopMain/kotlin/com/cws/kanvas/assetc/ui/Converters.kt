package com.cws.kanvas.assetc.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition

fun DpSize.toSize() = Size(width.value, height.value)

fun Size.toDpSize() = DpSize(width.dp, height.dp)

context(density: Density)
fun IntSize.toDpSize() = with(density) { DpSize(width.dp, height.dp) }

context(density: Density)
fun Size.toDpSize() = with(density) { DpSize(width.dp, height.dp) }

fun DpOffset.toOffset() = Offset(x.value, y.value)

fun Offset.toDpOffset() = DpOffset(x.dp, y.dp)

context(density: Density)
fun Offset.toDpOffset() = with(density) { DpOffset(x.dp, y.dp) }

fun WindowPosition.toOffset() = Offset(x.value, y.value)

fun Offset.toWindowPosition() = WindowPosition(x.dp, y.dp)

fun Color.toAwtColor() = java.awt.Color(red, green, blue, alpha)

fun Color.toHex() = String.format("#%08X", toArgb())

operator fun DpOffset.plus(other: DpSize) = DpOffset(x + other.width, y + other.height)
