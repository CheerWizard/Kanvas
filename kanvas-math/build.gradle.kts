plugins {
    alias(libs.plugins.kotlin.multiplatform)
    id("com.android.library")
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

dependencies {
    ksp(project(":fmm-ksp"))
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
                implementation(project(":fmm"))
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

        val stdMain by creating {
            dependsOn(commonMain)
        }

        val androidMain by getting {
            dependsOn(stdMain)
        }

        val desktopMain by getting {
            dependsOn(stdMain)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }

        val iosX64Main by getting { dependsOn(stdMain) }
        val iosArm64Main by getting { dependsOn(stdMain) }
        val iosSimulatorArm64Main by getting { dependsOn(stdMain) }
    }
}

android {
    namespace = "com.cws.kanvas.math"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
}