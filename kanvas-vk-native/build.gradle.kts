import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    mingwX64()
    linuxX64()
    macosArm64()
    macosX64()
    androidNativeX64()
    androidNativeArm64()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

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

                    when (konanTarget.family) {
                        Family.ANDROID -> compilerOpts(
                            "-DVK_USE_PLATFORM_ANDROID_KHR"
                        )

                        Family.LINUX -> compilerOpts(
                            "-DVK_USE_PLATFORM_XCB_KHR"
                        )

                        Family.MINGW -> compilerOpts(
                            "-DVK_USE_PLATFORM_WIN32_KHR"
                        )

                        Family.OSX, Family.IOS -> compilerOpts(
                            "-DVK_USE_PLATFORM_METAL_EXT"
                        )

                        else -> {}
                    }
                }
            }
        }

        binaries {
            sharedLib {
                baseName = "kanvas-vk-native"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kanvas-vk-contract"))
                implementation(kotlin("stdlib-common"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.atomicfu)
            }
        }

        val nativeMain by creating {
            kotlin.srcDir("src/nativeMain/kotlin")
            kotlin.srcDir("build/classes/kotlin/androidNativeArm64")
            dependsOn(commonMain)
        }

        val androidNativeArm64Main by getting {
            dependsOn(nativeMain)
        }

        val androidNativeX64Main by getting {
            dependsOn(nativeMain)
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }

        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }

        val macosX64Main by getting {
            dependsOn(nativeMain)
        }

        val macosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val iosX64Main by getting {
            dependsOn(nativeMain)
        }

        val iosArm64Main by getting {
            dependsOn(nativeMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(nativeMain)
        }
    }
}