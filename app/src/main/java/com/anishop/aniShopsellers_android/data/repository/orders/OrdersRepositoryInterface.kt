package com.anishop.aniShopsellers_android.data.repository.orders

import com.anishop.aniShopsellers_android.data.model.common.ApiResponse
import com.anishop.aniShopsellers_android.data.model.orders.AllOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.DispatchedOrdersResponse
import com.anishop.aniShopsellers_android.data.model.orders.NewOrderResponse
import com.anishop.aniShopsellers_android.data.model.orders.PendingOrderResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface OrdersRepositoryInterface {

    suspend fun getAllOrders(
        authToken: String
    ): Flow<DataState<AllOrdersResponse>>

    suspend fun getAllNewOrders(
        authToken: String
    ): Flow<DataState<NewOrderResponse>>

    suspend fun getAllPendingOrders(
        authToken: String
    ): Flow<DataState<PendingOrderResponse>>

    suspend fun getAllDispatchedOrders(
        authToken: String
    ): Flow<DataState<DispatchedOrdersResponse>>

    suspend fun confirmOrder(
        authToken: String,
        id: Int
    ): Flow<DataState<ApiResponse>>

    suspend fun cancelOrder(
        authToken: String,
        id: Int
    ): Flow<DataState<ApiResponse>>

    suspend fun dispatchOrder(
        authToken: String,
        id: Int,
        length: String,
        breadth: String,
        height: String,
        weight: String
    ): Flow<DataState<ApiResponse>>

}