package com.example.kittens.presentation.ui

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kittens.domain.models.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CatsScreen(viewModel: CatsViewModel = viewModel()) {
    val cats by viewModel.cats.observeAsState(initial = emptyList())

    CatsList(cats)
}

@Composable
fun CatsList(cats: List<Cat>) {
    LazyColumn {
        items(cats) { cat ->
            CatItem(cat)
        }
    }
}

@Composable
fun CatItem(cat: Cat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            GlideImageLoader(cat.url)
            Text(text = cat.id, style = MaterialTheme.typography.titleMedium)
            Text(text = cat.height.toString(), style = MaterialTheme.typography.bodyMedium)
            Text(text = cat.width.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun GlideImageLoader(imageUrl: String) {
    val context = LocalContext.current
    val imageBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    // Load images using Glide
    LaunchedEffect(imageUrl) {
        val glideOptions = RequestOptions()
        val bitmap = withContext(Dispatchers.IO) {
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .apply(glideOptions.centerCrop())
                .submit()
                .get()
        }
        imageBitmap.value = bitmap
    }

    // Display the image
    imageBitmap.value?.let { bitmap ->
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}