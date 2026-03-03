package com.cws.kanvas.assetc.json

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

val AppJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    serializersModule = SerializersModule {
        contextual(Dp::class, DpSerializer)
    }
}
