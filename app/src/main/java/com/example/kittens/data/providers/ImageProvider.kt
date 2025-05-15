package com.example.kittens.data.providers

import android.graphics.Bitmap

interface ImageProvider {
    suspend fun getBitmap(url: String): Bitmap?
}