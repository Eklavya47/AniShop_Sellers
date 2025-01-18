package com.anishop.aniShopsellers_android.utils.network

sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class onSuccess(val message: String) : UiState()
    data class onFailure(val message: String) : UiState()
}