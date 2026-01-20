pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("multiplatform") version "2.2.10"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Catch"
include(":app")
include(":kotlin-std")
include(":kotlin-std-gen")
include(":print")
include(":print-sandbox")
include(":kanvas")
include(":kanvas-math")
include(":kanvas-rendering")
include(":kanvas-vk-contract")
include(":kanvas-vk-native")
include(":kanvas-vk-jvm")
include(":kanvas-wgpu")
include(":kanvas-editor")
include(":kanvas-server")