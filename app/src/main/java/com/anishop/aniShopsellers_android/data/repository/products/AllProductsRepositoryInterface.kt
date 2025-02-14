package com.anishop.aniShopsellers_android.data.repository.products

import android.provider.ContactsContract.Data
import com.anishop.aniShopsellers_android.data.model.products.AllProductsResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface AllProductsRepositoryInterface {
    suspend fun getAllProducts(
        authToken: String
    ): Flow<DataState<AllProductsResponse>>
}