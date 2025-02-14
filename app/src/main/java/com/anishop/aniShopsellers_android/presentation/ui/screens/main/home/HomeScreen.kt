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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.data.model.home.GraphEntry
import com.anishop.aniShopsellers_android.presentation.navigation.MainNavGraph
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppBottomNavBar
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel.HomeScreenViewModel
import com.anishop.aniShopsellers_android.ui.theme.Primary
import com.anishop.aniShopsellers_android.utils.network.UiState
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@Composable
fun HomeScreen(
    onAddNewProductClick: () -> Unit,
    onViewDetailsClick: () -> Unit,
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
        "Pending" to (orderSummary?.pendingOrders ?: 0),
        "Shipped" to (orderSummary?.dispatchedOrders ?: 0),
        "Canceled" to (orderSummary?.cancelledOrders ?: 0),
        "New Orders" to (orderSummary?.newOrders ?: 0),
        "Completed" to (orderSummary?.completed ?: 0),
        "Return/Refund" to (orderSummary?.inComplete ?: 0)
    )
    var activeCategoryIndex by remember { mutableStateOf(0) }
    var selectedTimeRange by remember { mutableStateOf("This Week") }

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
            LineChartComponent(graphData, selectedTimeRange, orderSummaryList[activeCategoryIndex].first)

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
fun LineChartComponent(graphData: List<GraphEntry>, selectedTimeRange: String, selectedOrderType: String) {
    val context = LocalContext.current
    val chart = remember { LineChart(context) }
    var tooltipData by remember { mutableStateOf<Pair<String, String>?>(null) }
    var tooltipVisible by remember { mutableStateOf(false) }

    val xLabels = when (selectedTimeRange) {
        "This Week" -> listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        "This Month" -> (1..30).map { it.toString() }
        "This Year" -> listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        else -> emptyList()
    }

    val entries = graphData.mapIndexed { index, entry ->
        Entry(index.toFloat(), entry.totalQuantity.toFloat()).apply {
            data = entry
        }
    }

    val dataSet = LineDataSet(entries, selectedOrderType).apply {
        color = Color.Blue.toArgb()
        valueTextColor = Color.White.toArgb()
        setCircleColor(Color.White.toArgb())
        circleRadius = 5f
        setDrawValues(false)
        setDrawCircles(true)
        setDrawFilled(true)
        setDrawHighlightIndicators(false)
        lineWidth = 2f
    }

    val lineData = LineData(dataSet)
    chart.apply {
        data = lineData
        description.isEnabled = false
        xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(xLabels)
            position = XAxis.XAxisPosition.BOTTOM
            textColor = Color.White.toArgb()
        }
        axisLeft.apply {
            textColor = Color.White.toArgb()
            axisMaximum = graphData.maxOfOrNull { it.totalQuantity }?.toFloat() ?: 100f
        }
        axisRight.isEnabled = false
        legend.isEnabled = false
        setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val data = e?.data as? GraphEntry
                data?.let {
                    tooltipData = it.date to it.status
                    tooltipVisible = true
                    Handler(Looper.getMainLooper()).postDelayed({ tooltipVisible = false }, 5000)
                }
            }

            override fun onNothingSelected() {
                tooltipVisible = false
            }
        })
    }

    AndroidView(factory = { chart }, modifier = Modifier.fillMaxWidth().height(200.dp))

    if (tooltipVisible && tooltipData != null) {
        Tooltip(tooltipData!!.first, tooltipData!!.second)
    }
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