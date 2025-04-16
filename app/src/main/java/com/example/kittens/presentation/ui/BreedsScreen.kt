package com.example.kittens.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BreedsScreen(viewModel: BreedsViewModel = viewModel()) {
    val breeds by viewModel.breeds.observeAsState(initial = emptyList())
    viewModel.obtainBreeds() // Initial obtaining

    Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            text = breeds.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = { /* Handle save to DB logic */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save to DB")
        }
        Button(
            onClick = { viewModel.obtainBreeds() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Load from DB")
        }
    }
}