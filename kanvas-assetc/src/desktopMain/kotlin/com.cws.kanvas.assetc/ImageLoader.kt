package com.cws.kanvas.assetc

import com.cws.print.Print
import com.cws.std.memory.NativeBuffer
import org.lwjgl.stb.STBImage.*
import org.lwjgl.stb.STBImageWrite.*
import org.lwjgl.stb.STBImageResize.*

enum class PixelFormat(val channels: Int, val bytesPerPixel: Int) {
    FORMAT_R8(1, 1),
    FORMAT_RG8(2, 1),
    FORMAT_RGB8(3, 1),
    FORMAT_RGBA8(4, 1),

    FORMAT_R16(1, 2),
    FORMAT_RG16(2, 2),
    FORMAT_RGB16(3, 2),
    FORMAT_RGBA16(4, 2),

    FORMAT_R32(1, 4),
    FORMAT_RG32(2, 4),
    FORMAT_RGB32(3, 4),
    FORMAT_RGBA32(4, 4),

    FORMAT_DEPTH16(1, 2),
    FORMAT_DEPTH24(1, 3),
    FORMAT_DEPTH32(1, 4);
}

enum class ImageFormat {
    PNG,
    JPG,
    TGA,
    BMP,
    HDR
}

enum class ImageEdge(val value: Int) {
    CLAMP(STBIR_EDGE_CLAMP),
    REFLECT(STBIR_EDGE_REFLECT),
    WRAP(STBIR_EDGE_WRAP),
    ZERO(STBIR_EDGE_ZERO)
}

enum class ImageFilter(val value: Int) {
    DEFAULT(STBIR_FILTER_DEFAULT),
    BOX(STBIR_FILTER_BOX),
    TRIANGLE(STBIR_FILTER_TRIANGLE),
    CUBICSPLINE(STBIR_FILTER_CUBICBSPLINE),
    CATMULLROM(STBIR_FILTER_CATMULLROM),
    MITCHELL(STBIR_FILTER_MITCHELL),
    POINT_SAMPLE(STBIR_FILTER_POINT_SAMPLE),
    OTHER(STBIR_FILTER_OTHER)
}

data class Image(
    val width: Int,
    val height: Int,
    val pixelFormat: PixelFormat,
    val buffer: NativeBuffer,
)

class ImageLoader {

    companion object {
        private const val TAG = "ImageLoader"
        private const val IMAGE_SAVE_QUALITY = 100
    }

    fun load(filepath: String, pixelFormat: PixelFormat, flip: Boolean = false): Image? {
        val xPtr = IntArray(1)
        val yPtr = IntArray(1)
        val channels = IntArray(1)
        stbi_set_flip_vertically_on_load(flip)

        val buffer = when (pixelFormat) {
            PixelFormat.FORMAT_R8,
            PixelFormat.FORMAT_RG8,
            PixelFormat.FORMAT_RGB8,
            PixelFormat.FORMAT_RGBA8 -> {
                val buffer = stbi_load(filepath, xPtr, yPtr, channels, 3)
                if (buffer == null) {
                    Print.e(TAG, "Failed to load image from $filepath with x=${xPtr[0]}, y=${yPtr[0]}, format=$pixelFormat")
                    null
                } else {
                    val nativeBuffer = NativeBuffer(buffer.capacity())
                    nativeBuffer.buffer.put(buffer)
                    stbi_image_free(buffer)
                    nativeBuffer
                }
            }

            PixelFormat.FORMAT_R16,
            PixelFormat.FORMAT_RG16,
            PixelFormat.FORMAT_RGB16,
            PixelFormat.FORMAT_RGBA16,
            PixelFormat.FORMAT_DEPTH16 -> {
                val buffer = stbi_load_16(filepath, xPtr, yPtr, channels, pixelFormat.channels)
                if (buffer == null) {
                    Print.e(TAG, "Failed to load image from $filepath with x=${xPtr[0]}, y=${yPtr[0]}, format=$pixelFormat")
                    null
                } else {
                    val nativeBuffer = NativeBuffer(buffer.capacity() * Short.SIZE_BYTES)
                    nativeBuffer.buffer.asShortBuffer().put(buffer)
                    stbi_image_free(buffer)
                    nativeBuffer
                }
            }

            PixelFormat.FORMAT_R32,
            PixelFormat.FORMAT_RG32,
            PixelFormat.FORMAT_RGB32,
            PixelFormat.FORMAT_RGBA32,
            PixelFormat.FORMAT_DEPTH32 -> {
                val buffer = stbi_loadf(filepath, xPtr, yPtr, channels, pixelFormat.channels)
                if (buffer == null) {
                    Print.e(TAG, "Failed to load image from $filepath with x=${xPtr[0]}, y=${yPtr[0]}, format=$pixelFormat")
                    null
                } else {
                    val nativeBuffer = NativeBuffer(buffer.capacity() * Float.SIZE_BYTES)
                    nativeBuffer.buffer.asFloatBuffer().put(buffer)
                    stbi_image_free(buffer)
                    nativeBuffer
                }
            }

            else -> {
                Print.e(TAG, "ImageFormat = $pixelFormat is not supported for loading!")
                null
            }
        }

        if (buffer == null) {
            Print.e(TAG, "Image buffer is NULL!")
            return null
        }

        return Image(
            width = xPtr[0],
            height = yPtr[0],
            pixelFormat = pixelFormat,
            buffer = buffer,
        )
    }

