package com.anishop.aniShopsellers_android.data.repository.home

import com.anishop.aniShopsellers_android.data.model.common.SimpleResponse
import com.anishop.aniShopsellers_android.data.model.home.DashboardResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AddProductRepositoryInterface {
    suspend fun addNewProduct(
        authToken: String,
        productName: String,
        productDescription: String,
        basePrice: String,
        categoryId: String,
        discountPrice: String,
        currency: String,
        selectedSizes: List<String>,
        quantities: List<String>,
        images: List<File>
    ): Flow<DataState<SimpleResponse>>
}