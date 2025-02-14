package com.anishop.aniShopsellers_android.presentation.ui.screens.main.products

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.data.model.products.Product
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.products.viewModel.AllProductsScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun AllProductsScreen(
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit,
    viewModel: AllProductsScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val allProducts by viewModel.allProducts.collectAsState()

    // Fake Data
    /*val products = listOf(
        ProductUI("11","Latest Men's hoodie blue with anime design", "₹ 1299","4.2",false, R.drawable.product_one, 4.5),
        ProductUI("12","Men's hoodie black with anime design", "₹ 2299","4.3",true, R.drawable.product_one, 4.5),
        ProductUI("13","Men's tshirt black with anime design", "₹ 1399","4.4",false, R.drawable.product_one, 4.5),
        ProductUI("14","Men's polo blue with anime design", "₹ 999","4.5",false, R.drawable.product_one, 4.5),
        ProductUI("15","Men's white hoodie with anime design", "₹ 1599","4.2",true, R.drawable.product_one, 4.5),
        ProductUI("16","Men's polo tshirt with anime design", "₹ 999","4.0",true, R.drawable.product_one, 4.5),
        ProductUI("17","Men's tshirt black with anime design", "₹ 1399","4.4",false, R.drawable.product_one, 4.5),
    )*/

    Scaffold(
        topBar = {
            AppTopBar(
                title = "All Products",
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
    ) {innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 5.dp),
                //.background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(allProducts.size) {index ->
                ProductCard(
                    product = allProducts[index],
                    onProductClick = { /* TODO: Handle product click */ },
                )
            }
        }
        if (uiState is UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
    /*when(uiState){
        is UiState.onSuccess ->{

        }
        is UiState.onFailure ->{
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
        }
        else -> Unit
    }*/
}

// Product Card
@Composable
fun ProductCard(
    product: Product,
    onProductClick: (Product) -> Unit,
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
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.images[0])
                    .crossfade(true)
                    .build(),
                contentDescription = "Product Image",
                contentScale = ContentScale.FillBounds,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary, modifier = Modifier.scale(0.5f)
                    )
                },
                success = {
                    SubcomposeAsyncImageContent()
                },
                modifier = Modifier.fillMaxSize()
            )
            /*Image(
                painter = painterResource(product.productImage),
                contentDescription = product.productName + product.basePrice,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            )*/
            /*Card(
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
            }*/
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
                text = product.name,
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
                    text = product.basePrice.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF909DA5)
                    ),
                    //textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.rating_star),
                        contentDescription = "Rating",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                    Text(
                        text = product.averageRating.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
    }
}

/*
data class ProductUI(
    val productId: String,
    val productName: String,
    val basePrice: String,
    val averageRating: String,
    val isWishlisted: Boolean,
    @DrawableRes val productImage: Int,
    val rating: Double,
)*/
