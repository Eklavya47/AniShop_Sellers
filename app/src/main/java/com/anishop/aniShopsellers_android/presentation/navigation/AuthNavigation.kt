package com.anishop.aniShopsellers_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.ForgotPasswordScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.LoginScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.ResetPasswordScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.VerificationScreen

@Composable
fun AuthNavigation(
    onAuthComplete: () -> Unit
) {
    val authNavController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = AuthNavGraph.Login
    ){
        composable<AuthNavGraph.Login> {
            LoginScreen(
                onForgotPasswordClick = {
                    authNavController.navigate(AuthNavGraph.ForgotPassword)
                },
                onLoginVerifyClick = { userEmail, userPassword ->
                    authNavController.navigate(
                        AuthNavGraph.Verification(
                            userEmail = userEmail,
                            userPassword = userPassword
                        )
                    )
                },
                onLoginSuccessClick = {
                    onAuthComplete()
                }
            )
        }
        composable<AuthNavGraph.ForgotPassword> {
            ForgotPasswordScreen(
                onSendCodeClick = { email ->
                    authNavController.navigate(
                        AuthNavGraph.ResetPassword(
                            userEmail = email,
                        )
                    )
                },
                navigateUp = { authNavController.navigateUp() }
            )
        }
        composable<AuthNavGraph.Verification> { navBackStackEntry ->
            val userEmail = navBackStackEntry.toRoute<AuthNavGraph.Verification>().userEmail
            val userPassword = navBackStackEntry.toRoute<AuthNavGraph.Verification>().userPassword
            VerificationScreen(
                userEmail = userEmail,
                userPassword = userPassword,
                onContinueClick = {
                    onAuthComplete()
                },
                navigateUp = { authNavController.navigateUp() }
            )
        }
        composable<AuthNavGraph.ResetPassword> { navBackStackEntry ->
            val userEmail = navBackStackEntry.toRoute<AuthNavGraph.ResetPassword>().userEmail
            ResetPasswordScreen(
                userEmail = userEmail,
                navigateUp = { authNavController.navigateUp() },
                onContinueLogin = {
                    authNavController.navigate(AuthNavGraph.Login)
                }
            )
        }
    }
}