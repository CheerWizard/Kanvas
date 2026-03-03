package com.cws.kanvas.assetc.docking.controller

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.DpOffset
import com.cws.kanvas.assetc.core.Controller
import com.cws.kanvas.assetc.core.provideController
import com.cws.kanvas.assetc.docking.logic.DockContainerState
import com.cws.kanvas.assetc.docking.logic.DockSpaceState
import com.cws.kanvas.assetc.docking.logic.DockSpaceSlot
import com.cws.kanvas.assetc.docking.logic.DockWindowRepo
import com.cws.kanvas.assetc.docking.logic.DockWindowState
import com.cws.kanvas.assetc.docking.math.hitRect
import com.cws.kanvas.assetc.docking.ui.WindowCoords
import com.cws.kanvas.assetc.ui.toDpOffset
import com.cws.kanvas.assetc.ui.toOffset
import com.cws.kanvas.assetc.ui.toSize
import com.cws.print.Print
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class DockController : Controller() {

    protected val _restored = MutableStateFlow(false)
    val restored = _restored.asStateFlow()

    protected val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    abstract operator fun get(id: String?): DockWindowState?
    abstract fun getOrAdd(state: DockWindowState): DockWindowState
    abstract fun remove(state: DockWindowState)
    abstract fun saveState()
    abstract fun dragWindowStart(state: DockWindowState)
    abstract fun dragWindowEnd(state: DockWindowState)
    abstract fun dragWindow(state: DockWindowState, screenPos: Offset)
    abstract fun closeTab(container: DockContainerState, windowId: String)
    abstract fun selectTab(container: DockContainerState, windowId: String)
    abstract fun dragTabStart(container: DockContainerState)
    abstract fun dragTab(container: DockContainerState, windowCoords: WindowCoords, screenPos: Offset)
    abstract fun dragTabEnd(container: DockContainerState)
    abstract fun minimizeWindow(state: DockWindowState)
    abstract fun maximizeWindow(state: DockWindowState)
    abstract fun closeWindow(state: DockWindowState)

}

