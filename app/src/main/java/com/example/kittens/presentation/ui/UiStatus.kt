package com.example.kittens.presentation.ui

sealed class UiStatus {
    data object Idle : UiStatus()
    data object Loading : UiStatus()
    data class Success(val message: String) : UiStatus()
    data class Error(val message: String) : UiStatus()
}