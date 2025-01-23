package com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anishop.aniShopsellers_android.data.repository.auth.AuthRepository
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
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val secureStorage: SharedPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    private fun saveAuthKey(authKey: String) {
        secureStorage.edit().putString("auth_key", authKey).apply()
    }

    private fun saveLoginStatus() {
        secureStorage.edit().putBoolean("isLoggedIn", true).apply()
    }

    fun logoutUser() {
        secureStorage.edit().putBoolean("isLoggedIn", false).apply()
    }

    fun isLoggedIn(): Boolean {
        return secureStorage.getBoolean("isLoggedIn", false)
    }

    fun loginEmail(userEmail: String, userPassword: String) {
        viewModelScope.launch {
            repository.loginEmail(userEmail, userPassword).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiState.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiState.value = UiState.onFailure(it.exception.message.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun loginOtpVerify(userEmail: String, otp: String) {
        viewModelScope.launch {
            repository.loginOtpVerify(userEmail, otp).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiState.value = UiState.onSuccess(it.data.message)
                        saveAuthKey(it.data.token)
                        saveLoginStatus()
                    }
                    is DataState.Error -> {
                        _uiState.value = UiState.onFailure(it.exception.message.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun forgetPassword(userEmail: String) {
        viewModelScope.launch {
            repository.forgetPassword(userEmail).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiState.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiState.value = UiState.onFailure(it.exception.message.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetPassword(userEmail: String, otp: String, newPassword: String) {
        viewModelScope.launch {
            repository.resetPassword(userEmail, otp, newPassword).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiState.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiState.value = UiState.onFailure(it.exception.message.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}