plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
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

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}