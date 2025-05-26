package com.example.kittens.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteCat (
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "userId")
    val userId: String?,
    @ColumnInfo(name = "imageId")
    val imageId: String,
    @ColumnInfo(name = "subId")
    val subId: String?,
    @ColumnInfo(name = "createdAt")
    val createdAt: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
)