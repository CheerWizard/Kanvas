import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // Engine
                implementation(project(":kanvas"))
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                // Asset Compiler
                implementation(project(":kanvas-assetc"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.cws.kanvas.editor.MainKt"

        buildTypes.release.proguard {
            isEnabled.set(false)
        }

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Msi,
                TargetFormat.Deb
            )

            packageName = "KanvasEditor"
            packageVersion = "1.0.0"

            windows {
                iconFile.set(project.file("icons/KanvasLogo.ico"))
            }

            macOS {
                iconFile.set(project.file("icons/KanvasLogo.icns"))
            }

            linux {
                iconFile.set(project.file("icons/KanvasLogo.png"))
            }
        }
    }
}