    fun save(
        filename: String,
        image: Image,
        imageFormat: ImageFormat,
    ): Boolean {
        val width = image.width
        val height = image.height
        val pixelFormat = image.pixelFormat
        val data = image.buffer
        val stride = width * pixelFormat.channels * pixelFormat.bytesPerPixel

        val result = when (imageFormat) {
            ImageFormat.BMP -> {
                stbi_write_bmp(filename, width, height, pixelFormat.channels, data.buffer)
            }
            ImageFormat.PNG -> {
                stbi_write_png(filename, width, height, pixelFormat.channels, data.buffer, stride)
            }
            ImageFormat.JPG -> {
                stbi_write_jpg(filename, width, height, pixelFormat.channels, data.buffer, IMAGE_SAVE_QUALITY)
            }
            ImageFormat.TGA -> {
                stbi_write_tga(filename, width, height, pixelFormat.channels, data.buffer)
            }
            ImageFormat.HDR -> {
                stbi_write_hdr(filename, width, height, pixelFormat.channels, data.buffer.asFloatBuffer())
            }
        }

        if (!result) {
            Print.e(TAG, "Failed to save image into $filename as imageFormat=$imageFormat, pixelFormat=$pixelFormat, width=$width, height=$height")
        }

        return result
    }

    fun resize(
        srcImage: Image,
        dstImage: Image,
        edge: ImageEdge = ImageEdge.CLAMP, filter: ImageFilter = ImageFilter.DEFAULT,
    ): Boolean {
        val srcFormat = srcImage.pixelFormat
        val srcWidth = srcImage.width
        val srcHeight = srcImage.height
        val srcData = srcImage.buffer

        val dstFormat = dstImage.pixelFormat
        val dstWidth = dstImage.width
        val dstHeight = dstImage.height
        val dstData = dstImage.buffer

        if (srcFormat != dstFormat) {
            Print.e(TAG, "Failed to resize image, because $srcFormat and $dstFormat are not the same!")
            return false
        }

        val srcStride = srcWidth * srcFormat.channels * srcFormat.bytesPerPixel
        val dstStride = dstWidth * dstFormat.channels * dstFormat.bytesPerPixel

        val dataType = when (srcFormat) {
            PixelFormat.FORMAT_R8,
            PixelFormat.FORMAT_RG8,
            PixelFormat.FORMAT_RGB8,
            PixelFormat.FORMAT_RGBA8 -> STBIR_TYPE_UINT8

            PixelFormat.FORMAT_R16,
            PixelFormat.FORMAT_RG16,
            PixelFormat.FORMAT_RGB16,
            PixelFormat.FORMAT_RGBA16,
            PixelFormat.FORMAT_DEPTH16 -> STBIR_TYPE_UINT16

            PixelFormat.FORMAT_R32,
            PixelFormat.FORMAT_RG32,
            PixelFormat.FORMAT_RGB32,
            PixelFormat.FORMAT_RGBA32,
            PixelFormat.FORMAT_DEPTH32 -> STBIR_TYPE_FLOAT

            else -> {
                Print.e(TAG, "Unsupported image pixel format $srcFormat for image resizing!")
                return false
            }
        }

        val buffer = stbir_resize(
            srcData.buffer, srcWidth, srcHeight, srcStride,
            dstData.buffer, dstWidth, dstHeight, dstStride,
            srcFormat.channels, dataType, edge.value, filter.value,
        )

        if (buffer == null) {
            Print.e(TAG, "Failed to resize image from ($srcWidth, $srcHeight) into ($dstWidth, $dstHeight)")
            return false
        }

        return true
    }

}
