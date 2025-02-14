package com.anishop.aniShopsellers_android.data.model.orders

data class PendingOrderResponse(
    val status: String,
    val statusOfOrders: String,
    val totalPendingOrders: Int,
    val pendingOrders: List<Order>
)
