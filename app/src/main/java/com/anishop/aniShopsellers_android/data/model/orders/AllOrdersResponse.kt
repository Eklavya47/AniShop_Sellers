package com.anishop.aniShopsellers_android.data.model.orders

import com.anishop.aniShopsellers_android.data.model.products.Category
import com.anishop.aniShopsellers_android.data.model.products.Product
import com.anishop.aniShopsellers_android.data.model.products.Review

data class AllOrdersResponse(
    val status: String,
    val statusOfOrders: String,
    val totalOrders: Int,
    val orders: List<Order>
)

data class Order(
    val id: Int,
    val sellerId: Int,
    val userId: Int,
    val productId: Int,
    val varient: String,
    val isCompleted: Boolean,
    val quantity: Int,
    val price: Int,
    val addressId: Int,
    val isPrepaid: Boolean,
    val razorpayOrderId: String?,
    val razorpayPaymentId: String?,
    val deliveryOrderId: String?,
    val deliveryShipmentId: String?,
    val deliveryAwb: String?,
    val isRefund: Boolean,
    val refundId: String?,
    val isReviewed: Boolean,
    val reviewId: Int?,
    val deliveryDate: String?,
    val statusId: Int,
    val deliveryStatusId: String?,
    val createdAt: String,
    val updatedAt: String,
    val product: OrderProduct,
    val orderStatus: List<OrderStatus>
)

data class OrderProduct(
    val id: Int,
    val name: String,
    val description: String,
    val currency: String,
    val basePrice: Double,
    val discountPrice: Double,
    val percentOff: Int,
    val categoryId: Int,
    val createdAt: String,
    val updatedAt: String,
    val sellerId: Int,
    val images: List<String>,
    val soldCount: Int,
    val averageRating: Double,
)

data class OrderStatus(
    val id: Int,
    val status: String,
    val isCompleted: Boolean,
    val orderId: Int,
    val createdAt: String,
    val updatedAt: String
)

