package com.anishop.aniShopsellers_android.data.repository.home

import com.anishop.aniShopsellers_android.data.model.home.DashboardResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepositoryInterface {
    suspend fun fetchOrdersData(
        authToken: String
    ): Flow<DataState<DashboardResponse>>
}