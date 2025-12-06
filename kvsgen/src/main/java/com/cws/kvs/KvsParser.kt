package com.cws.kvs

class KvsParser {

    fun parse(source: String): KvsData {
        val structs = mutableListOf<Struct>()
        val enums = mutableListOf<Enum>()
        val constants = mutableListOf<Constant>()
        val imports = mutableListOf<Import>()

        val importRegex = Regex("""import\s+"([^"]+)"""")
        val structRegex = Regex("struct\\s+(\\w+)\\s*\\{([^}]*)}")
        val enumRegex = Regex("enum\\s+(\\w+)\\s*\\{([^}]*)}")
        val constRegex = Regex("const\\s+(\\w+)\\s*=\\s*([^\\s]+)")

        importRegex.findAll(source).map {
            Import(it.groupValues[1])
        }.toList()

        structRegex.findAll(source).forEach { match ->
            val name = match.groupValues[1]
            val body = match.groupValues[2].trim()
            val fields = body.lines()
                .mapNotNull { line ->
                    val parts = line.trim().split("=", " ").filter { it.isNotBlank() }
                    if (parts.size >= 2) Field(parts[1], parts[0], parts.getOrNull(2)) else null
                }
            structs += Struct(name, fields)
        }

        enumRegex.findAll(source).forEach { match ->
            val name = match.groupValues[1]
            val body = match.groupValues[2].trim()
            val values = body.lines()
                .mapNotNull { line ->
                    val parts = line.trim().split("=").map { it.trim() }
                    if (parts.size == 2) parts[0] to parts[1].toInt() else null
                }.toMap()
            enums += Enum(name, values)
        }

        constRegex.findAll(source).forEach { match ->
            constants += Constant(match.groupValues[1], match.groupValues[2])
        }

        return KvsData(
            imports = imports,
            structs = structs,
            enums = enums,
            constants = constants,
        )
    }

    private fun parseType(typeStr: String): Type {
        val trimmed = typeStr.trim()
        return if (trimmed.startsWith("Vector<") && trimmed.endsWith(">")) {
            val inner = trimmed.substringAfter("Vector<").substringBeforeLast(">")
            Type.List(parseType(inner))
        } else when (trimmed) {
            "boolean", "int8", "int16", "int32", "int64",
            "uint8", "uint16", "uint32", "uint64",
            "double", "string" -> Type.Primitive(trimmed)
            else -> Type.Custom(trimmed)
        }
    }

}