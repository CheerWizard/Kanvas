plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
//    linuxX64()
//    macosX64()
//    macosArm64()
//    mingwX64()
    androidNativeX64()
    androidNativeArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget>().configureEach {
        compilations.getByName("main") {
            cinterops {
                create("vulkan") {
                    defFile(
                        project.file("src/cinterop/vulkan.def")
                    )
                }
            }
        }

        binaries {
            sharedLib {
                baseName = "kanvas-vk"
            }
        }
    }

    sourceSets {
//        val main by creating {
//            dependencies {
////                // Math
////                api(project(":kanvas-math"))
////                // Logging
////                api(project(":print"))
////                // Standard
////                api(project(":kotlin-std"))
//                // Coroutines and Atomics
//                api(libs.atomicfu)
//                api(libs.kotlinx.coroutines.core)
//                api(kotlin("stdlib-common"))
//                api(libs.kotlinx.serialization.core)
//                api(libs.kotlinx.serialization.json)
//            }
//        }

        // Shared wrapper source set
        val nativeMain by creating {
            kotlin.srcDir("src/nativeMain/kotlin")
            kotlin.srcDir("build/classes/kotlin/androidNativeArm64")
            dependencies {
                api(kotlin("stdlib-common"))
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.serialization.core)
                api(libs.kotlinx.serialization.json)
                api(libs.atomicfu)
            }
        }

        // Android Native arm64
        val androidNativeArm64Main by getting {
            dependsOn(nativeMain)
        }

        // Android Native x64
        val androidNativeX64Main by getting {
            dependsOn(nativeMain)
        }
    }
}

dependencies {
    "ksp"(project(":kotlin-std-gen"))
}

tasks.register("ksp") {
    dependsOn("kspMainKotlinMetadata")
}