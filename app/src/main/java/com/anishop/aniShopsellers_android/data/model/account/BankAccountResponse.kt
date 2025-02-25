package com.anishop.aniShopsellers_android.data.model.account

data class BankAccountResponse(
    val status: String,
    val message: String,
    val accountAdded: AccountAdded
)

data class AccountAdded(
    val id: Int,
    val bankName: String,
    val benificiaryName: String,
    val accountNo: String,
    val ifscCode: String,
    val isVerified: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val sellerId: Int
)

data class BankDetails(
    val bankName: String? = null,
    val benificiaryName: String? = null,
    val accountNo: String? = null,
    val ifscCode: String? = null
)
