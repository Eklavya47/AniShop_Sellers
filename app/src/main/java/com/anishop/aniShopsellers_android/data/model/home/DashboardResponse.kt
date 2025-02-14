package com.anishop.aniShopsellers_android.data.model.home

data class DashboardResponse(
    val status: String,
    val allTypes: OrderSummary,
    val graphData: List<GraphEntry>,
    //val message: String?
)

data class OrderSummary(
    val totalOrders: Int,
    val newOrders: Int,
    val pendingOrders: Int,
    val dispatchedOrders: Int,
    val inComplete: Int,
    val completed: Int,
    val cancelledOrders: Int
)

data class GraphEntry(
    val date: String,
    val totalQuantity: Int,
    val status: String
)