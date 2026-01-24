plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    sourceSets {
        val jniMain by creating {
            dependencies {
                // Standard
                implementation(project(":kotlin-std"))
                implementation(kotlin("stdlib-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.atomicfu)
            }
        }

        val androidMain by getting {
            dependsOn(jniMain)
        }

        val desktopMain by getting {
            dependsOn(jniMain)
        }
    }
}

project.extra["jniProject"] = "vk"
apply(from = "$rootDir/scripts/jni.gradle.kts")

android {
    namespace = "com.cws.kanvas.vk"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
        }
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/cpp/vk/CMakeLists.txt")
        }
    }
}