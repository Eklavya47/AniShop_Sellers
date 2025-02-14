package com.anishop.aniShopsellers_android.data.model.orders

data class DispatchedOrdersResponse(
    val status: String,
    val statusOfOrders: String,
    val totalOrders: Int,
    val dispatchedOrders: List<Order>
)
