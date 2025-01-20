package com.anishop.aniShopsellers_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    NavHost(
        navController = appNavController,
        startDestination = AppNavGraph.AuthNav
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