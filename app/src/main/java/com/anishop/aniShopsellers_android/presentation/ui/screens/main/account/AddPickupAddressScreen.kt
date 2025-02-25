package com.anishop.aniShopsellers_android.presentation.ui.screens.main.account

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel.ProfileScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun AddPickupAddressScreen(
    onNavigate: () -> Unit,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStatePostPickupAddress.collectAsState()
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Pickup Address",
                onBackNavigationClick = {
                    onNavigate()
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            CustomInputField(
                fieldTitle = "Name",
                placeholderText = "Enter Name",
                input = name,
                onValueChange = {name = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Mobile Number",
                placeholderText = "Enter Mobile Number",
                input = mobileNumber,
                onValueChange = {mobileNumber = it},
                keyboardType = KeyboardType.Number
            )
            CustomInputField(
                fieldTitle = "Pincode",
                placeholderText = "Enter Pincode",
                input = pincode,
                onValueChange = {pincode = it},
                keyboardType = KeyboardType.Number
            )
            CustomInputField(
                fieldTitle = "Locality",
                placeholderText = "Enter Locality Name",
                input = locality,
                onValueChange = {locality = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Address",
                placeholderText = "Enter Address",
                input = address,
                onValueChange = {address = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "District",
                placeholderText = "Enter District Name",
                input = district,
                onValueChange = {district = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "City",
                placeholderText = "Enter City Name",
                input = city,
                onValueChange = {city = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "State",
                placeholderText = "Enter State Name",
                input = state,
                onValueChange = {state = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Country",
                placeholderText = "Enter Country Name",
                input = country,
                onValueChange = {country = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Landmark",
                placeholderText = "Enter Landmark",
                input = landmark,
                onValueChange = {landmark = it},
                keyboardType = KeyboardType.Unspecified
            )
            Spacer(modifier = Modifier.height(20.dp))
            GradientButton(
                text = "Add Pickup Address",
                onClick = {
                    viewModel.postPickupAddress(
                        name,
                        mobileNumber,
                        pincode,
                        locality,
                        address,
                        district,
                        city,
                        state,
                        country,
                        landmark
                    )
                },
                enabled = name.isNotEmpty() && mobileNumber.isNotEmpty() && pincode.isNotEmpty() && locality.isNotEmpty() && address.isNotEmpty() && district.isNotEmpty() && city.isNotEmpty() && state.isNotEmpty() && country.isNotEmpty() && landmark.isNotEmpty()
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
            Toast.makeText(
                context,
                "Pickup Address Added Successfully",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
            onNavigate()
        }
        is UiState.onFailure ->{
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
        }
        else -> Unit
    }
}