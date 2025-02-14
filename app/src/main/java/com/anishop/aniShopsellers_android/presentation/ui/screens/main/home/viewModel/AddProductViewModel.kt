package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anishop.aniShopsellers_android.data.repository.home.AddProductRepository
import com.anishop.aniShopsellers_android.utils.network.DataState
import com.anishop.aniShopsellers_android.utils.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: AddProductRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNewProduct(
        productName: String,
        productDescription: String,
        basePrice: String,
        categoryId: String,
        discountPrice: String,
        currency: String,
        selectedSizes: List<String>,
        quantities: List<String>,
        images: List<File>
    ){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.addNewProduct(
                    authToken,
                    productName,
                    productDescription,
                    basePrice,
                    categoryId,
                    discountPrice,
                    currency,
                    selectedSizes,
                    quantities,
                    images
                ).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiState.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}