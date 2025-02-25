package com.anishop.aniShopsellers_android.data.repository.account

import com.anishop.aniShopsellers_android.data.model.account.BankAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.BankDetails
import com.anishop.aniShopsellers_android.data.model.account.GetBankAccountResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface BankAccountRespositoryInterface {
    suspend fun getBankAccount(
        authToken: String
    ): Flow<DataState<GetBankAccountResponse>>

    suspend fun addBankAccount(
        authToken: String,
        bankName: String,
        benificiaryName: String,
        accountNumber: String,
        ifscCode: String,
    ): Flow<DataState<BankAccountResponse>>

    suspend fun updateBankAccount(
        authToken: String,
        bankDetails: BankDetails
    ): Flow<DataState<ApiResponse>>
}