plugins {
    kotlin("jvm") version "2.0.0"           // or latest stable
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.protobuf") version "0.9.4"   // for protobuf codegen
}

repositories {
    mavenCentral()
}

dependencies {
    // http server
    val ktor = "3.0.0"
    implementation("io.ktor:ktor-server-core:$ktor")
    implementation("io.ktor:ktor-server-netty:$ktor")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")

    // Protobuf
    val protobuf = "3.25.3"
    implementation("com.google.protobuf:protobuf-kotlin:${protobuf}")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    // logging
    implementation(project(":printer"))

    // Testing
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-tests:$ktor")
}