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
            dependencies {
                implementation(project(":kanvas-shaderc"))
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
        }
    }
}

dependencies {
    "ksp"(project(":kotlin-std-gen"))
}

tasks.register("ksp") {
    dependsOn("kspCommonMainKotlinMetadata")
}