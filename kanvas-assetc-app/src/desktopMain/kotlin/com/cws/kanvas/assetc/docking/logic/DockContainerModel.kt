package com.cws.kanvas.assetc.docking.logic

import kotlinx.serialization.Serializable

@Serializable
data class DockContainerModel(
    val currentWindow: String? = null,
    val windows: List<String> = emptyList(),
) {

    fun toState() = DockContainerState(
        currentWindow = currentWindow,
        windows = windows,
    )

}
