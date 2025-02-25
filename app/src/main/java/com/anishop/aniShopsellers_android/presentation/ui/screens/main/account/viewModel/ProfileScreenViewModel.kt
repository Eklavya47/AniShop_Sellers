package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anishop.aniShopsellers_android.data.model.account.Seller
import com.anishop.aniShopsellers_android.data.model.account.SellerDetails
import com.anishop.aniShopsellers_android.data.repository.account.ProfileRepository
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
class ProfileScreenViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {
    private val _uiStateGetSellerAccount = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateGetSellerAccount get() = _uiStateGetSellerAccount.asStateFlow()

    private val _uiStateUpdateSellerAccount = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateUpdateSellerAccount get() = _uiStateUpdateSellerAccount.asStateFlow()

    private val _uiStateChangePassword = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateChangePassword get() = _uiStateChangePassword.asStateFlow()

    private val _uiStatePostPickupAddress = MutableStateFlow<UiState>(UiState.Idle)
    val uiStatePostPickupAddress get() = _uiStatePostPickupAddress.asStateFlow()

    private val _sellerAccount = MutableStateFlow<Seller?>(null)
    val sellerAccount get() = _sellerAccount.asStateFlow()

    fun getSellerAccount(
    ) {
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getSellerAccount(
                    authToken
                ).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateGetSellerAccount.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateGetSellerAccount.value = UiState.onSuccess(it.data.status)
                            _sellerAccount.value = it.data.seller

                        }
                        is DataState.Error -> {
                            _uiStateGetSellerAccount.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else{
                _uiStateGetSellerAccount.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    fun updateSellerAccount(
        sellerDetails: SellerDetails
    ) {
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.updateSellerAccount(authToken, sellerDetails).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiStateUpdateSellerAccount.value = UiState.Loading
                        }

                        is DataState.Success -> {
                            _uiStateUpdateSellerAccount.value = UiState.onSuccess(it.data.status)

                        }
                        is DataState.Error -> {
                            _uiStateUpdateSellerAccount.value =
                                UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else{
                _uiStateGetSellerAccount.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    fun changePassword(
        oldPassword: String,
        newPassword: String
    ){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.changePassword(authToken, oldPassword, newPassword).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiStateChangePassword.value = UiState.Loading
                        }

                        is DataState.Success -> {
                            _uiStateChangePassword.value = UiState.onSuccess(it.data.status)
                        }
                        is DataState.Error -> {
                            _uiStateChangePassword.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiStateChangePassword.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    fun postPickupAddress(
        name: String,
        mobileNumber: String,
        pincode: String,
        locality: String,
        address: String,
        district: String,
        city: String,
        state: String,
        country: String,
        landmark: String
    ){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.postPickupAddress(authToken, name, mobileNumber, pincode, locality, address, district, city, state, country, landmark).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            _uiStatePostPickupAddress.value = UiState.Loading
                        }

                        is DataState.Success -> {
                            _uiStatePostPickupAddress.value = UiState.onSuccess(it.data.status)
                        }
                        is DataState.Error -> {
                            _uiStatePostPickupAddress.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiStatePostPickupAddress.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }

    fun resetState() {
        _uiStateGetSellerAccount.value = UiState.Idle
        _uiStateUpdateSellerAccount.value = UiState.Idle
        _uiStateChangePassword.value = UiState.Idle
        _uiStatePostPickupAddress.value = UiState.Idle
    }
}