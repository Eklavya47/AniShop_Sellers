package com.anishop.aniShopsellers_android.data.model.account

data class SellerAccountResponse(
    val status: String,
    val seller: Seller
)

data class Seller(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val panId: String,
    val mobileNumber: String,
    val createdAt: String,
    val updatedAt: String,
    val isVerified: Boolean,
    val otp: String?,
    val otpExpiresAt: String?,
    val pickupAddressId: Int?,
    val sellerbankaccountId: Int?
)

data class SellerDetails(
    val name: String? = null,
    val mobileNumber: String? = null
)

