package com.anishop.aniShopsellers_android.data.repository.auth

import com.anishop.aniShopsellers_android.data.model.auth.OtpVerifyResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepositoryInterface {
    // Login
    suspend fun loginEmail(
        userEmail: String,
        userPassword: String,
    ): Flow<DataState<ApiResponse>>

    //Login Email
    suspend fun loginOtpVerify(
        userEmail: String,
        otp: String
    ): Flow<DataState<OtpVerifyResponse>>

    //ForgetPassword
    suspend fun forgetPassword(
        userEmail: String,
    ): Flow<DataState<ApiResponse>>

    //ResetPassword
    suspend fun resetPassword(
        userEmail: String,
        otp: String,
        newPassword: String,
    ): Flow<DataState<ApiResponse>>
}