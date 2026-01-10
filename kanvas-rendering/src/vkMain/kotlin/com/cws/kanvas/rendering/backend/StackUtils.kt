package com.cws.kanvas.rendering.backend

import org.lwjgl.system.MemoryStack
import kotlin.use

inline fun <reified T> stack(block: (MemoryStack) -> T): T {
    return MemoryStack.stackPush().use { stack -> block(stack) }
}
