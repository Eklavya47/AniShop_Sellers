package com.anishop.aniShopsellers_android.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainNavGraph {
    @Serializable data object Home: MainNavGraph() {
        @Serializable data object HomePage: MainNavGraph()
        @Serializable data object AddProduct: MainNavGraph()
    }

    @Serializable data object AllProducts: MainNavGraph()

    @Serializable data object Account: MainNavGraph() {
        @Serializable data object SettingsPage: MainNavGraph()
        @Serializable data object SellerAccount: MainNavGraph()
        @Serializable data object GeneralStatement: MainNavGraph()
        @Serializable data object UpdateNameOrNumberPage: MainNavGraph()
        @Serializable data object ChangePasswordPage: MainNavGraph()
        @Serializable data object AddPickAddressPage: MainNavGraph()
        @Serializable data object AddBankAccountPage: MainNavGraph()
        @Serializable data object UpdateBankAccountPage: MainNavGraph()
        @Serializable data object BankAccountDetailsPage: MainNavGraph()
        @Serializable data object SellerFAQ: MainNavGraph()
    }

    @Serializable data object OrdersPage: MainNavGraph()
    @Serializable data class DispatchDetailsPage(val orderId: Int): MainNavGraph()
    @Serializable data class ViewDetailsPage(val orderId: Int): MainNavGraph()
    /*//single routes
    @Serializable data class ProductDetails(val productId: Int) : MainNavGraph()
    @Serializable data class Reviews(val review: List<Review>,val averageReview:String) : MainNavGraph()
    @Serializable data class AddressEntry(val title: String): MainNavGraph()*/
}

@Serializable
sealed class AppNavGraph {
    @Serializable data object MainNav: AppNavGraph()
    @Serializable data object AuthNav: AppNavGraph()
}

@Serializable
sealed class AuthNavGraph {
    @Serializable data object Login: AuthNavGraph()
    @Serializable data object ForgotPassword: AuthNavGraph()
    @Serializable data class Verification(val userEmail:String ,val userPassword:String): AuthNavGraph()
    @Serializable data class ResetPassword(val userEmail:String ): AuthNavGraph()
}