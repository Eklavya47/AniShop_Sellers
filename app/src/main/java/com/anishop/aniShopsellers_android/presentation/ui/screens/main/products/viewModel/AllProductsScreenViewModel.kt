package com.anishop.aniShopsellers_android.presentation.ui.screens.main.products.viewModel

import android.content.SharedPreferences
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.anishop.aniShopsellers_android.data.model.products.Product
import com.anishop.aniShopsellers_android.data.repository.products.AllProductsRepository
import com.anishop.aniShopsellers_android.utils.network.DataState
import com.anishop.aniShopsellers_android.utils.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductsScreenViewModel @Inject constructor(
    private val repository: AllProductsRepository,
    private val secureStorage: SharedPreferences
): ViewModel()  {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts get() = _allProducts.asStateFlow()

    init {
        getAllProducts()
    }

    @OptIn(UnstableApi::class)
    private fun getAllProducts(){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.getAllProducts(authToken).onEach{
                    when(it){
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _allProducts.value = it.data.products
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else{
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }
}