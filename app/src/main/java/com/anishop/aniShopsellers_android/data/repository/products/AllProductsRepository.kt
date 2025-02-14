package com.anishop.aniShopsellers_android.data.repository.products

import android.util.Log
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.products.AllProductsResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AllProductsRepository @Inject constructor(
    private val apiService: ApiService
): AllProductsRepositoryInterface {
    override suspend fun getAllProducts(
        authToken: String
    ): Flow<DataState<AllProductsResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getAllProducts(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "$e")
            emit(DataState.Error(e))
        }
    }
}