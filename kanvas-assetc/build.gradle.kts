plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
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

                // OpenAL (for sound engine)
                implementation("org.lwjgl:lwjgl-openal:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-openal:$lwjglVersion:$lwjglNatives")

                // Assimp (supports huge number of 3D models formats)
                implementation("org.lwjgl:lwjgl-assimp:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-assimp:$lwjglVersion:$lwjglNatives")

                // STB bindings (images, ttf/rect pack, stb_vorbis audio decoding)
                implementation("org.lwjgl:lwjgl-stb:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-stb:$lwjglVersion:$lwjglNatives")

                // Shaderc bindings for shader compilation (e.g., GLSL -> SPIR-V)
                implementation("org.lwjgl:lwjgl-shaderc:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl-shaderc:$lwjglVersion:$lwjglNatives")

                // LWJGL core
                implementation("org.lwjgl:lwjgl:$lwjglVersion")
                runtimeOnly("org.lwjgl:lwjgl:$lwjglVersion:$lwjglNatives")

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
    }
}