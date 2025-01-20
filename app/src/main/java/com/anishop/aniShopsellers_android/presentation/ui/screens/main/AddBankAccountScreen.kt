package com.anishop.aniShopsellers_android.presentation.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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
fun AddBankAccount(
    onNavigateBack: () -> Unit,
) {
    var bankName by remember { mutableStateOf("") }
    var beneficiaryName by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var IFSC_Code by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Product",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Bank Name
            CustomInputField(
                fieldTitle = "Bank Name",
                placeholderText = "Enter bank name",
                input = bankName,
                onValueChange = { bankName = it },
                keyboardType = KeyboardType.Text
            )

            // Beneficiary Name
            CustomInputField(
                fieldTitle = "Beneficiary Name",
                placeholderText = "Enter beneficiary name",
                input = beneficiaryName,
                onValueChange = { beneficiaryName = it },
                keyboardType = KeyboardType.Text
            )

            // Account Number
            CustomInputField(
                fieldTitle = "Account Number",
                placeholderText = "Enter account number",
                input = accountNumber,
                onValueChange = { accountNumber = it },
                keyboardType = KeyboardType.Number
            )

            // IFSC Code
            CustomInputField(
                fieldTitle = "IFSC Code",
                placeholderText = "Enter IFSC Code",
                input = IFSC_Code,
                onValueChange = {  IFSC_Code = it },
                keyboardType = KeyboardType.Text
            )

            // Update Bank button
            GradientButton(
                text = "Update Bank",
                onClick = { /* Handle update bank button click */ }
            )
        }
    }
}