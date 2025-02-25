package com.anishop.aniShopsellers_android.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.AddBankAccountScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.AddPickupAddressScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.BankAccountDetailsScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.ChangePasswordScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.GeneralStatementScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.ProfileScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.SettingsScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.UpdateBankAccountScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.UpdateNameOrNumberScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.AddProductScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.HomeScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.DispatchDetailsScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.OrdersScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.ViewDetailsScreen
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.products.AllProductsScreen

@SuppressLint("NewApi")
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
        composable<MainNavGraph.OrdersPage> {
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
                },
                onDispatchClick = { orderId ->
                    mainNavController.navigate(
                        MainNavGraph.DispatchDetailsPage(
                            orderId = orderId
                        )
                    )
                },
                onViewDetailsClick = { orderId ->
                    mainNavController.navigate(
                        MainNavGraph.ViewDetailsPage(
                            orderId = orderId
                        )
                    )
                }
            )
        }

        composable<MainNavGraph.DispatchDetailsPage> {navBackStackEntry ->
            val orderId = navBackStackEntry.toRoute<MainNavGraph.DispatchDetailsPage>().orderId
            DispatchDetailsScreen(
                orderId = orderId,
                onNavigateBack = {
                    mainNavController.navigateUp()
                }
            )
        }

        composable<MainNavGraph.ViewDetailsPage> {navBackStackEntry ->
            val orderId = navBackStackEntry.toRoute<MainNavGraph.ViewDetailsPage>().orderId
            ViewDetailsScreen(
                orderId = orderId,
                onNavigate = {
                    mainNavController.navigateUp()
                },
                onViewOrderStatusClick = {}
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
                    onSellerAccountClick = {
                      mainNavController.navigate(MainNavGraph.Account.SellerAccount)
                    },
                    onGeneralStatementClick = {
                        mainNavController.navigate(MainNavGraph.Account.GeneralStatement)
                    },
                    onBankAccountDetailsClick = {
                        mainNavController.navigate(MainNavGraph.Account.BankAccountDetailsPage)
                    },
                    onLogoutClick = {
                        onLogout()
                    }
                )
            }

            composable<MainNavGraph.Account.GeneralStatement> {
                GeneralStatementScreen(
                    onNavigate = {
                        mainNavController.navigateUp()
                    }
                )
            }

            composable<MainNavGraph.Account.SellerAccount> {
                ProfileScreen(
                    onNavigateBack = {
                        mainNavController.navigateUp()
                    },
                    onUpdateClick = {mainNavController.navigate(MainNavGraph.Account.UpdateNameOrNumberPage)},
                    onChangePasswordClick = {mainNavController.navigate(MainNavGraph.Account.ChangePasswordPage)},
                    onPickupAddressClick = {mainNavController.navigate(MainNavGraph.Account.AddPickAddressPage)}
                )
            }

            composable<MainNavGraph.Account.UpdateNameOrNumberPage> {
                UpdateNameOrNumberScreen(
                    onNavigateBack = {mainNavController.navigateUp()}
                )
            }

            composable<MainNavGraph.Account.ChangePasswordPage> {
                ChangePasswordScreen(
                    onNavigateBack = {mainNavController.navigateUp()}
                )
            }

            composable<MainNavGraph.Account.AddPickAddressPage> {
                AddPickupAddressScreen(
                    onNavigate = {mainNavController.navigateUp()}
                )
            }

            composable<MainNavGraph.Account.BankAccountDetailsPage> {
                BankAccountDetailsScreen(
                    onNavigate = {mainNavController.navigateUp()},
                    onAddBankAccountClick = {mainNavController.navigate(MainNavGraph.Account.AddBankAccountPage)},
                    onUpdateBankAccountClick = {mainNavController.navigate(MainNavGraph.Account.UpdateBankAccountPage)}
                )
            }

            composable<MainNavGraph.Account.AddBankAccountPage> {
                AddBankAccountScreen(
                    onNavigateBack = {mainNavController.navigateUp()}
                )
            }

            composable<MainNavGraph.Account.UpdateBankAccountPage> {
                UpdateBankAccountScreen(
                    onNavigateBack = {mainNavController.navigateUp()}
                )
            }
        }
    }
}