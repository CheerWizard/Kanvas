package com.cws.kanvaslab.project

import com.cws.kanvas.core.Game

expect class GameReloader {
    fun reload(project: GameProject): Game?
}