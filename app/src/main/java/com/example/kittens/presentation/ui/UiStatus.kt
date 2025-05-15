package com.example.kittens.presentation.ui

sealed class UiStatus {
    object Idle : UiStatus()
    object Loading : UiStatus()
    data class Success(val message: String) : UiStatus()
    data class Error(val message: String) : UiStatus()
}