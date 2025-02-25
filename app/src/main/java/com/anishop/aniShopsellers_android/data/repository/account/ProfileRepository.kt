package com.anishop.aniShopsellers_android.data.repository.account

import android.util.Log
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.account.SellerAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.SellerDetails
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.data.model.common.SimpleResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val apiService: ApiService
): ProfileRepositoryInterface {
    override suspend fun getSellerAccount(
        authToken: String
    ): Flow<DataState<SellerAccountResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getSellerAccount(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "$e")
            emit(DataState.Error(e))
        }
    }

    override suspend fun updateSellerAccount(authToken: String, sellerDetails: SellerDetails): Flow<DataState<SimpleResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.updateSellerAccount(authToken, sellerDetails)
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

    override suspend fun changePassword(
        authToken: String,
        oldPassword: String,
        newPassword: String
    ): Flow<DataState<ApiResponse>> = flow {
        val jsonBody = """
            {
                "oldPassword": "$oldPassword",
                "newPassword": "$newPassword"
            }
        """.trimIndent()

        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())
        emit(DataState.Loading)
        try {
            val response = apiService.changePassword(authToken, requestBody)
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

    override suspend fun postPickupAddress(
        authToken: String,
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
    ): Flow<DataState<ApiResponse>> = flow {
        val jsonBody = """
            {
                "name": "$name",
                "mobileNumber": "$mobileNumber",
                "pincode": "$pincode",
                "locality": "$locality",
                "address": "$address",
                "district": "$district",
                "city": "$city",
                "state": "$state",
                "country": "$country",
                "landmark": "$landmark"
            }
        """.trimIndent()
        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())
        emit(DataState.Loading)
        try {
            val response = apiService.postPickupAddress(authToken, requestBody)
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