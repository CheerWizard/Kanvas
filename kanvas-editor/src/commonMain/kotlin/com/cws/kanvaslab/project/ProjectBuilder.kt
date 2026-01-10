package com.cws.kanvaslab.project

expect class ProjectBuilder() {
    fun build(projectConfig: ProjectConfig)
    fun build(projectRoot: String, tasks: List<String>)
}