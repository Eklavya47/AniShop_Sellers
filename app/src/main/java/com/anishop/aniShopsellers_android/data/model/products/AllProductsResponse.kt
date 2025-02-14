package com.anishop.aniShopsellers_android.data.model.products

data class AllProductsResponse(
    val status: String,
    val products: List<Product>
)

data class Product(
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
    val reviews: List<Review>,
    val category: Category
)

data class Review(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String,
    val orderId: Int,
    val productId: Int,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String
)

data class Category(
    val id: Int,
    val name: String,
    val image: String?,
    val createdAt: String,
    val updatedAt: String
)