class DockControllerImpl(
    private val dockWindowRepo: DockWindowRepo = DockWindowRepo(),
) : DockController() {

    private val windowsMap = mutableMapOf<String, DockWindowState>()
    private val floatingWindowsStack = mutableListOf<DockWindowState>()
    private var currentWindowId: String? = null
    private var targetWindowId: String? = null
    private var targetSlot: DockSpaceSlot? = null

    override fun onCreate() {
        scope.launch {
            dockWindowRepo.load().forEach {
                getOrAdd(it.toState())
            }
            _restored.value = true
        }
    }

    override fun onDestroy() {
        dockWindowRepo.save(windowsMap.map { it.value.toModel() })
        _saved.value = true
    }

    override fun get(id: String?) = windowsMap[id]

    override fun getOrAdd(state: DockWindowState): DockWindowState {
        var cachedState = windowsMap[state.id]
        if (cachedState == null) {
            cachedState = state
            windowsMap[state.id] = state
            if (!state.docked) {
                floatingWindowsStack.add(state)
            }
        }
        return cachedState
    }

    override fun remove(state: DockWindowState) {
        if (windowsMap.contains(state.id)) {
            windowsMap.remove(state.id)
            floatingWindowsStack.remove(state)
        }
    }

    override fun saveState() {
        scope.launch {
            dockWindowRepo.save(windowsMap.map { it.value.toModel() })
        }
    }

    override fun dragWindowStart(state: DockWindowState) {
        floatingWindowsStack.remove(state)
        floatingWindowsStack.add(state)
        currentWindowId = state.id
    }

    override fun dragWindowEnd(state: DockWindowState) {
        val id = currentWindowId
        val parentId = targetWindowId
        val slot = targetSlot
        if (id != null && parentId != null && slot != null) {
            dock(id, parentId, slot)
        } else {
            saveState()
        }
        targetSlot = null
        targetWindowId = null
        currentWindowId = null
    }

    private fun dock(id: String, parentId: String, slot: DockSpaceSlot) {
        val window = windowsMap[id] ?: return
        val parentWindow = windowsMap[parentId] ?: return
        val dockSpace = parentWindow.dockSpace ?: return

        when (dockSpace) {
            is DockSpaceState.Host -> when (slot) {
                DockSpaceSlot.Top -> dockSpace.top.add(window.id)
                DockSpaceSlot.Bottom -> dockSpace.bottom.add(window.id)
                DockSpaceSlot.Left -> dockSpace.left.add(window.id)
                DockSpaceSlot.Right -> dockSpace.right.add(window.id)
                DockSpaceSlot.Center -> dockSpace.center.add(window.id)
                else -> return
            }
        }

        window.docked = true
        window.dockSpace?.hoverSlot(null)
        floatingWindowsStack.remove(window)
        saveState()
    }

    override fun dragWindow(state: DockWindowState, screenPos: Offset) {
        Print.d("DockController", screenPos.toString())
        // find top window before current window
        val hoveredWindow = floatingWindowsStack.getOrNull(floatingWindowsStack.lastIndex - 1) ?: return
        targetSlot = hoveredWindow.dockSpace?.findAvailableSlot(
            hoveredWindow.position.toOffset(),
            hoveredWindow.size.toSize(),
            screenPos
        )
        targetWindowId = if (targetSlot == null) null else hoveredWindow.id
        hoveredWindow.dockSpace?.hoverSlot(targetSlot)
    }

    override fun closeTab(container: DockContainerState, windowId: String) {
        undock(container, windowId)
        saveState()
    }

    private fun undock(container: DockContainerState, windowId: String): DockWindowState? {
        container.windows.remove(windowId)
        container.currentWindow = container.windows.firstOrNull()
        val window = windowsMap[windowId]
        window?.docked = false
        return window
    }

    override fun selectTab(container: DockContainerState, windowId: String) {
        container.currentWindow = windowId
        saveState()
    }

    override fun dragTabStart(container: DockContainerState) {
        val currentWindow = container.currentWindow ?: return
        selectTab(container, currentWindow)
    }

    override fun dragTabEnd(container: DockContainerState) {
        // no-op
    }

    override fun dragTab(
        container: DockContainerState,
        windowCoords: WindowCoords,
        screenPos: Offset
    ) {
        val windowId = container.currentWindow ?: return
        val windowSize = windowCoords.coords?.size ?: return

        val isOutside = !hitRect(
            topLeft = Offset(windowCoords.awtX.toFloat(), windowCoords.awtY.toFloat()),
            size = Size(windowSize.width.toFloat(), windowSize.height.toFloat()),
            target = screenPos,
        )

        if (isOutside) {
            val window = undock(container, windowId)
            window?.position = screenPos.toDpOffset()
        }
    }

    override fun minimizeWindow(state: DockWindowState) {
        state.minimized = true
        saveState()
    }

    override fun maximizeWindow(state: DockWindowState) {
        state.maximized = !state.maximized
        saveState()
    }

    override fun closeWindow(state: DockWindowState) {
        state.visible = false
        saveState()
        state.onClose()
    }

}

class DockControllerPreview : DockController() {

    override fun onCreate() {}
    override fun onDestroy() {}
    override fun get(id: String?): DockWindowState? = null
    override fun getOrAdd(state: DockWindowState): DockWindowState {
        return DockWindowState(id = "Preview", title = "Preview")
    }
    override fun remove(state: DockWindowState) {}
    override fun saveState() {}
    override fun dragWindowStart(state: DockWindowState) {}
    override fun dragWindowEnd(state: DockWindowState) {}
    override fun dragWindow(state: DockWindowState, screenPos: Offset) {}
    override fun closeTab(container: DockContainerState, windowId: String) {}
    override fun selectTab(container: DockContainerState, windowId: String) {}
    override fun dragTabStart(container: DockContainerState) {}
    override fun dragTabEnd(container: DockContainerState) {}
    override fun dragTab(
        container: DockContainerState,
        windowCoords: WindowCoords,
        screenPos: Offset
    ) {}
    override fun minimizeWindow(state: DockWindowState) {}
    override fun maximizeWindow(state: DockWindowState) {}
    override fun closeWindow(state: DockWindowState) {}

}

@Composable
fun provideDockController(): DockController = provideController(
    preview = { DockControllerPreview() },
    impl = { DockControllerImpl() },
)
