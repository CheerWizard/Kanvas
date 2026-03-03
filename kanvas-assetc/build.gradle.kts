plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    sourceSets {
        val desktopMain by getting {
            dependencies {
                // LWJGL
                val osName = System.getProperty("os.name").lowercase()
                val osArch = System.getProperty("os.arch").lowercase()
                val lwjglVersion = "3.4.1"
                val lwjglNatives = when {
                    osName.contains("windows") -> "natives-windows"
                    osName.contains("linux") -> "natives-linux"
                    osName.contains("mac") -> "natives-macos"
                    else -> throw GradleException("Unsupported OS: $osName")
                }

                // Assimp (supports huge number of 3D models formats)
                implementation("org.lwjgl:lwjgl-assimp:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-assimp:$lwjglVersion:$lwjglNatives")

                // STB bindings (images, ttf/rect pack, stb_vorbis audio decoding)
                implementation("org.lwjgl:lwjgl-stb:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-stb:$lwjglVersion:$lwjglNatives")

                // Shaderc bindings for shader compilation (e.g., GLSL -> SPIR-V)
                implementation("org.lwjgl:lwjgl-shaderc:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-shaderc:$lwjglVersion:$lwjglNatives")

                // Standard
                implementation(project(":kanvas-math"))
                implementation(project(":print"))
                implementation(project(":kotlin-std"))
                implementation(kotlin("stdlib-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.atomicfu)
            }
        }
    }
}