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

    private val _uiStateLogin = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateLogin get() = _uiStateLogin.asStateFlow()

    private val _uiStateLoginOtpVerify = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateLoginOtpVerify get() = _uiStateLoginOtpVerify.asStateFlow()

    private val _uiStateForgetPassword = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateForgetPassword get() = _uiStateForgetPassword.asStateFlow()

    private val _uiStateResetPassword = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateResetPassword get() = _uiStateResetPassword.asStateFlow()

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
                        _uiStateLogin.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiStateLogin.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiStateLogin.value = UiState.onFailure(it.exception.message.toString())
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
                        _uiStateLoginOtpVerify.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiStateLoginOtpVerify.value = UiState.onSuccess(it.data.message)
                        saveAuthKey(it.data.token)
                        saveLoginStatus()
                    }
                    is DataState.Error -> {
                        _uiStateLoginOtpVerify.value = UiState.onFailure(it.exception.message.toString())
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
                        _uiStateForgetPassword.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiStateForgetPassword.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiStateForgetPassword.value = UiState.onFailure(it.exception.message.toString())
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
                        _uiStateResetPassword.value = UiState.Loading
                    }
                    is DataState.Success -> {
                        _uiStateResetPassword.value = UiState.onSuccess(it.data.message)
                    }
                    is DataState.Error -> {
                        _uiStateResetPassword.value = UiState.onFailure(it.exception.message.toString())
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetState() {
        _uiStateLogin.value = UiState.Idle
        _uiStateLoginOtpVerify.value = UiState.Idle
        _uiStateForgetPassword.value = UiState.Idle
        _uiStateResetPassword.value = UiState.Idle
    }
}