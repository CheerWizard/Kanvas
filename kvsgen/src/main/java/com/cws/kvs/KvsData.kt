package com.cws.kvs

sealed class Type {
    data class Primitive(val name: String) : Type()
    data class List(val inner: Type) : Type()
    data class Custom(val name: String) : Type()
}

data class Field(val name: String, val type: Type, val defaultValue: String?)
data class Struct(val name: String, val fields: List<Field>)
data class Enum(val name: String, val values: Map<String, Int>)
data class Constant(val name: String, val value: String)
data class Import(val path: String)

data class KvsData(
    val imports: List<Import>,
    val structs: List<Struct>,
    val enums: List<Enum>,
    val constants: List<Constant>,
)
