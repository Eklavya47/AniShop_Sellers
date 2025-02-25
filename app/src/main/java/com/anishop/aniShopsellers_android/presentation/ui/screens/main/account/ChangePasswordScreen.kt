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
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.PasswordInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.account.viewModel.ProfileScreenViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateChangePassword.collectAsState()
    val context = LocalContext.current
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Change Password",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            PasswordInputField(
                password = oldPassword,
                onValueChange = { oldPassword = it },
                isError = false,
                isVerified = true,
                inputTitle = "Enter Old Password",
                placeHolderText = "Old Password"
            )
            PasswordInputField(
                password = newPassword,
                onValueChange = { newPassword = it },
                isError = false,
                isVerified = true,
                inputTitle = "Enter New Password",
                placeHolderText = "New Password"
            )
            Spacer(modifier = Modifier.height(20.dp))
            GradientButton(
                text = "Change Password",
                onClick = {viewModel.changePassword(oldPassword, newPassword)},
                enabled = oldPassword != newPassword
            )
        }
        if (uiState is UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {when(uiState){
                is UiState.onSuccess ->{
                    Toast.makeText(
                        context,
                        "Password Changed Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
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
                CircularProgressIndicator(color = Color.White)
            }
        }
    }

}