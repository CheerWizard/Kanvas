import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.components.resources)
                // Standard
                implementation(project(":print"))
                implementation(project(":kotlin-std"))
                implementation(kotlin("stdlib-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.atomicfu)
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                // Assetc
                implementation(project(":kanvas-assetc"))
                // Compose
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        buildTypes.release.proguard {
            isEnabled.set(false)
        }

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )

            packageName = "AssetC"
            packageVersion = "1.0.0"

            windows {
                iconFile.set(project.file("icons/ic_kanvas.ico"))
            }

            macOS {
                iconFile.set(project.file("icons/ic_kanvas.icns"))
            }

            linux {
                iconFile.set(project.file("icons/ic_kanvas.png"))
            }
        }
    }
}
