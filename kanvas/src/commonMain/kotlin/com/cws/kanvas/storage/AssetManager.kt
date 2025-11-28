package com.cws.kanvas.storage

import kotlinx.serialization.Serializable

interface Asset {
    fun serialize(byteArray: ByteArray, offset: Int): Int
    fun deserialize(byteArray: ByteArray, offset: Int): Int
}

@Serializable
data class AssetPackage(
    val id: String,
    val assetIds: List<String>
)

expect class AssetPackageClient {
    fun download(assetPackageId: String): AssetPackage
    fun upload(assetPackage: AssetPackage)
}

class AssetManager {

    private val clients = mutableMapOf<String, AssetPackageClient>()

    fun download(assetPackageIds: List<String>) {

    }

}