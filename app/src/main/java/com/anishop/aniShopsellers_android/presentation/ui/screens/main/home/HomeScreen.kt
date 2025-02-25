package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.data.model.home.processOrders
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel.HomeScreenViewModel
import com.anishop.aniShopsellers_android.ui.theme.Primary
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun HomeScreen(
    onAddNewProductClick: () -> Unit,
    currentDestination: NavDestination?,
    onBottomNavIconClick: (MainNavGraph) -> Unit,
    onNavigate: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val graphData by viewModel.dashboardData.collectAsState()
    val orderSummary by viewModel.orderSummaryList.collectAsState()
    val orderSummaryList = listOf(
        "All Orders" to (orderSummary?.totalOrders ?: 0),
        "New Orders" to (orderSummary?.newOrders ?: 0),
        "Dispatched" to (orderSummary?.dispatchedOrders ?: 0),
        "Pending" to (orderSummary?.pendingOrders ?: 0),
        "Return/Refund" to (orderSummary?.inComplete ?: 0),
        "Completed" to (orderSummary?.completed ?: 0),
        "Canceled" to (orderSummary?.cancelledOrders ?: 0)
    )
    var activeCategoryIndex by remember { mutableStateOf(0) }
    var selectedTimeRange by remember { mutableStateOf("This Month") }

    // Process the orders
    val (weekOrders, monthOrders, yearOrders) = processOrders(graphData)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    OrderSummaryCard(
                        title,
                        count,
                        isSelected = index == activeCategoryIndex, // Check if this card is selected
                        onClick = { activeCategoryIndex = index }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Seals Statistics", style = MaterialTheme.typography.titleLarge)
                TimeRangeSelector(
                    selectedTimeRange = selectedTimeRange,
                    onTimeRangeSelected = { selectedTimeRange = it }
                )
            }

            // Line Graph
            LineChart(weekOrders, monthOrders, yearOrders, selectedTimeRange)

            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                text = "Add New Product",
                onClick = onAddNewProductClick,
                enabled = true,
                modifier = Modifier.padding(bottom = 16.dp)
            )
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
    when(uiState){
        is UiState.onSuccess ->{

        }
        is UiState.onFailure ->{
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
        }
        else -> Unit
    }
}

@Composable
fun TimeRangeSelector(
    selectedTimeRange: String,
    onTimeRangeSelected: (String) -> Unit
) {
    val options = listOf("This Week", "This Month", "This Year")
    val currentIndex = options.indexOf(selectedTimeRange)

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedTimeRange,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(end = 10.dp)
        ) {
            IconButton(
                onClick = {
                    if (currentIndex > 0) onTimeRangeSelected(options[currentIndex - 1])
                },
                enabled = currentIndex > 0,
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Previous",
                    modifier = Modifier.size(35.dp)
                )
            }

            IconButton(
                onClick = {
                    if (currentIndex < options.size - 1) onTimeRangeSelected(options[currentIndex + 1])
                },
                enabled = currentIndex < options.size - 1,
                modifier = Modifier.size(35.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Next",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}

@Composable
fun LineChart(weekOrders: Map<String, Int>, monthOrders: Map<String, Int>, yearOrders: Map<String, Int>, selectedTimeRange: String) {
    val steps = 5
    val ordersData = when (selectedTimeRange) {
        "This Week" -> weekOrders
        "This Month" -> monthOrders
        "This Year" -> yearOrders
        else -> emptyMap()
    }
    val pointsData = ordersData.entries.mapIndexed { index, entry ->
        Point(index.toFloat(), entry.value.toFloat())
    }
    //val pointsData = listOf(Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))

    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointsData.size - 1)
        .labelData { i -> ordersData.keys.elementAtOrNull(i)?.take(3)?.capitalize() ?: "" }
        .labelAndAxisLinePadding(15.dp)
        //.startDrawPadding(20.dp)
        .axisLineColor(Color.White)
        .axisLabelColor(Color.White)
        .build()

    val maxY = (ordersData.values.maxOrNull() ?: 1).toFloat()
    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = maxY / steps
            (i * yScale).toInt().toString()
        }
        .axisLineColor(Color.White)
        .axisLabelColor(Color.White)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = Color(0xFF14B8A6),
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = Color(0xFF14B8A6),
                    ),
                    SelectionHighlightPoint(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF14B8A6),
                                Color.White
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = null,
        backgroundColor = Color.Transparent
    )

    co.yml.charts.ui.linechart.LineChart(
        modifier = Modifier.fillMaxWidth().height(300.dp),
        lineChartData = lineChartData
    )
}

@Composable
fun Tooltip(date: String, status: String) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = date, color = Color.White, fontSize = 14.sp)
            Text(text = status, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun OrderSummaryCard(
    title: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Primary else MaterialTheme.colorScheme.surface
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