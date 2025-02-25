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
import com.anishop.aniShopsellers_android.data.model.account.BankDetails
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel.BankAccountViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun UpdateBankAccountScreen(
    onNavigateBack: () -> Unit,
    viewModel: BankAccountViewModel = hiltViewModel()
){
    val uiState by viewModel.uiStateUpdateBankAccount.collectAsState()
    val context = LocalContext.current
    var bankName by remember { mutableStateOf("") }
    var beneficiaryName by remember { mutableStateOf("") }
    var ifscCode by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                "Update Bank Account",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            CustomInputField(
                fieldTitle = "Bank Name",
                placeholderText = "Enter Bank Name",
                input = bankName,
                onValueChange = {bankName = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Beneficiary Name",
                placeholderText = "Enter Beneficiary Name",
                input = beneficiaryName,
                onValueChange = {beneficiaryName = it},
                keyboardType = KeyboardType.Unspecified
            )
            CustomInputField(
                fieldTitle = "Account Number",
                placeholderText = "Enter Account Number",
                input = accountNumber,
                onValueChange = {accountNumber = it},
                keyboardType = KeyboardType.Number
            )
            CustomInputField(
                fieldTitle = "IFSC Code",
                placeholderText = "Enter IFSC Code",
                input = ifscCode,
                onValueChange = {ifscCode = it},
                keyboardType = KeyboardType.Unspecified
            )
            Spacer(modifier = Modifier.height(20.dp))
            GradientButton(
                text = "Update Details",
                onClick = {
                    val bankDetails = BankDetails(
                        bankName = bankName.ifEmpty { null },
                        benificiaryName = beneficiaryName.ifEmpty { null },
                        accountNo = accountNumber.ifEmpty { null },
                        ifscCode = ifscCode.ifEmpty { null }
                    )
                    viewModel.updateBankAccount(bankDetails)
                },
                enabled = bankName.isNotEmpty() || beneficiaryName.isNotEmpty() || accountNumber.isNotEmpty() || ifscCode.isNotEmpty()
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
                "Details Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
            onNavigateBack()
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