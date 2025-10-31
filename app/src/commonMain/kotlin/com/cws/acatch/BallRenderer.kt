package com.cws.acatch

import com.cws.fmm.collections.ShortList
import com.cws.kanvas.gfx.core.Kanvas
import com.cws.kanvas.gfx.pipeline.Shader
import com.cws.kanvas.gfx.geometry.Geometry
import com.cws.kanvas.gfx.geometry.GeometryManager
import com.cws.kanvas.gfx.pipeline.UniformBuffer

class BallBuffer : UniformBuffer(
    binding = 0,
    typeSize = Ball.SIZE_BYTES,
    count = 100,
)

class BallRenderer(
    private val shaderManager: ShaderManager,
    private val geometryManager: GeometryManager,
) {

    private val shader = Shader()
    private val ballBuffer = BallBuffer()

    fun init() {
        shaderManager.load("ball.vert", "ball.frag") {
            shader.init(it)
        }
        geometryManager.add(Geometry(
            vertices = VertexList(),
            indices = ShortList()
        ))
        ballBuffer.init()
    }

    fun release() {
        shader.release()
        ballBuffer.release()
    }

    fun render(balls: BallList) {
        if (shader.isReady()) {
            ballBuffer.bind()
            shader.run()
            Kanvas.run {
                drawElementsInstanced(TRIANGLES, 6, UINT, 0, balls.size)
            }
        }
    }

}