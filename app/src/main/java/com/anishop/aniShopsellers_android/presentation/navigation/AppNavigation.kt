package com.anishop.aniShopsellers_android.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()
    val isLoggedIn = authViewModel.isLoggedIn()

    Scaffold() {
        NavHost(
            navController = appNavController,
            startDestination = if (isLoggedIn) AppNavGraph.MainNav else AppNavGraph.AuthNav
        ){
            composable<AppNavGraph.AuthNav> {
                AuthNavigation(
                    onAuthComplete = {
                        appNavController.navigate(AppNavGraph.MainNav) {
                            popUpTo(AppNavGraph.AuthNav) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<AppNavGraph.MainNav> {
                MainNavigation(
                    onLogout = {
                        authViewModel.logoutUser()
                        appNavController.navigate(AppNavGraph.AuthNav) {
                            popUpTo(AppNavGraph.MainNav) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}