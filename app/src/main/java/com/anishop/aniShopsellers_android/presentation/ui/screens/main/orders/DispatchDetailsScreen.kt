package com.anishop.aniShopsellers_android.presentation.ui.screens.main.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton

@Composable
fun DispatchDetailsScreen(
    onNavigateBack: () -> Unit
) {
    var productHeight by remember { mutableStateOf("") }
    var productLength by remember { mutableStateOf("") }
    var productBreadth by remember { mutableStateOf("") }
    var productWeight by remember { mutableStateOf("") }

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
                placeholderText = "Product Height",
                input = productHeight,
                onValueChange = {productHeight = it},
                keyboardType = KeyboardType.Number
            )

            // Product Length
            CustomInputField(
                fieldTitle = "Enter Product Length",
                placeholderText = "Product Length",
                input = productLength,
                onValueChange = {productLength = it},
                keyboardType = KeyboardType.Number
            )

            // Product Breadth
            CustomInputField(
                fieldTitle = "Enter Product Breadth",
                placeholderText = "Product Breadth",
                input = productBreadth,
                onValueChange = {productBreadth = it},
                keyboardType = KeyboardType.Number
            )

            // Product Weight
            CustomInputField(
                fieldTitle = "Enter Product Weight",
                placeholderText = "Product Weight",
                input = productWeight,
                onValueChange = {productWeight = it},
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.weight(1f))
            GradientButton(
                text = "Dispatch Product",
                onClick = { /*TODO*/ },
                enabled = productHeight.isNotEmpty() && productLength.isNotEmpty() && productBreadth.isNotEmpty() && productWeight.isNotEmpty()
            )
        }
    }
}