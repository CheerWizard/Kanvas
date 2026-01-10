plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    // http server
    val ktor = "3.0.0"
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    // Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    // Protobuf
    implementation(libs.protobuf.kotlin)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(project(":print"))

    // logging

    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.ktor.server.tests)
}