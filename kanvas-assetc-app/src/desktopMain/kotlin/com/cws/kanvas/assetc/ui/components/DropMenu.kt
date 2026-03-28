package com.cws.kanvas.assetc.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.cws.kanvas.assetc.overlay.LocalOverlayController
import com.cws.kanvas.assetc.ui.LocalTheme
import com.cws.kanvas.assetc.ui.applyIf
import com.cws.kanvas.assetc.ui.bloomBorders
import com.cws.kanvas.assetc.ui.icons.IconArrow
import com.cws.kanvas.assetc.ui.rememberInteractionState
import com.cws.kanvas.assetc.ui.styling.BaseBorder
import com.cws.kanvas.assetc.ui.styling.ColorLightness
import com.cws.kanvas.assetc.ui.styling.Gradient
import com.cws.kanvas.assetc.ui.styling.HorizontalDivider
import com.cws.kanvas.assetc.ui.styling.InnerBottomHighLight
import com.cws.kanvas.assetc.ui.styling.InnerTopHighlight
import com.cws.kanvas.assetc.ui.styling.ShadowBorder
import com.cws.kanvas.assetc.ui.styling.surface
import com.cws.kanvas.assetc.ui.styling.surfaceButton

data class DropMenuStyle(
    val backgroundColor: Color,
    val itemGradient: Gradient,
    val itemSelectedGradient: Gradient,
    val lightness: ColorLightness,
)

@Composable
fun DropMenu(
    modifier: Modifier = Modifier,
    style: DropMenuStyle,
    textStyle: AppTextStyle,
    items: List<DropMenuItemState>,
) {
    val innerItemsMap = remember { mutableStateMapOf<String, List<DropMenuItemState>>() }

    Row {
        DropMenuList(
            modifier = modifier,
            style = style,
            textStyle = textStyle,
            items = items,
            innerItems = innerItemsMap,
        )
        innerItemsMap.forEach { (id, innerItems) ->
            DropMenuList(
                modifier = modifier,
                style = style,
                textStyle = textStyle,
                items = innerItems,
                innerItems = innerItemsMap,
            )
        }
    }
}

@Composable
fun DropMenuList(
    modifier: Modifier = Modifier,
    style: DropMenuStyle,
    textStyle: AppTextStyle,
    items: List<DropMenuItemState>,
    innerItems: SnapshotStateMap<String, List<DropMenuItemState>>,
) {
    LazyColumn(
        modifier = modifier
            .surface(LocalTheme.current.elevationLow)
    ) {
        items(items.size, { i -> items[i].id }) { i ->
            val defaultDividerColors = LocalTheme.current.dividerColors
            val dividerColors = remember { mutableStateOf(defaultDividerColors) }
            val itemState = items[i]

            DropMenuItem(
                modifier = Modifier.fillMaxWidth(),
                style = style,
                textStyle = textStyle,
                state = itemState,
                dividerColors = dividerColors,
                innerItems = innerItems,
            )

            if (i != items.lastIndex) {
                HorizontalDivider(colorStops = defaultDividerColors)
            }
        }
    }
}

class DropMenuItemState(
    selected: Boolean = false,
    enabled: Boolean = true,
    val id: String,
    val title: String,
    val icon: ImageVector? = null,
    val innerItems: SnapshotStateList<DropMenuItemState> = SnapshotStateList(),
    val onClick: () -> Unit = {},
) {
    var selected by mutableStateOf(selected)
    var enabled by mutableStateOf(enabled)
}

@Composable
fun DropMenuItem(
    modifier: Modifier = Modifier,
    style: DropMenuStyle,
    textStyle: AppTextStyle,
    state: DropMenuItemState,
    dividerColors: MutableState<Array<Pair<Float, Color>>>,
    innerItems: SnapshotStateMap<String, List<DropMenuItemState>>,
) {
    val density = LocalDensity.current
    val interactionState = rememberInteractionState(enabled = state.enabled)

    val enabled = interactionState.enabled
    val hovered by interactionState.hovered
    val pressed by interactionState.pressed

    var background: Gradient = if (state.selected) style.itemSelectedGradient else style.itemGradient
    val innerBottomColor: Color
    val innerTopColor: Color

    val hoverGradient = if (state.selected) {
        background.lightness(style.lightness.hoverSelected)
    } else {
        background.lightness(style.lightness.hovered)
    }

    when {
        !enabled -> {
            background = background.lightness(style.lightness.disabled)
            innerBottomColor = InnerBottomHighLight
            innerTopColor = InnerTopHighlight
            dividerColors.value = LocalTheme.current.dividerColors
        }
        hovered -> {
            background = hoverGradient
            innerBottomColor = InnerBottomHighLight
            innerTopColor = InnerTopHighlight
            dividerColors.value = LocalTheme.current.dividerActiveColors
        }
        pressed -> {
            background = background.lightness(style.lightness.pressed)
            innerBottomColor = InnerTopHighlight
            innerTopColor = InnerBottomHighLight
            dividerColors.value = LocalTheme.current.dividerActiveColors
        }
        state.selected -> {
            background = style.itemSelectedGradient
            innerBottomColor = InnerTopHighlight
            innerTopColor = InnerBottomHighLight
            dividerColors.value = LocalTheme.current.dividerActiveColors
        }
        else -> {
            innerBottomColor = InnerBottomHighLight
            innerTopColor = InnerTopHighlight
            dividerColors.value = LocalTheme.current.dividerColors
        }
    }

    val iconSize = with(density) { (textStyle.style.fontSize * 1.2f).toDp() }

    if (state.innerItems.isNotEmpty()) {
        LaunchedEffect(hovered, pressed) {
            if (hovered || pressed) {
                innerItems[state.id] = state.innerItems
            } else {
                innerItems.remove(state.id)
            }
        }
    }

    Row(
        modifier = modifier
            .surfaceButton(
                background = background,
                shadowColor = ShadowBorder,
                baseColor = BaseBorder,
                innerBottomHighlight = innerBottomColor,
                innerTopHighlight = innerTopColor,
            )
            .applyIf(hovered || pressed || state.selected) {
                bloomBorders()
            }
            .clickable(
                enabled = enabled,
                interactionSource = interactionState.interactionSource,
                indication = ripple(bounded = false, color = hoverGradient.colors[1]),
                onClick = state.onClick,
            )
            .hoverable(
                enabled = enabled,
                interactionSource = interactionState.interactionSource,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (state.icon != null) {
            AppIcon(
                modifier = Modifier.size(iconSize),
                imageVector = state.icon,
                color = textStyle.primaryColor,
                interactionState = interactionState,
            )
        }
        AppText(
            text = state.title,
            style = textStyle,
            enabled = enabled,
            hovered = hovered,
            pressed = pressed,
        )
        Spacer(Modifier.weight(1f))
        if (state.innerItems.isNotEmpty()) {
            AppIcon(
                modifier = Modifier.size(iconSize),
                imageVector = IconArrow,
                color = textStyle.primaryColor,
                interactionState = interactionState,
            )
        }
    }
}
