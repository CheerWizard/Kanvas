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

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        binaries.all {
            linkerOpts(
                "-framework", "AudioToolbox",
                "-framework", "CoreAudio"
            )
        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // Rendering
                api(project(":kanvas-rendering"))
                // Compose
                api(compose.runtime)
                api(compose.foundation)
                api(compose.components.uiToolingPreview)
                api(compose.components.resources)
                // Standard
                api(project(":kotlin-std"))
            }
        }

        val androidMain by getting {
            dependencies {}
            dependsOn(commonMain)
        }

        val iosMain by creating {
            dependsOn(commonMain)
        }

        val desktopMain by getting {
            dependencies {
                // Gamepad
                implementation(libs.jinput)
                // Compose
                api(compose.desktop.currentOs)
            }
            dependsOn(commonMain)
        }

        val jsMain by getting {
            dependencies {}
            dependsOn(commonMain)
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