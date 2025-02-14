package com.anishop.aniShopsellers_android.data.repository.orders

import android.util.Log
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.orders.AllOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.DispatchedOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.NewOrderResponse
import com.anishop.aniShopsellers_android.data.model.orders.PendingOrderResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrdersRepository @Inject constructor(
    private val apiService: ApiService
): OrdersRepositoryInterface {
    override suspend fun getAllOrders(authToken: String): Flow<DataState<AllOrdersResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getAllOrders(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "$e")
            emit(DataState.Error(e))
        }
    }

    override suspend fun getAllNewOrders(authToken: String): Flow<DataState<NewOrderResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getAllNewOrders(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "${e.cause}")
            emit(DataState.Error(e))
        }
    }

    override suspend fun getAllPendingOrders(authToken: String): Flow<DataState<PendingOrderResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getAllPendingOrders(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "${e.message}")
            emit(DataState.Error(e))
        }
    }

    override suspend fun getAllDispatchedOrders(authToken: String): Flow<DataState<DispatchedOrdersResponse>> = flow {
        emit(DataState.Loading)
        try {
            val response = apiService.getAllDispatchedOrders(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "${e.stackTrace}")
            emit(DataState.Error(e))
        }
    }
}