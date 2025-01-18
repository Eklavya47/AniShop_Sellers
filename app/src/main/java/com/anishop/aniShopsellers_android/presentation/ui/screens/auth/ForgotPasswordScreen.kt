package com.anishop.aniShopsellers_android.presentation.ui.screens.auth

import android.util.Patterns
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.EmailInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun ForgotPasswordScreen(
    onSendCodeClick: (String) -> Unit,
    navigateUp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isEmailVerified by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(email) {
        isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isEmailVerified = email.isNotEmpty() && !isEmailError
    }

    Scaffold {innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Reset Password",
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.05).em
                    )
                    Text(
                        text = "Let’s get you back into your account",
                        fontSize = 16.sp,
                        color = Color(0xFF808080),
                        fontWeight = FontWeight.Normal
                    )
                }
                EmailInputField(
                    email = email,
                    onValueChange = { email = it },
                    isError = isEmailError,
                    isVerified = isEmailVerified,
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 4.dp)
                )
                Spacer(Modifier.height(11.dp))
                GradientButton(
                    text = "Send OTP",
                    onClick = {
                        //viewModel.forgetPassword(email)
                    },
                    buttonWidth = 1f,
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    enabled = isEmailVerified
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
        /*when (uiState) {

            is UiState.onSuccess -> {

                onSendCodeClick(email)
                viewModel.resetState()
            }

            is UiState.onFailure -> {
                Toast.makeText(
                    context,
                    (uiState as UiState.onFailure).message,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetState()
            }

            else -> Unit
        }*/
    }
}