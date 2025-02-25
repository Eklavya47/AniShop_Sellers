package com.anishop.aniShopsellers_android.data.dataSource.remote

import com.anishop.aniShopsellers_android.data.model.account.BankAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.BankDetails
import com.anishop.aniShopsellers_android.data.model.account.GetBankAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.SellerAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.SellerDetails
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
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

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
        @Part images: List<MultipartBody.Part>,
        @Part("variants[0][size]") size1: RequestBody,
        @Part("variants[0][stockQuantity]") quantity1: RequestBody,
        @Part("variants[1][size]") size2: RequestBody,
        @Part("variants[1][stockQuantity]") quantity2: RequestBody,
        @Part("variants[2][size]") size3: RequestBody,
        @Part("variants[2][stockQuantity]") quantity3: RequestBody,
        @Part("variants[3][size]") size4: RequestBody,
        @Part("variants[3][stockQuantity]") quantity4: RequestBody,
        @Part("currency") currency: RequestBody
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

    // Confirm New Order
    @PATCH("seller/orders/new-orders/{id}/confirmed")
    suspend fun confirmOrder(
        @Header("Authorization") authToken: String,
        @Path("id") orderId: Int
    ): ApiResponse

    // Confirm New Order
    @PATCH("seller/orders/new-orders/{id}/cancelled")
    suspend fun cancelOrder(
        @Header("Authorization") authToken: String,
        @Path("id") orderId: Int
    ): ApiResponse

    // Dispatch Pending Order
    @PUT("seller/orders/pending-orders/generate-order/{id}")
    @Headers("Content-Type: application/json")
    suspend fun dispatchOrder(
        @Header("Authorization") authToken: String,
        @Path("id") orderId: Int,
        @Body body: RequestBody
    ): ApiResponse

    // Get Seller Account
    @GET("seller/account")
    suspend fun getSellerAccount(
        @Header("Authorization") authToken: String
    ): SellerAccountResponse

    // Update Seller Account
    @PATCH("seller/update")
    @Headers("Content-Type: application/json")
    suspend fun updateSellerAccount(
        @Header("Authorization") authToken: String,
        @Body body: SellerDetails
    ): SimpleResponse

    // Change Seller Password
    @POST("seller/auth/change-password")
    @Headers("Content-Type: application/json")
    suspend fun changePassword(
        @Header("Authorization") authToken: String,
        @Body body: RequestBody
    ): ApiResponse

    // Post Pickup Address
    @POST("seller/pickup-address/add")
    @Headers("Content-Type: application/json")
    suspend fun postPickupAddress(
        @Header("Authorization") authToken: String,
        @Body body: RequestBody
    ): ApiResponse

    // Get Bank Account
    @GET("seller/bank-account")
    suspend fun getBankAccount(
        @Header("Authorization") authToken: String
    ): GetBankAccountResponse

    // Add Bank Account
    @POST("seller/bank-account/add")
    @Headers("Content-Type: application/json")
    suspend fun addBankAccount(
        @Header("Authorization") authToken: String,
        @Body body: RequestBody
    ): BankAccountResponse

    // Update Bank Account
    @PATCH("seller/bank-account/update")
    @Headers("Content-Type: application/json")
    suspend fun updateBankAccount(
        @Header("Authorization") authToken: String,
        @Body body: BankDetails
    ): ApiResponse
}