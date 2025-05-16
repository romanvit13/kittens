package com.example.kittens.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kittens.domain.models.FavouriteCat
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon

@Composable
fun FavouriteCatsScreen(viewModel: FavouriteCatsViewModel = koinViewModel()) {
    val favCats by viewModel.favCats.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(6) { FakeCatItem() } // Show 6 shimmer items
        }
    } else {
        FavouriteCatsList(favCats, viewModel)
    }
}

@Composable
fun FavouriteCatsList(favouriteCats: List<FavouriteCat>, viewModel: FavouriteCatsViewModel) {
    LazyColumn {
        items(favouriteCats) { cat ->
            FavouriteCatItem(cat = cat, onRemoveClick = { catToRemove ->
                viewModel.removeFavouriteCat(catToRemove)
            })
        }
    }
}

@Composable
fun FavouriteCatItem(
    cat: FavouriteCat,
    onRemoveClick: (FavouriteCat) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

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
                        .data(cat.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Column(Modifier.padding(20.dp)) {
                    Text(text = "ID: ${cat.id}", style = MaterialTheme.typography.titleLarge)
                }
            }

            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Confirmation dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Remove Favourite") },
            text = { Text("Are you sure you want to remove this cat from your favourites?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onRemoveClick(cat)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}