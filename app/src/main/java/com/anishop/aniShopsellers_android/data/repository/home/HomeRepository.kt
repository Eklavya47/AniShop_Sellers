package com.anishop.aniShopsellers_android.data.repository.home

import android.util.Log
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.home.DashboardResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService
): HomeRepositoryInterface {

    override suspend fun fetchOrdersData(
        authToken: String
    ): Flow<DataState<DashboardResponse>>{
        return flow {
            try {
                Log.d("APIResponse","repo is called")
                emit(DataState.Loading)
                val response = apiService.getOrdersData(authToken)
                Log.d("APIResponse", response.toString())
                emit(DataState.Success(response))
            } catch (e: Exception){
                emit(DataState.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}

    /*= flow{
        emit(DataState.Loading)
        try {
            val response = apiService.getOrdersData(authToken)
            emit(DataState.Success(response))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }*/
