package com.cws.std.memory

import java.io.File
import kotlin.use

actual object NativeLibrary {

    @Suppress("UnsafeDynamicallyLoadedCode")
    actual inline fun <reified T> load(libName: String) {
        val os = System.getProperty("os.name").lowercase()
        val arch = System.getProperty("os.arch").lowercase()

        val libFile = when {
            os.contains("win") && (arch == "amd64" || arch == "x86_64") -> "windows-x86_64"
            os.contains("linux") && (arch == "amd64" || arch == "x86_64") -> "linux-x86_64"
            os.contains("linux") && (arch == "aarch64" || arch == "arm64") -> "linux-arm64"
            os.contains("mac") && (arch == "x86_64") -> "macos-x86_64"
            os.contains("mac") && (arch == "aarch64" || arch == "arm64") -> "macos-arm64"
            else -> error("Unsupported platform: $os / $arch")
        }

        val tmpFile = createTempFile(suffix = File(libFile).extension)

        T::class.java.getResourceAsStream("/$libFile")!!.use { input ->
            tmpFile.outputStream().use { output ->
                output.write(input.readBytes())
            }
        }

        System.load(tmpFile.absolutePath)
    }

}