package com.cws.kvs

fun main() {
    val kvs = """
        struct Test {
            int id = 0
            string name = "default"
        }

        enum TestEnum {
            VALUE_ONE = 1
            VALUE_TWO = 2
        }

        const PI = 3.14
    """.trimIndent()

    val parser = KvsParser()
    val generator = KvsGenerator()
    val kvsData = parser.parse(kvs)
    println("--- Kotlin ---")
    println(generator.generateKotlin(kvsData))
    println("--- C++ ---")
    println(generator.generateCpp(kvsData))
}
