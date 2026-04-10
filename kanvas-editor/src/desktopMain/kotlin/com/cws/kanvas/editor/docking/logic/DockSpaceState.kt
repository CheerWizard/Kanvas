package com.cws.kanvas.editor.docking.logic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.cws.kanvas.editor.docking.math.hitRect

sealed interface DockSpaceState {

    fun toModel(): DockSpaceModel
    fun findAvailableSlot(position: Offset, size: Size, screenPos: Offset): DockSpaceSlot?

    fun hoverSlot(slot: DockSpaceSlot?)

    class Host : DockSpaceState {
        var topFraction by mutableFloatStateOf(0.1f)
        var leftFraction by mutableFloatStateOf(0.1f)
        var rightFraction by mutableFloatStateOf(0.1f)
        var bottomFraction by mutableFloatStateOf(0.1f)
        var top = DockContainerState()
        var bottom = DockContainerState()
        var left = DockContainerState()
        var right = DockContainerState()
        var center = DockContainerState()
        var hoverSlot by mutableStateOf<DockSpaceSlot?>(null)

        override fun toModel() = DockSpaceModel.Host(
            topFraction = topFraction,
            bottomFraction = bottomFraction,
            leftFraction = leftFraction,
            rightFraction = rightFraction,
            top = top.toModel(),
            bottom = bottom.toModel(),
            left = left.toModel(),
            right = right.toModel(),
            center = center.toModel(),
            hoverSlot = hoverSlot,
        )

        override fun findAvailableSlot(position: Offset, size: Size, screenPos: Offset): DockSpaceSlot? {
            val y = position.y
            val x = position.x
            val w = size.width
            val h = size.height

            val slot = when {
                hitRect(
                    topLeft = Offset(x, y),
                    size = Size(w, h * topFraction),
                    target = screenPos,
                ) -> DockSpaceSlot.Top

                hitRect(
                    topLeft = Offset(x, y + h * (1f - bottomFraction)),
                    size = Size(w, h * bottomFraction),
                    target = screenPos,
                ) -> DockSpaceSlot.Bottom

                hitRect(
                    topLeft = Offset(x, y + h * topFraction),
                    size = Size(w * leftFraction, h * (1f - topFraction - bottomFraction)),
                    target = screenPos,
                ) -> DockSpaceSlot.Left

                hitRect(
                    topLeft = Offset(x + w * leftFraction, y + h * topFraction),
                    size = Size(w * (1f - leftFraction - rightFraction), h * (1f - topFraction - bottomFraction)),
                    target = screenPos,
                ) -> DockSpaceSlot.Center

                hitRect(
                    topLeft = Offset(x + w * (1f - rightFraction), y + h * topFraction),
                    size = Size(w * rightFraction, h * (1f - topFraction - bottomFraction)),
                    target = screenPos,
                ) -> DockSpaceSlot.Right

                else -> null
            }
            return slot
        }

        override fun hoverSlot(slot: DockSpaceSlot?) {
            hoverSlot = slot
        }

    }

}
