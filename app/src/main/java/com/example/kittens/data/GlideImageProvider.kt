package com.example.kittens.data

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GlideImageProvider(private val context: Context) : ImageProvider {
    private val memoryCache = object : LruCache<String, Bitmap>(calculateMemoryCacheSize()) {}

    override suspend fun getBitmap(url: String): Bitmap? {
        memoryCache.get(url)?.let { return it }

        return withContext(Dispatchers.IO) {
            try {
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .submit()
                    .get()

                memoryCache.put(url, bitmap)
                bitmap
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun calculateMemoryCacheSize(): Int {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        return maxMemory / 8
    }
}
