package com.example.kittens.presentation.ui.compose

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittens.domain.models.Cat
import com.example.kittens.presentation.ui.CatsViewModel
import com.example.kittens.presentation.ui.UiStatus
import org.koin.androidx.compose.koinViewModel

@Composable
fun CatsScreen(viewModel: CatsViewModel = koinViewModel()) {
    val cats by viewModel.cats.collectAsState()
    val isInitialLoading by viewModel.isLoading.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()

    LazyColumn {
        when {
            isInitialLoading -> {
                items(6) {
                    FakeCatItem()
                }
            }

            else -> {
                itemsIndexed(cats) { index, cat ->
                    CatItem(cat = cat, onFavouriteClick = {
                        viewModel.toggleFavourite(cat)
                    })

                    if (index == cats.lastIndex - 3) {
                        viewModel.loadMoreCats()
                    }
                }

                if (isLoadingMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatItem(
    cat: Cat,
    onFavouriteClick: (Cat) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cat.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

//                Column(Modifier.padding(20.dp)) {
//                    Text(text = "ID: ${cat.id}", style = MaterialTheme.typography.titleLarge)
//                    Text(text = "Height: ${cat.height}", style = MaterialTheme.typography.bodyLarge)
//                    Text(text = "Width: ${cat.width}", style = MaterialTheme.typography.bodyLarge)
//                    if (!cat.breeds.isNullOrEmpty()) {
//                        val firstBreed = cat.breeds[0]
//                        Text(text = firstBreed.name, style = MaterialTheme.typography.bodyLarge)
//                        Text(text = firstBreed.origin, style = MaterialTheme.typography.bodyLarge)
//                        Text(
//                            text = firstBreed.temperament,
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                    }
//                }
            }

            // Heart Icon Button
            IconButton(
                onClick = { onFavouriteClick(cat) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = if (cat.isFavourite()) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (cat.isFavourite()) "Remove from favourites" else "Add to favourites",
                    tint = Color.Red,
                )
            }
        }
    }
}

@Composable
fun FakeCatItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    )
            ) {

            }
        }
    }
}

@Composable
fun ShimmerBox(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f, animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing), repeatMode = RepeatMode.Reverse
        ), label = "alpha"
    )

    Box(
        modifier = modifier
            .background(Color.LightGray.copy(alpha = alpha), RoundedCornerShape(8.dp))
    )
}