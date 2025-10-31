package com.cws.acatch

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.cws.acatch.collision.CollisionBox
import com.cws.acatch.data.GameGrid
import com.cws.acatch.data.GameScene
import com.cws.acatch.data.Score
import com.cws.acatch.data.generateBalls
import com.cws.fmm.stackScope
import com.cws.kanvas.core.Game
import com.cws.kanvas.math.Vec2
import com.cws.kanvas.math.Vec4

class TestGame : Game() {

    private val score = mutableStateOf(Score())
    private val animateScore = mutableStateOf(false)

    private var scene: GameScene? = null

    private val ballRenderer = BallRenderer(
        shaderManager = engine.shaderManager,
        geometryManager = engine.geometryManager,
    )

    override fun onCreate() {
        ballRenderer.init()

        val balls = generateBalls(
            size = 100,
            width = config.window.width.toFloat(),
            height = config.window.height.toFloat()
        )

        val scene = GameScene(
            screenBox = CollisionBox(
                x = 0f,
                y = 0f,
                w = config.window.width.toFloat(),
                h = config.window.height.toFloat()
            ),
            grid = GameGrid(
                width = config.window.width,
                height = config.window.height,
                cellSize = 20
            ),
            balls = balls,
            projectiles = ProjectileList(1),
        )

        this.scene = scene
    }

    override fun onDestroy() {
        ballRenderer.release()
    }

    override fun onUpdate(dtMillis: Float) {
        val t = dtMillis / 1000f

        val scene = this.scene ?: return

        val screenBox = scene.screenBox
        val balls = scene.balls
        val projectiles = scene.projectiles
        val grid = scene.grid
        val sensor = engine.inputSensorManager.sensor

        grid.clear()
        grid.fill(
            entities = balls,
            visible = { balls[it].visible == 1 },
            pos = { balls[it].pos },
        )

        stackScope {
            balls.forEachIndexed { i, ball ->
                if (ball.visible == 1) {
                    val x0 = screenBox.x
                    val x1 = screenBox.x + screenBox.w
                    val y0 = screenBox.y
                    val y1 = screenBox.y + screenBox.h
                    val r = ball.radius
                    ball.velocity += Vec2(sensor.acceleration.x * t, sensor.acceleration.y * t)
                    var x = ball.pos.x
                    var y = ball.pos.y
                    val dx = x + sensor.direction.x * ball.velocity.x * t
                    val dy = y + sensor.direction.y * ball.velocity.y * t
                    if (dx - r > x0 && dx + r < x1) x = dx
                    if (dy - r > y0 && dy + r < y1) y = dy
                    ball.pos = Vec2(x, y)
                }
            }

            projectiles.forEachIndexed { i, projectile ->
                if (projectile.visible) {
                    projectile.velocity += projectile.acceleration * t
                    projectile.pos += projectile.dir * projectile.velocity * t

                    val px = projectile.pos.x
                    val py = projectile.pos.y
                    val col = grid.col(px)
                    val row = grid.row(py)

                    for (dy in -1..1) {
                        for (dx in -1..1) {
                            val c = (col + dx).coerceIn(0, grid.cols - 1)
                            val r = (row + dy).coerceIn(0, grid.rows - 1)
                            val cellIndex = r * grid.cols + c
                            val cellBalls = grid.cells[cellIndex]
                            repeat(cellBalls.size) { j ->
                                val ball = balls[j]
                                val dx = px - ball.pos.x
                                val dy = py - ball.pos.y
                                val r = ball.radius * ball.radius
                                val l = dx * dx + dy * dy
                                when {
                                    r >= l -> {
                                        onProjectileHit(j)
                                        destroyProjectile(i)
                                        return@forEachIndexed
                                    }
                                    r < l -> {
                                        onProjectileMissed(j)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onRender(dtMillis: Float) {
        scene?.let { scene ->
            ballRenderer.render(scene.balls)
        }
    }

    @Composable
    override fun onRenderUI() {
        val score by this.score

        val animateScoreScale by animateFloatAsState(
            targetValue = if (animateScore.value) 1.5f else 1f,
            animationSpec = tween(100),
            label = "animateScoreScale",
            finishedListener = {
                if (it == 1.5f) animateScore.value = false
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, Color.LightGray, Color.Transparent),
                        center = Offset(
                            config.window.width.toFloat() / 2,
                            config.window.height.toFloat() / 2,
                        ),
                        radius = config.window.height.toFloat() / 2,
                    )
                )
        ) {
            BasicText(
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(animateScoreScale),
                text = score.value.toString(),
                style = TextStyle(
                    fontSize = 60.sp,
                    color = Color(1f, 1f, 1f, 1f)
                )
            )
        }
    }

    override fun onTapPressed(x: Float, y: Float) {
        spawnProjectile(x, y)
    }

    private fun onProjectileHit(i: Int) {
        val scene = this.scene ?: return
        val balls = scene.balls
        val oldScore = score.value
        val ball = balls[i]
        score.value = score.value.copy(
            value = oldScore.value + 1,
            color = ball.color
        )
        animateScore.value = true
        ball.visible = 0
    }

    private fun onProjectileMissed(i: Int) {}

    private fun spawnProjectile(x: Float, y: Float) {
        val scene = this.scene ?: return
        val projectiles = scene.projectiles
        val y = 2000f
        val i = 0
        stackScope {
            val projectile = projectiles[i]
            projectile.pos = Vec2(x, y)
            projectile.velocity = Vec2()
            projectile.dir = Vec2(y = -1f)
            projectile.acceleration = Vec2(y = 9.8f * 1000f)
            projectile.length = (64..128).random().toFloat()
            projectile.color = Vec4(0f, 0f, 0f, 1f)
            projectile.visible = true
        }
    }

    private fun destroyProjectile(i: Int) {
        val scene = this.scene ?: return
        scene.projectiles[i].visible = false
    }

}