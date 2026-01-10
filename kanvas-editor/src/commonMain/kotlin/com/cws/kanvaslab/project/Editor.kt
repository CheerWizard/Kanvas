package com.cws.kanvaslab.project

import kotlinx.serialization.Serializable

@Serializable
data class EditorConfig(
    val projects: MutableMap<String, ProjectConfig>,
)

class Editor(
    val config: EditorConfig,
) {

    private lateinit var currentProject: Project

    fun createProject(projectConfig: ProjectConfig) {
        // TODO this will
        //  1. Generate Gradle project folder structure
        //  2. Replace version, name, packageName with data from ProjectConfig
        //  3. Store new ProjectConfig in EditorConfig
        //  4. Open it
    }

    fun removeProject(projectName: String) {
        // TODO this will
        //  1. Erase project folder
        //  2. Erase project config from EditorConfig
        //  3. Ask project to clean itself, remove modules from runtime
        //  4.
    }

    fun closeProject(projectName: String) {

    }

    fun loadOpenedProjects() {

    }

}