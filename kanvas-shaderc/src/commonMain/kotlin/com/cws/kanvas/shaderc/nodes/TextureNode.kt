package com.cws.kanvas.shaderc.nodes

private val register = Node.register { TextureNode(it) }

class TextureNode(override val expr: String) : Node()