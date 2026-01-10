val lwjglVersion = "3.3.6"

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("com.android.library")
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    androidTarget()
    js(IR) {
        browser {
            binaries.library()
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
        }
        nodejs {
            binaries.library()
        }
    }
    jvm("desktop")
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.all {
            linkerOpts(
                "-framework", "AudioToolbox",
                "-framework", "CoreAudio"
            )
        }
    }

//    val iosTargets = listOf("iosX64", "iosArm64", "iosSimulatorArm64")
//    iosTargets.forEach { targetName ->
//        val target = kotlin.targets.getByName(targetName) as org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
//        target.compilations.getByName("main").cinterops {
//            val moltenVk by creating {
//                definitionFile = file("src/moltenVkMain/cinterop/vulkan.def")
//            }
//        }
//    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // Math
                api(project(":kanvas-math"))
                // Logging
                api(project(":print"))
                // Standard
                api(project(":kotlin-std"))
                // Compose
                api("org.jetbrains.compose.runtime:runtime:1.7.1")
                api("org.jetbrains.compose.foundation:foundation:1.7.1")
                api(compose.components.uiToolingPreview)
                // Coroutines and Atomics
                api(libs.atomicfu)
                api(libs.kotlinx.coroutines.core)
                api(kotlin("stdlib-common"))
                api(libs.kotlinx.serialization.core)
                api(libs.kotlinx.serialization.json)
            }
        }

        val glslMain by creating {
            dependsOn(commonMain)
        }

        val wgslMain by creating {
            dependsOn(commonMain)
        }

        val vkMain by creating {
            dependencies {
                // LWJGL Vulkan
                val osName = System.getProperty("os.name").lowercase()
                val lwjglNatives = when {
                    osName.contains("windows") -> "natives-windows"
                    osName.contains("mac") -> "natives-macos"
                    osName.contains("linux") -> "natives-linux"
                    else -> throw GradleException("Unsupported OS: $osName")
                }
                implementation("org.lwjgl:lwjgl-vulkan:${lwjglVersion}")
                implementation("org.lwjgl:lwjgl-vma:${lwjglVersion}")
                implementation("org.lwjgl:lwjgl-shaderc:${lwjglVersion}:${lwjglNatives}")
                runtimeOnly("org.lwjgl:lwjgl-vulkan:${lwjglVersion}:${lwjglNatives}")
                runtimeOnly("org.lwjgl:lwjgl-vma:${lwjglVersion}:${lwjglNatives}")
                runtimeOnly("org.lwjgl:lwjgl-shaderc:${lwjglVersion}:${lwjglNatives}")
            }
            dependsOn(glslMain)
        }

        val moltenVkMain by creating {
            dependencies {}
            dependsOn(glslMain)
        }

        val wgpuMain by creating {
            dependencies {
                // TODO create WGPU bindings
            }
            dependsOn(wgslMain)
        }

        val androidMain by getting {
            dependencies {
                // Compose
                api("androidx.activity:activity-compose:1.10.1")
                api(libs.androidx.core.ktx)
            }
            dependsOn(vkMain)
        }

        val iosMain by creating {
            dependsOn(moltenVkMain)
        }

        val desktopMain by getting {
            dependencies {
                // LWJGL Window
                val osName = System.getProperty("os.name").lowercase()
                val lwjglNatives = when {
                    osName.contains("windows") -> "natives-windows"
                    osName.contains("mac") -> "natives-macos"
                    osName.contains("linux") -> "natives-linux"
                    else -> throw GradleException("Unsupported OS: $osName")
                }
                implementation("org.lwjgl:lwjgl:${lwjglVersion}")
                runtimeOnly("org.lwjgl:lwjgl:${lwjglVersion}:${lwjglNatives}")
                // Compose
                api(compose.desktop.currentOs)
            }
            dependsOn(vkMain)
        }

        val jsMain by getting {
            dependencies {
                api(compose.html.core)
                api(compose.runtime)
            }
            dependsOn(wgpuMain)
        }

        val iosX64Main by getting {
            dependsOn(iosMain)
        }
        
        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    compileSdk = 36
    namespace = "com.cws.kanvas"

    defaultConfig {
        minSdk = 26
        targetSdk = 36
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets["main"].assets.srcDir("$buildDir/generated/commonAssets")
}

dependencies {
    "ksp"(project(":kotlin-std-gen"))
}

afterEvaluate {
    tasks.named("kspDebugKotlinAndroid") {
        enabled = false
    }
    tasks.named("kspReleaseKotlinAndroid") {
        enabled = false
    }
    tasks.named("kspKotlinDesktop") {
        enabled = false
    }
    tasks.named("kspKotlinJs") {
        enabled = false
    }
}

tasks.register<Copy>("copyCommonResourcesToAssets") {
    from("src/commonMain/resources")
    into("$buildDir/generated/commonAssets")
}

tasks.named("preBuild") {
    dependsOn("copyCommonResourcesToAssets")
}

tasks.register("ksp") {
    dependsOn("kspCommonMainKotlinMetadata")
}