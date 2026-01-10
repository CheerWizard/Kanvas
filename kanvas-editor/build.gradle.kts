plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                implementation(project(":kanvas"))
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.gradle.tooling.api)
            }
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