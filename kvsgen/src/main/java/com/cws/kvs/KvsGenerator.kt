package com.cws.kvs

class KvsGenerator {

    fun generateKotlin(data: KvsData): String {
        val builder = StringBuilder()

        generateKotlinImports(data.imports)

        data.structs.forEach { struct ->
            builder.appendLine("data class ${struct.name}(")

            struct.fields.forEachIndexed { i, f ->
                val default = f.defaultValue?.let { " = $it" } ?: ""
                builder.append("    val ${f.name}: ${f.type}$default")
                if (i != struct.fields.lastIndex) {
                    builder.appendLine(",")
                } else {
                    builder.appendLine()
                }
            }

            builder.appendLine(")")
        }

        data.enums.forEach { enum ->
            builder.appendLine("enum class ${enum.name} {")

            enum.values.keys.forEachIndexed { i, k ->
                builder.append("    $k")
                if (i != enum.values.size - 1) {
                    builder.appendLine(",")
                } else {
                    builder.appendLine()
                }
            }

            builder.appendLine("}")
        }

        data.constants.forEach { constant ->
            builder.appendLine("const val ${constant.name} = ${constant.value}")
        }

        return builder.toString()
    }

    fun generateCpp(data: KvsData): String {
        val builder = StringBuilder()

        generateCppIncludes(data.imports)

        data.structs.forEach { s ->
            builder.appendLine("struct ${s.name} {")

            s.fields.forEach { f ->
                val default = f.defaultValue?.let { " = $it" } ?: ""
                builder.appendLine("    ${f.type} ${f.name}$default;")
            }

            builder.appendLine("};")
        }

        data.enums.forEach { e ->
            builder.appendLine("enum class ${e.name} {")

            e.values.entries.forEachIndexed { i, entry ->
                builder.append("    ${entry.key} = ${entry.value}")
                if (i != e.values.size - 1) {
                    builder.appendLine(",")
                } else {
                    builder.appendLine()
                }
            }

            builder.appendLine("};")
        }

        data.constants.forEach { c ->
            builder.appendLine("const auto ${c.name} = ${c.value};")
        }

        return builder.toString()
    }

    private fun mapToKotlin(type: Type): String = when(type) {
        is Type.Primitive -> when (type.name) {
            "boolean" -> "Boolean"
            "int8" -> "Byte"
            "int16" -> "Short"
            "int32" -> "Int"
            "int64" -> "Long"
            "uint8" -> "UByte"
            "uint16" -> "UShort"
            "uint32" -> "UInt"
            "uint64" -> "ULong"
            "double" -> "Double"
            "string" -> "String"
            else -> throw IllegalArgumentException("Unknown primitive: ${type.name}")
        }
        is Type.List -> "List<${mapToKotlin(type.inner)}>"
        is Type.Custom -> type.name
    }

    private fun mapToCpp(type: Type): String = when (type) {
        is Type.Primitive -> when(type.name) {
            "boolean" -> "bool"
            "int8" -> "int8_t"
            "int16" -> "int16_t"
            "int32" -> "int32_t"
            "int64" -> "int64_t"
            "uint8" -> "uint8_t"
            "uint16" -> "uint16_t"
            "uint32" -> "uint32_t"
            "uint64" -> "uint64_t"
            "double" -> "double"
            "string" -> "std::string"
            else -> throw IllegalArgumentException("Unknown primitive: ${type.name}")
        }
        is Type.List -> "std::vector<${mapToCpp(type.inner)}>"
        is Type.Custom -> type.name
    }

    private fun generateKotlinImports(imports: List<Import>): String =
        imports.joinToString("\n") { """import "${it.path.removeSuffix(".kvs")}" """ }

    private fun generateCppIncludes(imports: List<Import>): String =
        imports.joinToString("\n") { """#include "${it.path}" """ }

}