package com.cws.kanvas.assetc.filebrowser

import androidx.compose.ui.graphics.vector.ImageVector
import com.cws.kanvas.assetc.ui.icons.IconDir
import com.cws.kanvas.assetc.ui.icons.IconImage
import com.cws.kanvas.assetc.ui.icons.IconJson
import com.cws.kanvas.assetc.ui.icons.IconSound
import java.io.File

enum class FileType(
    val icon: ImageVector,
    val extension: String,
) {
    DIR(IconDir, ""),
    PNG(IconImage, "png"),
    JPEG(IconImage, "jpeg"),
    JPG(IconImage, "jpg"),
    SVG(IconImage, "svg"),
    BMP(IconImage, "bmp"),
    MP3(IconSound, "mp3"),
    OGG(IconSound, "ogg"),
    WAV(IconSound, "wav"),
    JSON(IconJson, "json"),
    IMAGE_ASSET(IconImage, "asset"),
    SOUND_ASSET(IconSound, "asset"),
}

fun File.toFileType(): FileType? {
    if (isDirectory) return FileType.DIR
    return FileType.entries.find { it.extension == extension }
}
