package com.cws.kanvas.editor.docking.logic

import com.cws.kanvas.editor.core.json.AppJson
import com.cws.print.Print
import kotlinx.serialization.encodeToString
import java.io.File
import java.lang.Exception

class DockWindowRepo(
    private val dockspaceFilepath: String = "dockspace.json",
) {

    companion object {
        private val TAG = DockWindowRepo::class.simpleName.toString()
    }

    fun load(): List<DockWindowModel> {
        return try {
            val file = File(dockspaceFilepath)
            val json = file.readText()
            AppJson.decodeFromString<List<DockWindowModel>>(json)
        } catch (e: Exception) {
            Print.w(TAG, "Failed to read from $dockspaceFilepath", e)
            emptyList()
        }
    }

    fun save(models: List<DockWindowModel>) {
        try {
            val json = AppJson.encodeToString(models)
            val file = File(dockspaceFilepath)
            file.writeText(json)
        } catch (e: Exception) {
            Print.w(TAG, "Failed to write into $dockspaceFilepath", e)
        }
    }

}
