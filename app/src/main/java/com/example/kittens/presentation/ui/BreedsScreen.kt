package com.example.kittens.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kittens.domain.models.Breed

@Composable
fun BreedsScreen(viewModel: BreedsViewModel = viewModel()) {
    val breeds by viewModel.breeds.observeAsState(initial = emptyList())
    viewModel.obtainBreeds() // Trigger data update

    BreedList(breeds) // Display the data
}

@Composable
fun BreedList(breeds: List<Breed>) {
    LazyColumn {
        items(breeds) { breed ->
            BreedItem(breed)
        }
    }
}

@Composable
fun BreedItem(breed: Breed) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = breed.name, style = MaterialTheme.typography.titleMedium)
        Text(text = breed.temperament, style = MaterialTheme.typography.bodyMedium)
        Text(text = breed.origin, style = MaterialTheme.typography.bodyMedium)
    }
}