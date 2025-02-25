package com.anishop.aniShopsellers_android.data.repository.account

import android.util.Log
import androidx.collection.emptyIntSet
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.account.BankAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.BankDetails
import com.anishop.aniShopsellers_android.data.model.account.GetBankAccountResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class BankAccountRepository @Inject constructor(
    private val apiService: ApiService
): BankAccountRespositoryInterface {
    override suspend fun getBankAccount(
        authToken: String
    ): Flow<DataState<GetBankAccountResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getBankAccount(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                // This handles the error response (e.g., 400)
                val errorBody = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.e("APIResponse", "Error response: $errorBody")
                emit(DataState.Error(Exception(errorBody)))
            } else {
                Log.e("APIResponse", "Exception: ${e.message}")
                emit(DataState.Error(e))
            }
        }
    }

    override suspend fun addBankAccount(
        authToken: String,
        bankName: String,
        benificiaryName: String,
        accountNumber: String,
        ifscCode: String
    ): Flow<DataState<BankAccountResponse>> = flow {
        val jsonBody = """
            {
                "bankName": "$bankName",
                "benificiaryName": "$benificiaryName",
                "accountNo": "$accountNumber",
                "ifscCode": "$ifscCode"
            }
        """.trimIndent()

        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())
        emit(DataState.Loading)
        try {
            val response = apiService.addBankAccount(authToken, requestBody)
            emit(DataState.Success(response))
        } catch (e: Exception){
            if (e is HttpException) {
                // This handles the error response (e.g., 400)
                val errorBody = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.e("APIResponse", "Error response: $errorBody")
                emit(DataState.Error(Exception(errorBody)))
            } else {
                Log.e("APIResponse", "Exception: ${e.message}")
                emit(DataState.Error(e))
            }
        }
    }

    override suspend fun updateBankAccount(
        authToken: String,
        bankDetails: BankDetails
    ): Flow<DataState<ApiResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.updateBankAccount(authToken, bankDetails)
            emit(DataState.Success(response))
        } catch (e: Exception){
            if (e is HttpException) {
                // This handles the error response (e.g., 400)
                val errorBody = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.e("APIResponse", "Error response: $errorBody")
                emit(DataState.Error(Exception(errorBody)))
            } else {
                Log.e("APIResponse", "Exception: ${e.message}")
                emit(DataState.Error(e))
            }
        }
    }
}