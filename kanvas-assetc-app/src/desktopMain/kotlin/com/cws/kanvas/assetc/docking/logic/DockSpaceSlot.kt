package com.cws.kanvas.assetc.docking.logic

import kotlinx.serialization.Serializable

@Serializable
enum class DockSpaceSlot {
    Top,
    Bottom,
    Left,
    Right,
    Center,
    TopColumn,
    BottomColumn,
    LeftRow,
    RightRow;
}
