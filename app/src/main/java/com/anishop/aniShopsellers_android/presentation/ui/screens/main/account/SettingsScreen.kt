package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun SettingsScreen(
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit,
    onSellerAccountClick: () -> Unit,
    onGeneralStatementClick: () -> Unit,
    onBankAccountDetailsClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                onBackNavigationClick = {
                    onNavigate()
                }
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
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                //.navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Seller Account Card
            OptionsCard(
                leadingIcon = R.drawable.ic_account_circle_outlined,
                trailingIcon = R.drawable.ic_chevron_right,
                title = "Seller Account",
                onClick = { onSellerAccountClick() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // General Statment
                OptionsCard(
                    leadingIcon = R.drawable.ic_general_statement,
                    trailingIcon = R.drawable.ic_chevron_right,
                    title = "General Statement",
                    onClick = { onGeneralStatementClick() }
                )

                // Language
                OptionsCard(
                    leadingIcon = R.drawable.ic_language,
                    trailingIcon = R.drawable.ic_chevron_right,
                    title = "Bank Account Details",
                    onClick = { onBankAccountDetailsClick() }
                )

                // Sellers FAQ
                OptionsCard(
                    leadingIcon = R.drawable.ic_faq,
                    trailingIcon = R.drawable.ic_chevron_right,
                    title = "Sellers FAQ's",
                    onClick = { /*onOptionsClick(AccountScreenOption.MyOrders)*/ }
                )

                // About
                OptionsCard(
                    leadingIcon = R.drawable.ic_about,
                    trailingIcon = R.drawable.ic_chevron_right,
                    title = "Licenses",
                    onClick = {
                        val intent = Intent(context, OssLicensesMenuActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            // Logout Button
            LogoutButton(
                onClick = onLogoutClick
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

// Logout Button UI
@Composable
fun LogoutButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .height(48.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFED1010)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_logout),
                contentDescription = "logout",
                tint = Color(0xFFED1010),
                modifier = Modifier
                    .size(22.dp)
            )
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFFED1010)
                )
            )
        }
    }
}

// Options Card UI
@Composable
fun OptionsCard(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    trailingIcon: Int,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF191919)
        ),
        shape = RoundedCornerShape(15.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(leadingIcon),
                    contentDescription = title,
                    modifier = Modifier
                        .size(22.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFFF1F1F1)
                    ),
                )
            }
            Icon(
                imageVector = ImageVector.vectorResource(trailingIcon),
                contentDescription = title,
                modifier = Modifier
                    .size(35.dp),
                tint = Color(0xFFB3B3B3)
            )
        }
    }
}