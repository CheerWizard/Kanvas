package com.cws.kanvas.editor.docking.logic

import kotlinx.serialization.Serializable

@Serializable
sealed interface DockSpaceModel {

    fun toState(): DockSpaceState

    @Serializable
    data class Host(
        val topFraction: Float,
        val bottomFraction: Float,
        val leftFraction: Float,
        val rightFraction: Float,
        val top: DockContainerModel,
        val bottom: DockContainerModel,
        val left: DockContainerModel,
        val right: DockContainerModel,
        val center: DockContainerModel,
        val hoverSlot: DockSpaceSlot?,
    ) : DockSpaceModel {

        override fun toState() = DockSpaceState.Host().apply {
            topFraction = this@Host.topFraction
            bottomFraction = this@Host.bottomFraction
            leftFraction = this@Host.leftFraction
            rightFraction = this@Host.rightFraction
            top = this@Host.top.toState()
            bottom = this@Host.bottom.toState()
            left = this@Host.left.toState()
            right = this@Host.right.toState()
            center = this@Host.center.toState()
            hoverSlot = this@Host.hoverSlot
        }

    }

}
