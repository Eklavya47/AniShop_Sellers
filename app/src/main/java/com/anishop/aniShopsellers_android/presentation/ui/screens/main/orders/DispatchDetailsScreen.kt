package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders.viewModel.OrdersScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun DispatchDetailsScreen(
    orderId: Int,
    onNavigateBack: () -> Unit,
    ordersScreenViewModel: OrdersScreenViewModel = hiltViewModel()
) {
    var productHeight by remember { mutableStateOf("") }
    var productLength by remember { mutableStateOf("") }
    var productBreadth by remember { mutableStateOf("") }
    var productWeight by remember { mutableStateOf("") }

    val uiStateDispatchOrder by ordersScreenViewModel.uiStateDispatchOrder.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Dispatch Details",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Product Height
            CustomInputField(
                fieldTitle = "Enter Product Height",
                placeholderText = "Product Height in cm",
                input = productHeight,
                onValueChange = {productHeight = it},
                keyboardType = KeyboardType.Number
            )

            // Product Length
            CustomInputField(
                fieldTitle = "Enter Product Length",
                placeholderText = "Product Length in cm",
                input = productLength,
                onValueChange = {productLength = it},
                keyboardType = KeyboardType.Number
            )

            // Product Breadth
            CustomInputField(
                fieldTitle = "Enter Product Breadth",
                placeholderText = "Product Breadth in cm",
                input = productBreadth,
                onValueChange = {productBreadth = it},
                keyboardType = KeyboardType.Number
            )

            // Product Weight
            CustomInputField(
                fieldTitle = "Enter Product Weight",
                placeholderText = "Product Weight in kgs",
                input = productWeight,
                onValueChange = {productWeight = it},
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                text = "Dispatch Product",
                onClick = { ordersScreenViewModel.dispatchOrder(orderId, productLength, productBreadth, productHeight, productWeight) },
                enabled = productHeight.isNotEmpty() && productLength.isNotEmpty() && productBreadth.isNotEmpty() && productWeight.isNotEmpty()
            )
        }
        if (uiStateDispatchOrder is UiState.Loading) {
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
    when(uiStateDispatchOrder){
        is UiState.onSuccess ->{
            Toast.makeText(
                context,
                "Order Dispatched Successfully",
                Toast.LENGTH_SHORT
            ).show()
            ordersScreenViewModel.resetState()
            onNavigateBack()
        }
        is UiState.onFailure ->{
            Toast.makeText(
                context,
                (uiStateDispatchOrder as UiState.onFailure).message,
                Toast.LENGTH_SHORT
            ).show()
            ordersScreenViewModel.resetState()
        }
        else -> Unit
    }
}