package com.anishop.aniShopsellers_android.data.repository.account

import com.anishop.aniShopsellers_android.data.model.account.SellerAccountResponse
import com.anishop.aniShopsellers_android.data.model.account.SellerDetails
import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.data.model.common.SimpleResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface ProfileRepositoryInterface {
    suspend fun getSellerAccount(
        authToken: String
    ): Flow<DataState<SellerAccountResponse>>

    suspend fun updateSellerAccount(
        authToken: String,
        sellerDetails: SellerDetails
    ): Flow<DataState<SimpleResponse>>

    suspend fun changePassword(
        authToken: String,
        oldPassword: String,
        newPassword: String
    ): Flow<DataState<ApiResponse>>

    suspend fun postPickupAddress(
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
    ): Flow<DataState<ApiResponse>>
}