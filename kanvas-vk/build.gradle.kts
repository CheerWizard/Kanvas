import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvm("desktop")
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    targets.withType<KotlinNativeTarget>().all {
        compilations["main"].cinterops {
            val vk by creating {
                defFile(project.file("src/cinterop/vk.def"))
            }
        }
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

        val vkNativeMain by creating {
            dependsOn(jniMain)
            kotlin.srcDir("build/classes/kotlin/iosArm64/main/cinterop/kanvas-vk-cinterop-vk/default/linkdata/package_vk")
        }

        val iosMain by creating { dependsOn(vkNativeMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }
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