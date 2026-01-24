plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("com.android.library")
}

kotlin {
    androidTarget()
    js(IR) {
        browser {
            binaries.library()
        }
        nodejs {
            binaries.library()
        }
        compilerOptions {
            // enable support of BigInt for Long
            freeCompilerArgs.add("-Xir-per-module")
            freeCompilerArgs.add("-Xuse-js-bigint-for-long")
        }
    }
    jvm("desktop")
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Logger
                implementation(project(":print"))
                // Standard
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.atomicfu)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val jniMain by creating {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn(jniMain)
        }

        val desktopMain by getting {
            dependsOn(jniMain)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val iosX64Main by getting { dependsOn(nativeMain) }
        val iosArm64Main by getting { dependsOn(nativeMain) }
        val iosSimulatorArm64Main by getting { dependsOn(nativeMain) }
    }
}

project.extra["jniProject"] = "cmemory"
apply(from = "$rootDir/scripts/jni.gradle.kts")

android {
    namespace = "com.cws.std"
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
            path = file("src/cpp/cmemory/CMakeLists.txt")
        }
    }
}