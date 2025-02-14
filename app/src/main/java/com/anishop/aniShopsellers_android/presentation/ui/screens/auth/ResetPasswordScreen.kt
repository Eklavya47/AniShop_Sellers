package com.anishop.aniShopsellers_android.presentation.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.PasswordInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.TextWithDifferentColors
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun ResetPasswordScreen(
    userEmail: String,
    navigateUp: () -> Unit,
    onContinueLogin: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var newPassword by remember { mutableStateOf("") }
    var passwordOneVisibility by remember { mutableStateOf(false) }
    var reEnterPassword by remember { mutableStateOf("") }
    var passwordTwoVisibility by remember { mutableStateOf(false) }
    val otpLength = 6
    val otpValues = remember { List(otpLength) { mutableStateOf("") } }
    var otpEntered by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Flag to track if OTP verification is clicked
    var loginClicked by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Reset Password",
                onBackNavigationClick = {
                    navigateUp()
                }
            )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 10.dp, horizontal = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ){
                        Text(
                            text = "Enter 6 Digit Code",
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = (-0.05).em,
                        )
                        Text(
                            text = "Enter 6 digit code that your receive on your email ($userEmail).",
                            fontSize = 16.sp,
                            color = Color(0xFF808080),
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OTPTextField(
                            otpLength = otpLength,
                            onOtpComplete = { otp ->
                                otpEntered = otp
                            },
                            otpValues = otpValues,
                        )
                        TextWithDifferentColors(
                            text1 = "Email not received?",
                            text2 = " Resend code",
                            color1 = Color(0xFF808080),
                            color2 = Color(0xFF2391CE),
                            modifier = Modifier
                                .align(Alignment.Start)
                                .clickable {
                                    viewModel.forgetPassword(userEmail)
                                }
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Reset Password",
                        modifier = Modifier,
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.05).em,
                    )
                    Text(
                        text = "Set the new password for your account so you can login and access all the features.",
                        fontSize = 16.sp,
                        color = Color(0xFF808080),
                        fontWeight = FontWeight.Normal
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    PasswordInputField(
                        inputTitle = "New Password",
                        isError = false,
                        isVerified = false,
                        password = newPassword,
                        onValueChange = { newPassword = it },
                        placeHolderText = "Enter new password"
                    )
                    PasswordInputField(
                        inputTitle = "Re-Enter Password",
                        isError = false,
                        isVerified = false,
                        password = reEnterPassword,
                        onValueChange = { reEnterPassword = it },
                        placeHolderText = "Re-enter new password"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                GradientButton(
                    text = "Login",
                    onClick = {
                        loginClicked = true
                        viewModel.resetPassword(userEmail, otpEntered, newPassword)
                    },
                    enabled = newPassword == reEnterPassword && newPassword.isNotEmpty() && reEnterPassword.isNotEmpty(),
                    buttonWidth = 1f,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
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

            when (uiState) {
                is UiState.onSuccess -> {
                    if (loginClicked){
                        onContinueLogin()
                        viewModel.resetState()
                    }
                }
                is UiState.onFailure -> {
                    loginClicked = false
                    Toast.makeText(
                        context,
                        (uiState as UiState.onFailure).message,
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.resetState()
                }
                else -> Unit
            }
        }
    }
}