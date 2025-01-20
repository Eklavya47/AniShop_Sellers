package com.anishop.aniShopsellers_android.data.dataSource.remote

import com.anishop.aniShopsellers_android.data.model.auth.OtpVerifyResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @POST("seller/auth/login")
    @Headers("Content-Type: application/json")
    suspend fun loginEmail(
        @Body body: RequestBody
    ): ApiResponse

    @POST("seller/auth/login-verify-otp")
    @Headers("Content-Type: application/json")
    suspend fun loginOtpVerify(
        @Body body: RequestBody
    ): OtpVerifyResponse

    @POST("seller/auth/forgot-password")
    @Headers("Content-Type: application/json")
    suspend fun forgetPassword(
        @Body body: RequestBody
    ): ApiResponse

    @POST("seller/auth/reset-password")
    @Headers("Content-Type: application/json")
    suspend fun resetPassword(
        @Body body: RequestBody
    ): ApiResponse
}