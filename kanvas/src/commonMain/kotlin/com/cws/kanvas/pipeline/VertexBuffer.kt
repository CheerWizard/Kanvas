package com.cws.kanvas.pipeline

import com.cws.kanvas.core.Kanvas
import com.cws.fmm.ObjectList

class VertexBuffer(size: Int) : GpuBuffer(
    type = Kanvas.VERTEX_BUFFER,
    typeSize = Vertex.SIZE_BYTES,
    capacity = size
) {

    fun add(vertices: ObjectList) {
        ensureCapacity(size = vertices.size)
        addArray(vertices)
    }

    fun update(index: Int, vertices: ObjectList) {
        ensureCapacity(index = index, size = vertices.size)
        update(index) {
            setArray(index, vertices)
        }
    }

}