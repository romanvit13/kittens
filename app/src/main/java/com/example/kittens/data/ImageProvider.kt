package com.example.kittens.data

import android.graphics.Bitmap

interface ImageProvider {
    suspend fun getBitmap(url: String): Bitmap?
}