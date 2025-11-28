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
include(":fmm")
include(":fmm-ksp")
include(":printer")
include(":printer-sandbox")
include(":kanvas")
include(":kanvas-math")
include(":kanvas-gfx")
include(":kanvas-editor")
include(":kanvas-server")