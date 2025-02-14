package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anishop.aniShopsellers_android.data.model.orders.AllOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.DispatchedOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.NewOrderResponse
import com.anishop.aniShopsellers_android.data.model.orders.PendingOrderResponse
import com.anishop.aniShopsellers_android.data.repository.orders.OrdersRepository
import com.anishop.aniShopsellers_android.utils.network.DataState
import com.anishop.aniShopsellers_android.utils.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val repository: OrdersRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    private val _allOrders = MutableStateFlow<AllOrdersResponse?>(null)
    val allOrders get() = _allOrders.asStateFlow()

    private val _allNewOrders = MutableStateFlow<NewOrderResponse?>(null)
    val allNewOrders get() = _allNewOrders.asStateFlow()

    private val _allPendingOrders = MutableStateFlow<PendingOrderResponse?>(null)
    val allPendingOrders get() = _allPendingOrders.asStateFlow()

    private val _allDispatchedOrders = MutableStateFlow<DispatchedOrdersResponse?>(null)
    val allDispatchedOrders get() = _allDispatchedOrders.asStateFlow()

    fun getAllOrders(){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getAllOrders(authToken).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _allOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    fun getAllNewOrders(){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getAllNewOrders(authToken).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _allNewOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    fun getAllPendingOrders(){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getAllPendingOrders(authToken).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _allPendingOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    fun getAllDispatchedOrders(){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getAllDispatchedOrders(authToken).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _allDispatchedOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }
}