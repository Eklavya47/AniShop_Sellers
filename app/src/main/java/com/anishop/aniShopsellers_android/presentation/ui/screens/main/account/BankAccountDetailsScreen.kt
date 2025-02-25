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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel.BankAccountViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun BankAccountDetailsScreen(
    onNavigate: () -> Unit,
    onAddBankAccountClick: () -> Unit,
    onUpdateBankAccountClick: () -> Unit,
    viewModel: BankAccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateGetBankAccount.collectAsState()
    val context = LocalContext.current
    val bankAccount by viewModel.bankAccount.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBankAccount()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Bank Account Details",
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_account),
                text = ("Bank Name - ${bankAccount?.bankName ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_person),
                text = ("Beneficiary Name - ${bankAccount?.benificiaryName ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_pin),
                text = ("Account Number - ${bankAccount?.accountNo ?: ""}")
            )
            DetailsRow(
                icon = ImageVector.vectorResource(R.drawable.ic_password),
                text = ("IFSC Code - ${bankAccount?.ifscCode ?: ""}")
            )
            Spacer(modifier = Modifier.height(10.dp))
            GradientButton(
                text = "Add Bank Account",
                onClick = {onAddBankAccountClick()},
                enabled = true
            )
            GradientButton(
                text = "Update Bank Account",
                onClick = {onUpdateBankAccountClick()},
                enabled = true
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
            viewModel.resetState()
            onNavigate()
        }
        else -> Unit
    }
}