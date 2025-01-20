package com.anishop.aniShopsellers_android.data.repository.auth

import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.auth.OtpVerifyResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
): AuthRepositoryInterface {
    override suspend fun loginEmail(
        userEmail: String,
        userPassword: String
    ): Flow<DataState<ApiResponse>> = flow {
        val jsonBody = """
            {
                "email": "$userEmail",
                "password": "$userPassword"
            }
        """.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
        emit(DataState.Loading)

        try {
            val response = apiService.loginEmail(requestBody)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun loginOtpVerify(
        userEmail: String,
        otp: String
    ): Flow<DataState<OtpVerifyResponse>> = flow {
        val jsonBody = """
            {
                "email": "$userEmail",
                "otp": "$otp"
            }
        """.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
        emit(DataState.Loading)

        try {
            val response = apiService.loginOtpVerify(requestBody)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun forgetPassword(userEmail: String): Flow<DataState<ApiResponse>> = flow {
        val jsonBody = """
            {
                "email": "$userEmail"
            }
        """.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
        emit(DataState.Loading)

        try {
            val response = apiService.forgetPassword(requestBody)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun resetPassword(
        userEmail: String,
        otp: String,
        newPassword: String
    ): Flow<DataState<ApiResponse>> = flow {
        val jsonBody = """
            {
                "email": "$userEmail",
                "otp": "$otp",
                "new_password": "$newPassword"
            }
        """.trimIndent()

        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaType(), jsonBody)
        emit(DataState.Loading)

        try {
            val response = apiService.resetPassword(requestBody)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}