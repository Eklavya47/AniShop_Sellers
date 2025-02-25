package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar

@Composable
fun GeneralStatementScreen(
    onNavigate: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "General Statement",
                onBackNavigationClick = {
                    onNavigate()
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            HorizontalDivider(color = Color(0xFF272727))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Welcome to AniShop Sellers App ",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                lineHeight = 36.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Empowering your business in the ever-growing anime merchandise market.",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "At AniShop, we are passionate about connecting anime enthusiasts with the products they love. As a seller on our platform, you become a vital part of India’s most vibrant anime community. Here’s what we promise",
                color = Color(0xFF8A8A8A)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Expand Your Reach:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Gain access to a large and dedicated audience of anime fans across the country who are actively seeking unique and high-quality merchandise.",
                color = Color(0xFF8A8A8A)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Seamless Selling Experience:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Our intuitive platform is designed to make product listing, inventory management, and order fulfillment simple and efficient.",
                color = Color(0xFF8A8A8A)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Brand Visibility:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Stand out with tools and features that help showcase your brand and products effectively.",
                color = Color(0xFF8A8A8A)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Reliable Support:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Enjoy the backing of a responsive support team ready to assist you with any challenges you face.",
                color = Color(0xFF8A8A8A)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Secure Transactions:",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Benefit from secure payment processing and timely payouts to keep your business running smoothly.\n\n" +
                        "Whether you are selling anime T-shirts, hoodies, phone cases, manga, collectibles, or other merchandise, AniShop provides the perfect platform to grow your business and connect with your target audience.\n\n" +
                        "Join us in creating a thriving marketplace where anime fans can discover the products they love while helping your business reach new heights. Together, let’s redefine the anime shopping experience in India!",
                color = Color(0xFF8A8A8A)
            )
        }
    }
}