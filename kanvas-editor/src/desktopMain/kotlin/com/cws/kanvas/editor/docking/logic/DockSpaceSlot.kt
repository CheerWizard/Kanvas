package com.cws.kanvas.editor.docking.logic

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
