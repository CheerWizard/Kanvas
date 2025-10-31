package com.cws.acatch.data

import com.cws.acatch.collision.CollisionBox

class GameScene(
    val screenBox: CollisionBox,
    val grid: GameGrid,
    val balls: BallList,
    val projectiles: ProjectileList
)