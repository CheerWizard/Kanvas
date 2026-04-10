plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
}

kotlin {
    jvm("desktop")

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                // Standard
                api(project(":kotlin-std"))
            }
        }

        val desktopMain by getting {
            dependencies {}
        }
    }
}

dependencies {
    "ksp"(project(":kotlin-std-gen"))
}

tasks.register("ksp") {
    dependsOn("kspCommonMainKotlinMetadata")
}