plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
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
                // Standard
                api(project(":kotlin-std"))
            }
        }

        val desktopMain by getting {
            dependencies {
                // LWJGL
                val osName = System.getProperty("os.name").lowercase()
                val osArch = System.getProperty("os.arch").lowercase()
                val lwjglVersion = "3.4.1"
                val lwjglNatives = when {
                    osName.contains("mac") && (osArch.contains("arm") || osArch.contains("aarch")) -> "natives-macos-arm64"
                    osName.contains("mac") -> "natives-macos"
                    osName.contains("windows") -> "natives-windows"
                    osName.contains("linux") -> "natives-linux"
                    else -> error("Unsupported OS: $osName / $osArch")
                }

                // Shaderc bindings for shader compilation (e.g., GLSL -> SPIR-V)
                implementation("org.lwjgl:lwjgl-shaderc:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-shaderc:$lwjglVersion:$lwjglNatives")

                // LWJGL core
                implementation("org.lwjgl:lwjgl:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")
            }
        }
    }
}

dependencies {
    "ksp"(project(":kotlin-std-gen"))
}

tasks.register("ksp") {
    dependsOn("kspCommonMainKotlinMetadata")
}