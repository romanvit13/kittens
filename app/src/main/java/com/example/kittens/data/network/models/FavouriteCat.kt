package com.example.kittens.data.network.models

import com.google.gson.annotations.SerializedName

data class FavouriteCatRequest(
    @SerializedName("image_id")
    val imageId: String,

    @SerializedName("sub_id")
    val subId: String?
)

data class FavouriteCat(
    val id: Long,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("image_id")
    val imageId: String,
    @SerializedName("sub_id")
    val subId: String?,
    @SerializedName("created_at")
    val createdAt: String,
    val image: Image
)

data class Image(
    val id: String,
    val url: String
)