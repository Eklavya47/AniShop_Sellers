package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anishop.aniShopsellers_android.data.model.account.AccountAdded
import com.anishop.aniShopsellers_android.data.model.account.BankDetails
import com.anishop.aniShopsellers_android.data.model.account.Seller
import com.anishop.aniShopsellers_android.data.repository.account.BankAccountRepository
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
class BankAccountViewModel @Inject constructor(
    private val repository: BankAccountRepository,
    private val secureStorage: SharedPreferences
): ViewModel() {

    private val _uiStateGetBankAccount = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateGetBankAccount get() = _uiStateGetBankAccount.asStateFlow()

    private val _uiStateUpdateBankAccount = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateUpdateBankAccount get() = _uiStateUpdateBankAccount.asStateFlow()

    private val _uiStateAddBankAccount = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateAddBankAccount get() = _uiStateAddBankAccount.asStateFlow()

    private val _bankAccount = MutableStateFlow<AccountAdded?>(null)
    val bankAccount get() = _bankAccount.asStateFlow()

    fun getBankAccount() {
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null) {
                repository.getBankAccount(authToken).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateGetBankAccount.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateGetBankAccount.value = UiState.onSuccess(it.data.status)
                            _bankAccount.value = it.data.bankAccount

                        }
                        is DataState.Error -> {
                            _uiStateGetBankAccount.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiStateGetBankAccount.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    fun addBankAccount(
        bankName: String,
        benificiaryName: String,
        accountNumber: String,
        ifscCode: String
    ){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.addBankAccount(authToken, bankName, benificiaryName, accountNumber, ifscCode).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateAddBankAccount.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateAddBankAccount.value = UiState.onSuccess(it.data.status)

                        }
                        is DataState.Error -> {
                            _uiStateAddBankAccount.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiStateAddBankAccount.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    fun updateBankAccount(
        bankDetails: BankDetails
    ){
        viewModelScope.launch {
            val authToken = getAuthKey()
            if (authToken != null){
                repository.updateBankAccount(authToken, bankDetails).onEach {
                    when(it){
                        is DataState.Loading -> {
                            _uiStateUpdateBankAccount.value = UiState.Loading
                        }
                        is DataState.Success -> {
                            _uiStateUpdateBankAccount.value = UiState.onSuccess(it.data.status)

                        }
                        is DataState.Error -> {
                            _uiStateUpdateBankAccount.value = UiState.onFailure(it.exception.message.toString())
                        }
                    }
                }.launchIn(viewModelScope)
            } else {
                _uiStateUpdateBankAccount.value = UiState.onFailure("Auth token is missing")
            }
        }
    }

    private fun getAuthKey(): String? {
        return secureStorage.getString("auth_key", null)
    }

    fun resetState() {
        _uiStateGetBankAccount.value = UiState.Idle
        _uiStateUpdateBankAccount.value = UiState.Idle
        _uiStateAddBankAccount.value = UiState.Idle
    }
}