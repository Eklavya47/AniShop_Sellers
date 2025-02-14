package com.anishop.aniShopsellers_android.data.model.orders

import com.anishop.aniShopsellers_android.data.model.products.Product

data class NewOrderResponse(
    val status: String,
    val statusOfOrders: String,
    val totalNewOrders: Int,
    val newOrders: List<Order>
)