package com.anishop.aniShopsellers_android.presentation.ui.screens.main.products

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar

@Composable
fun AllProductsScreen(
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigateBack: () -> Unit
) {
    // Fake Data
    val products = listOf(
        ProductUI("11","Latest Men's hoodie blue with anime design", "₹ 1299","4.2",false, R.drawable.product_one,"₹ 999", 23),
        ProductUI("12","Men's hoodie black with anime design", "₹ 2299","4.3",true, R.drawable.product_one, "₹ 1999", 13),
        ProductUI("13","Men's tshirt black with anime design", "₹ 1399","4.4",false, R.drawable.product_one, "₹ 699", 50),
        ProductUI("14","Men's polo blue with anime design", "₹ 999","4.5",false, R.drawable.product_one, "₹ 499", 50),
        ProductUI("15","Men's white hoodie with anime design", "₹ 1599","4.2",true, R.drawable.product_one, "₹ 899", 43),
        ProductUI("16","Men's polo tshirt with anime design", "₹ 999","4.0",true, R.drawable.product_one, "₹ 499", 50),
        ProductUI("17","Men's tshirt black with anime design", "₹ 1399","4.4",false, R.drawable.product_one, "₹ 999", 28),
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "All Products",
                onBackNavigationClick = {
                    onNavigateBack()
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products.size) {index ->
                ProductCard(
                    product = products[index],
                    onProductClick = { /* TODO: Handle product click */ },
                    onHeartClick = { /* TODO: Handle wishlist toggle */ }
                )
            }
        }
    }
}

// Product Card
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductUI,
    onProductClick: (ProductUI) -> Unit,
    onHeartClick: () -> Unit
) {
    Card(
        onClick = { onProductClick(product) },
        modifier = Modifier
            .sizeIn(
                maxWidth = 175.dp,
                minHeight = 170.dp
            )
            .aspectRatio(0.65f/1f),
        colors = CardDefaults.cardColors(
            contentColor = Color(0xFFF3F4F6),
            containerColor =  Color(0xFF191919)
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.72f)
        ) {
            Image(
                painter = painterResource(product.productImage),
                contentDescription = product.productName + product.basePrice,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 5.dp)
                    .size(28.dp)
                    .clickable { onHeartClick() },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = if(product.isWishlisted) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                        contentDescription = if (product.isWishlisted) "Add to favorites" else "Remove from favorites",
                        modifier = Modifier
                            .size(16.dp),
                        tint = if(!product.isWishlisted) MaterialTheme.colorScheme.background else Color(0xFFED1010),
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 6.dp)
                .padding(end = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = product.productName,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleSmall.copy(
                    lineHeight = 18.sp
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.basePrice,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF909DA5)
                    ),
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = product.discountPrice,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight(450),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "-" + product.percentageOff.toString() + "%",
                    style = MaterialTheme.typography.bodySmall.copy(
                        //color = Color(0XFF20ed09) GREEN COLOR
                        color = Color(0xFFf05107)
                    )
                )
            }
        }
    }
}

data class ProductUI(
    val productId: String,
    val productName: String,
    val basePrice: String,
    val averageRating: String,
    val isWishlisted: Boolean,
    @DrawableRes val productImage: Int,
    val discountPrice: String,
    val percentageOff: Int
)