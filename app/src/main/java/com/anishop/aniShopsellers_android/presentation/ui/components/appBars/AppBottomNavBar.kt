package com.anishop.aniShopsellers_android.presentation.ui.components.appBars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.ui.theme.Primary

@Composable
fun AppBottomNavBar(
    currentDestination: NavDestination?,
    onBottomNavItemClick: (MainNavGraph) -> Unit
) {
    val insets = WindowInsets.navigationBars.asPaddingValues()

    HorizontalDivider(thickness = 1.dp, color = Color(0xFF323232))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + insets.calculateBottomPadding())
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        topLevelRoutes.forEach { topLevelRoute ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
            Button(
                onClick = {
                    if (!isSelected)
                        onBottomNavItemClick(topLevelRoute.route)

                },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(6.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            topLevelRoute.selectedIcon
                        ),
                        contentDescription = topLevelRoute.displayName,
                        tint = if (isSelected) Primary else Color.Unspecified,
                        modifier = Modifier
                            .size(22.dp)
                    )
                    Text(
                        text = topLevelRoute.displayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.8.sp,
                        color = if (isSelected) Primary else Color.Unspecified
                    )
                }
            }
        }
    }
}

val topLevelRoutes = listOf(
    TopLevelRoute("Home", R.drawable.ic_home_outlined, MainNavGraph.Home.HomePage),
    TopLevelRoute("Products", R.drawable.ic_products_outlined, MainNavGraph.AllProducts),
    TopLevelRoute("Orders", R.drawable.ic_orders_outlined, MainNavGraph.OrdersPage),
    TopLevelRoute("Account", R.drawable.ic_account_circle_outlined, MainNavGraph.Account.SettingsPage),
)

data class TopLevelRoute<T: Any>(
    val displayName: String,
    @DrawableRes val selectedIcon: Int,
    val route: T
)
