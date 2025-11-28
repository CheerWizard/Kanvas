plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
            commonWebpackConfig {
                cssSupport {
                    enabled = true
                }
            }
            webpackTask {
                copy {
                    from("$projectDir/src/commonMain/resources")
                    into("$buildDir/processedResources/js/main")
                }
            }
        }
        nodejs {
            binaries.executable()
        }
    }

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                runtimeOnly(libs.gradle.tooling.api)
                implementation(project(":kanvas"))
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
        }

        val jsMain by getting {
            dependsOn(commonMain)
        }
    }
}

tasks.register<Copy>("copyCommonResourcesToAssets") {
    from("src/commonMain/resources")
    into("$buildDir/generated/commonAssets")
}

tasks.named("desktopProcessResources") {
    dependsOn("copyCommonResourcesToAssets")
}

tasks.named("jsBrowserProductionWebpack") {
    dependsOn("copyCommonResourcesToAssets")
}