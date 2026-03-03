package com.cws.kanvas.assetc.docking.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cws.kanvas.assetc.docking.logic.DockSpaceSlot
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.docking.math.fraction

@Composable
internal fun DockHost(
    modifier: Modifier = Modifier,
    state: DockWindowState,
    dockSpace: DockSpaceState.Host,
) {
    val topFraction = dockSpace.topFraction
    val bottomFraction = dockSpace.bottomFraction
    val leftFraction = dockSpace.leftFraction
    val rightFraction = dockSpace.rightFraction

    Column(
        modifier = modifier,
    ) {
        // top
        DockContainer(
            modifier = Modifier
                .weight(topFraction),
            hovered = dockSpace.hoverSlot == DockSpaceSlot.Top,
            container = dockSpace.top,
        )
        DockDivider(
            onFractionChange = {
                dockSpace.topFraction = topFraction.fraction(it)
            },
        )

        // center container
        Row(
            modifier = Modifier.weight(1f - topFraction - bottomFraction)
        ) {
            // left
            DockContainer(
                modifier = Modifier
                    .weight(leftFraction),
                hovered = dockSpace.hoverSlot == DockSpaceSlot.Left,
                container = dockSpace.left,
            )
            DockDivider(
                onFractionChange = {
                    dockSpace.leftFraction = leftFraction.fraction(it)
                },
            )

            // center
            DockContainer(
                modifier = Modifier
                    .weight(1f - leftFraction - rightFraction),
                hovered = dockSpace.hoverSlot == DockSpaceSlot.Center,
                container = dockSpace.center,
            )

            // right
            DockDivider(
                onFractionChange = {
                    dockSpace.rightFraction = rightFraction.fraction(-it)
                },
            )
            DockContainer(
                modifier = Modifier
                    .weight(rightFraction),
                hovered = dockSpace.hoverSlot == DockSpaceSlot.Right,
                container = dockSpace.right,
            )
        }

        // bottom
        DockDivider(
            onFractionChange = {
                dockSpace.bottomFraction = bottomFraction.fraction(-it)
            },
        )
        DockContainer(
            modifier = Modifier
                .weight(bottomFraction),
            hovered = dockSpace.hoverSlot == DockSpaceSlot.Bottom,
            container = dockSpace.bottom,
        )
    }
}
