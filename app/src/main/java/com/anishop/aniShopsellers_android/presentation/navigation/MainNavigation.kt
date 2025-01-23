package com.anishop.aniShopsellers_android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.SettingsScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.AddProductScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.HomeScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.OrdersScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.products.AllProductsScreen

@Composable
fun MainNavigation(
    onLogout: () -> Unit
) {
    val mainNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        navController = mainNavController,
        startDestination = MainNavGraph.Home
    ){
        navigation<MainNavGraph.Home>(startDestination = MainNavGraph.Home.HomePage){
            composable<MainNavGraph.Home.HomePage> {
                HomeScreen(
                    onAddNewProductClick = {
                        mainNavController.navigate(MainNavGraph.Home.AddProduct)
                    },
                    onViewDetailsClick = {
                        mainNavController.navigate(MainNavGraph.Home.ViewDetails)
                    },
                    currentDestination = currentDestination,
                    onBottomNavIconClick = { route ->
                        mainNavController.navigate(route) {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    onNavigate = {
                        mainNavController.navigate(MainNavGraph.Account)
                    }
                )
            }
            composable<MainNavGraph.Home.AddProduct> {
                AddProductScreen(
                    onNavigateBack = {
                        mainNavController.navigateUp()
                    }
                )
            }
        }
        composable<MainNavGraph.AllProducts> {
            AllProductsScreen(
                currentDestination = currentDestination,
                onBottomNavIconClick = { route ->
                    mainNavController.navigate(route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                onNavigate = {
                    mainNavController.navigate(MainNavGraph.Account)
                }
            )
        }
        composable<MainNavGraph.Orders> {
            OrdersScreen(
                currentDestination = currentDestination,
                onBottomNavIconClick = { route ->
                    mainNavController.navigate(route) {
                        popUpTo(mainNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                onNavigate = {
                    mainNavController.navigate(MainNavGraph.Account)
                }
            )
        }
        navigation<MainNavGraph.Account>(startDestination = MainNavGraph.Account.SettingsPage){
            composable<MainNavGraph.Account.SettingsPage> {
                SettingsScreen(
                    currentDestination = currentDestination,
                    onBottomNavIconClick = { route ->
                        mainNavController.navigate(route) {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    },
                    onNavigate = {
                        mainNavController.navigateUp()
                    },
                    onLogoutClick = {
                        onLogout()
                    }
                )
            }
        }
    }
}