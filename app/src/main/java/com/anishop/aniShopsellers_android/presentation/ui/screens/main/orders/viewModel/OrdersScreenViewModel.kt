package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.viewModel

import android.content.SharedPreferences
import android.health.connect.datatypes.units.Length
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val repository: OrdersRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {
    private val _uiStateAllOrders = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateAllOrders get() = _uiStateAllOrders.asStateFlow()

    private val _uiStateNewOrders = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateNewOrders get() = _uiStateNewOrders.asStateFlow()

    private val _uiStatePendingOrders = MutableStateFlow<UiState>(UiState.Idle)
    val uiStatePendingOrder get() = _uiStatePendingOrders.asStateFlow()

    private val _uiStateGetDispatchedOrders = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateGetDispatchedOrders get() = _uiStateGetDispatchedOrders.asStateFlow()

    private val _uiStateConfirmNewOrder = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateConfirmNewOrder get() = _uiStateConfirmNewOrder.asStateFlow()

    private val _uiStateCancelNewOrder = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateCancelNewOrder get() = _uiStateCancelNewOrder.asStateFlow()

    private val _uiStateDispatchOrder = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateDispatchOrder get() = _uiStateDispatchOrder.asStateFlow()

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
                            _uiStateAllOrders.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateAllOrders.value = UiState.onSuccess(it.data.status)
                            _allOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiStateAllOrders.value = UiState.onFailure(it.exception.message.toString())
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
                            _uiStateNewOrders.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateNewOrders.value = UiState.onSuccess(it.data.status)
                            _allNewOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiStateNewOrders.value = UiState.onFailure(it.exception.message.toString())
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
                            _uiStatePendingOrders.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStatePendingOrders.value = UiState.onSuccess(it.data.status)
                            _allPendingOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiStatePendingOrders.value = UiState.onFailure(it.exception.message.toString())
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
                            _uiStateGetDispatchedOrders.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateGetDispatchedOrders.value = UiState.onSuccess(it.data.status)
                            _allDispatchedOrders.value = it.data
                        }
                        is DataState.Error -> {
                            _uiStateGetDispatchedOrders.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                Log.e("DashboardViewModel", "Auth token is missing")
            }
        }
    }

    fun confirmOrder(id: Int){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.confirmOrder(authToken, id).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateConfirmNewOrder.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateConfirmNewOrder.value = UiState.onSuccess(it.data.message)
                        }
                        is DataState.Error -> {
                            _uiStateConfirmNewOrder.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun cancelOrder(id: Int){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.cancelOrder(authToken, id).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateCancelNewOrder.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateCancelNewOrder.value = UiState.onSuccess(it.data.message)
                        }
                        is DataState.Error -> {
                            _uiStateCancelNewOrder.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    fun dispatchOrder(id: Int, length: String, breadth: String, height: String, weight: String){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.dispatchOrder(authToken, id, length, breadth, height, weight).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateDispatchOrder.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateDispatchOrder.value = UiState.onSuccess(it.data.message)
                        }
                        is DataState.Error -> {
                            _uiStateDispatchOrder.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }

    fun resetState() {
        _uiStateAllOrders.value = UiState.Idle
        _uiStateNewOrders.value = UiState.Idle
        _uiStatePendingOrders.value = UiState.Idle
        _uiStateGetDispatchedOrders.value = UiState.Idle
        _uiStateConfirmNewOrder.value = UiState.Idle
        _uiStateCancelNewOrder.value = UiState.Idle
        _uiStateDispatchOrder.value = UiState.Idle
    }
}