package com.cws.kanvas.assetc.filebrowser

import com.cws.kanvas.assetc.core.json.AppJson
import com.cws.print.Print
import com.cws.std.storage.DesktopPreferences
import kotlinx.serialization.encodeToString

class FileBrowserRepo {

    companion object {
        private val TAG = FileBrowserRepo::class.simpleName.orEmpty()
        private const val KEY_FILE_BROWSER_MODEL = "KEY_FILE_BROWSER_MODEL"
    }

    private val preferences = DesktopPreferences("app_preferences")

    fun load(): FileBrowserModel? {
        return try {
            val json = preferences.getString(KEY_FILE_BROWSER_MODEL, "")
            AppJson.decodeFromString(json)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to load file browser model from preferences!", e)
            null
        }
    }

    fun save(fileBrowserModel: FileBrowserModel) {
        try {
            val json = AppJson.encodeToString(fileBrowserModel)
            preferences.setString(KEY_FILE_BROWSER_MODEL, json)
        } catch (e: Exception) {
            Print.e(TAG, "Failed to save file browser model into preferences!", e)
        }
    }

}
