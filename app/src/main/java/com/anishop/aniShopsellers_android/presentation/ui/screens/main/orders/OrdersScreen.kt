@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.data.model.orders.Order
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel.HomeScreenViewModel
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.viewModel.OrdersScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun OrdersScreen(
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    orderScreenViewModel: OrdersScreenViewModel = hiltViewModel()
) {
    val uiState by orderScreenViewModel.uiState.collectAsState()
    val allOrders by orderScreenViewModel.allOrders.collectAsState()
    val newOrders by orderScreenViewModel.allNewOrders.collectAsState()
    val pendingOrders by orderScreenViewModel.allPendingOrders.collectAsState()
    val dispatchedOrders by orderScreenViewModel.allDispatchedOrders.collectAsState()
    val orderSummary by homeScreenViewModel.orderSummaryList.collectAsState()
    val orderSummaryList = listOf(
        "All Orders" to (orderSummary?.totalOrders ?: 0),
        "Pending" to (orderSummary?.pendingOrders ?: 0),
        "Shipped" to (orderSummary?.dispatchedOrders ?: 0),
        "Canceled" to (orderSummary?.cancelledOrders ?: 0),
        "New Orders" to (orderSummary?.newOrders ?: 0),
        "Completed" to (orderSummary?.completed ?: 0),
        "Return/Refund" to (orderSummary?.inComplete ?: 0)
    )

    val dummyCategoryList = listOf("All Orders", "New Orders", "Dispatched", "Pending", "Return & Replace", "Cancel")
    var activeCategory by remember { mutableStateOf(dummyCategoryList[0]) }

    LaunchedEffect(activeCategory) {
        when(activeCategory){
            "All Orders" -> orderScreenViewModel.getAllOrders()
            "New Orders" -> orderScreenViewModel.getAllNewOrders()
            "Dispatched" -> orderScreenViewModel.getAllDispatchedOrders()
            "Pending" -> orderScreenViewModel.getAllPendingOrders()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Orders",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                //.navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Order Summary Text
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleLarge,
            )
            // Order Summary Horizontal row
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(orderSummaryList.size) { index ->
                    val (title, count) = orderSummaryList[index]
                    OrderSummaryCard(title, count)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Orders Type Scrollable row
            HorizontalCategorySelector(
                categoriesList = dummyCategoryList,
                activeCategory = activeCategory,
                onCategoryChange = { activeCategory = it },
                backGroundColor = MaterialTheme.colorScheme.background,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Vertical List of Orders
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when(activeCategory){
                    "All Orders" -> allOrders?.orders?.let {
                        items(it.size) { index ->
                            OrderCard(
                                order = allOrders!!.orders[index],
                                activeCategory
                            )
                        }
                    }
                    "New Orders" -> newOrders?.newOrders?.let {
                        items(it.size) { index ->
                            OrderCard(order = newOrders!!.newOrders[index], activeCategory)
                        }
                    }
                    "Dispatched" -> dispatchedOrders?.dispatchedOrders?.let {
                        items(it.size) { index ->
                            OrderCard(order = dispatchedOrders!!.dispatchedOrders[index], activeCategory)
                        }
                    }
                    "Pending" -> pendingOrders?.pendingOrders?.let {
                        items(it.size) { index ->
                            OrderCard(order = pendingOrders!!.pendingOrders[index], activeCategory)
                        }
                    }
                }
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

@Composable
fun OrderSummaryCard(title: String, count: Int) {
    Card(
        modifier = Modifier
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleSmall, color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = count.toString(), style = MaterialTheme.typography.headlineSmall, color = Color.White)
        }
    }
}

@Composable
fun HorizontalCategorySelector(
    categoriesList: List<String>,
    activeCategory: String,
    onCategoryChange: (String) -> Unit,
    backGroundColor: Color,
    modifier: Modifier
) {
    val state = rememberLazyListState()
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(color = backGroundColor)
            //.height(36.dp)
    ) {
        items(categoriesList.size) {
            CategoryButton(
                onClick = { if (activeCategory != categoriesList[it]) onCategoryChange(categoriesList[it]) },
                buttonTitle = categoriesList[it],
                isActive = categoriesList[it] == activeCategory
            )
        }
    }
}

@Composable
fun CategoryButton(
    onClick: () -> Unit,
    buttonTitle: String,
    isActive: Boolean,
) {
    Button(
        onClick = { onClick() },
        /*modifier = Modifier
            .sizeIn(maxHeight = 32.dp),*/
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isActive) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.surface,
            contentColor = if(isActive) MaterialTheme.colorScheme.background  else MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(
            text = buttonTitle,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun OrderCard(
    order: Order,
    activeCategory: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(order.product.images[0])
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
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
            )
            /*Image(
                painter = painterResource(order.imageResId),
                contentDescription = "Product Image",
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
            )*/
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = order.product.name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = order.product.createdAt,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Order#: ${order.productId}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))
                when (activeCategory) {
                    "New Orders" -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { /* Handle Cancel */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF25D61)),

                                ) {
                                Text(text = "Cancel")
                            }
                            Button(
                                onClick = { /* Handle Confirm */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F5D0D)),
                            ) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                    "Pending" -> {
                        Button(
                            onClick = { /* Handle Cancel */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6D6D6))
                        ) {
                            Text(
                                text = "Dispatch",
                                color = Color.Black
                            )
                        }
                    }
                    "Shipped" -> {
                        Button(
                            onClick = { /* Handle Cancel */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF012000))
                        ) {
                            Text(
                                text = " Confirmed",
                                color = Color(0xFF04A900)
                            )
                        }
                    }
                    "Canceled" -> {
                        Button(
                            onClick = { /* Handle Cancel */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0x59903434))
                        ) {
                            Text(
                                text = "Canceled",
                                color = Color(0xFFDA0000)
                            )
                        }
                    }
                    "Return/Replace" -> {
                        Button(
                            onClick = { /* Handle Cancel */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF282828))
                        ) {
                            Text(
                                text = "Return",
                                color = Color(0xFF989898)
                            )
                        }
                    }
                    "Dispatched" -> {
                        Button(
                            onClick = { /* Handle Cancel */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF))
                        ) {
                            Text(
                                text = "View Details",
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}