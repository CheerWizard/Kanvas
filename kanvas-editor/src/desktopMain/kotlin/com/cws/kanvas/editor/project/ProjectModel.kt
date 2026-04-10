package com.cws.kanvas.editor.project

import kotlinx.serialization.Serializable

@Serializable
data class ProjectModel(
    val name: String,
    val root: String,
    val packageName: String,
    val mainActivity: String,
    var target: ProjectTarget,
    var buildType: ProjectBuildType,
    var launchMode: ProjectLaunchMode,
    var connectedDeviceId: String? = null,
    val modules: MutableSet<String> = mutableSetOf(),
)

@Serializable
enum class ProjectTarget {
    DESKTOP,
    WEB,
    ANDROID,
    IOS,
}

@Serializable
enum class ProjectBuildType {
    DEBUG,
    RELEASE,
}

@Serializable
enum class ProjectLaunchMode {
    DEBUG,
    DEFAULT
}
