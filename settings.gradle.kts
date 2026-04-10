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

rootProject.name = "Kanvas"
include(":kotlin-std")
include(":kotlin-std-gen")
include(":print")
include(":print-sandbox")
include(":kanvas")
include(":kanvas-rendering")
include(":kanvas-spirv")
include(":kanvas-shaderc")
include(":kanvas-shaderc-sandbox")
include(":kanvas-vk")
include(":kanvas-wgpu")
include(":kanvas-editor")
include(":kanvas-server")
include(":kanvas-assetc")