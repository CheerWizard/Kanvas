plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("com.android.library")
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
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

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // Math
                implementation(project(":kanvas-math"))
                // Logging
                implementation(project(":printer"))
                // Fast Memory Model
                implementation(project(":fmm"))
                // Compose
                implementation("org.jetbrains.compose.runtime:runtime:1.7.1")
                implementation("org.jetbrains.compose.foundation:foundation:1.7.1")
                implementation(compose.components.uiToolingPreview)
                // Coroutines and Atomics
                implementation(libs.atomicfu)
                implementation(libs.kotlinx.coroutines.core)
                implementation(kotlin("stdlib-common"))
            }
        }

        val jniMain by creating {
            dependsOn(commonMain)
        }

        val cinteropMain by creating {
            dependsOn(commonMain)
        }

        val wasmMain by creating {
            dependsOn(cinteropMain)
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.10.1")
                implementation(libs.androidx.core.ktx)
            }
            dependsOn(jniMain)
        }

        val iosMain by creating {
            dependsOn(cinteropMain)
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
            dependsOn(jniMain)
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
            dependsOn(wasmMain)
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
    namespace = "com.cws.kanvas.gfx"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 36

        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++20"
                arguments += listOf("-DANDROID_SUPPORT_FLEXIBLE_PAGE_SIZES=ON", "-DCMAKE_BUILD_TYPE=Debug")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        debug {
            isJniDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/cpp/STC/CMakeLists.txt")
        }
    }

    sourceSets["main"].assets.srcDir("$buildDir/generated/commonAssets")
}

dependencies {
    "ksp"(project(":fmm-ksp"))
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
    from("src/cpp/STC/shaders")
    into("$buildDir/generated/commonAssets/shaders")
}

tasks.named("preBuild") {
    dependsOn("copyCommonResourcesToAssets")
}

tasks.register("ksp") {
    dependsOn("kspCommonMainKotlinMetadata")
}