package com.cws.kanvaslab.project

import com.cws.kanvas.core.GameModule

expect class GameModuleLoader() {
    fun load(projectConfig: ProjectConfig, moduleName: String): GameModule?
}