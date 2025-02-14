package com.anishop.aniShopsellers_android.data.dataSource.remote

import com.anishop.aniShopsellers_android.data.model.auth.OtpVerifyResponse
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.data.model.common.SimpleResponse
import com.anishop.aniShopsellers_android.data.model.home.DashboardResponse
import com.anishop.aniShopsellers_android.data.model.orders.AllOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.DispatchedOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.NewOrderResponse
import com.anishop.aniShopsellers_android.data.model.orders.PendingOrderResponse
import com.anishop.aniShopsellers_android.data.model.products.AllProductsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiService {

    // Auth Api
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

    // Home Screen Api
    @GET("seller/dashboard")
    suspend fun getOrdersData(
        @Header("Authorization") authToken: String
    ): DashboardResponse

    // Add Product Api
    @Multipart
    @POST("products/create")
    suspend fun addNewProduct(
        @Header("Authorization") authToken: String,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("basePrice") basePrice: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("discountPrice") discountPrice: RequestBody,
        @Part imageParts: List<MultipartBody.Part>,
        @Part("currency") currency: RequestBody,
        @Part vararg variants: MultipartBody.Part
    ): SimpleResponse

    // Get All Products
    @GET("seller/products/all")
    suspend fun getAllProducts(
        @Header("Authorization") authToken: String
    ): AllProductsResponse

    // Get All Orders
    @GET("seller/orders/all")
    suspend fun getAllOrders(
        @Header("Authorization") authToken: String
    ): AllOrdersResponse

    // Get All New Orders
    @GET("seller/orders/new-orders")
    suspend fun getAllNewOrders(
        @Header("Authorization") authToken: String
    ): NewOrderResponse

    // Get All Pending Orders
    @GET("seller/orders/pending-orders")
    suspend fun getAllPendingOrders(
        @Header("Authorization") authToken: String
    ): PendingOrderResponse

    // Get All Dispatched Orders
    @GET("seller/orders/shipping-orders")
    suspend fun getAllDispatchedOrders(
        @Header("Authorization") authToken: String
    ): DispatchedOrdersResponse
}