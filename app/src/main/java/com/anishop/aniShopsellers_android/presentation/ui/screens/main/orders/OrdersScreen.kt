package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar

@Composable
fun OrdersScreen(
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit
) {
    val dummyCategoryList = listOf("All Orders", "New Orders", "Dispatched", "Pending", "Return & Replace", "Cancel")
    var activeCategory by remember { mutableStateOf(dummyCategoryList[0]) }

    val orderSummary = listOf(
        "All Orders" to 640,
        "Pending" to 435,
        "Shipped" to 1180,
        "Canceled" to 238,
        "New Orders" to 50,
        "Dispatched" to 70,
        "Completed" to 200,
        "Return/Refund" to 30
    )
    val orders = List(20) { i ->
        Order(
            id = "72827${i + 36}",
            title = "KING Oversized Sweatshirt",
            date = "25 Dec 2024",
            status = when (i % 6) {
                0 -> "New Order"
                1 -> "Pending"
                2 -> "Shipped"
                3 -> "Canceled"
                4 -> "Return/Replace"
                else -> "Dispatched"
            },
            imageResId = R.drawable.product_one
        )
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
                items(orderSummary.size) { index ->
                    val (title, count) = orderSummary[index]
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
                items(orders.size) { index ->
                    OrderCard(order = orders[index])
                }
            }
        }
    }
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
fun OrderCard(order: Order) {
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
            Image(
                painter = painterResource(order.imageResId),
                contentDescription = "Product Image",
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = order.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Text(
                    text = order.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Order#: ${order.id}",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )

                Spacer(modifier = Modifier.weight(1f))
                when (order.status) {
                    "New Order" -> {
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
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF17284E))
                        ) {
                            Text(
                                text = "Processing",
                                color = Color(0xFF0050FF)
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
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC77D00))
                        ) {
                            Text(text = "Dispatched")
                        }
                    }
                }
            }
        }
    }
}

// Data class for Order
data class Order(
    val id: String,
    val title: String,
    val date: String,
    val status: String,
    val imageResId: Int
)