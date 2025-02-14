package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel

import android.content.SharedPreferences
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.anishop.aniShopsellers_android.data.model.home.GraphEntry
import com.anishop.aniShopsellers_android.data.model.home.OrderSummary
import com.anishop.aniShopsellers_android.data.repository.home.HomeRepository
import com.anishop.aniShopsellers_android.utils.network.DataState
import com.anishop.aniShopsellers_android.utils.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @OptIn(UnstableApi::class)
@Inject constructor(
    private val repository: HomeRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    private val _dashboardData = MutableStateFlow<List<GraphEntry>>(emptyList())
    val dashboardData: StateFlow<List<GraphEntry>> get() = _dashboardData.asStateFlow()

    private val _orderSummaryList = MutableStateFlow<OrderSummary?>(null)
    val orderSummaryList: StateFlow<OrderSummary?> get() = _orderSummaryList.asStateFlow()

    init {
        fetchOrdersData()
    }

    @OptIn(UnstableApi::class)
    private fun fetchOrdersData() {
        viewModelScope.launch {
            val authToken = getAuthKey()
            Log.d("APIResponse", "$authToken")
            if (authToken != null) {
                Log.d("APIResponse", "viewModel is called")
                repository.fetchOrdersData(authToken).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiState.value = UiState.onSuccess(it.data.status)
                            _orderSummaryList.value = it.data.allTypes
                            _dashboardData.value = it.data.graphData
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