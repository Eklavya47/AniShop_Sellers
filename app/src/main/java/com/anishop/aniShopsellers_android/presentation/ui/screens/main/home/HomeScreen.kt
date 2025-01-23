package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar

@Composable
fun HomeScreen(
    onAddNewProductClick: () -> Unit,
    onViewDetailsClick: () -> Unit,
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Verification",
                onBackNavigationClick = {
                    onNavigate()
                },
                navIcon = ImageVector.vectorResource(R.drawable.ic_account_circle_outlined),
                navIconContentDescription = "Go to Account Screen"
            )
        },
        bottomBar = {
            AppBottomNavBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = { route->
                    onBottomNavIconClick(route)
                }
            )
        }
    ){innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {  }
    }
}