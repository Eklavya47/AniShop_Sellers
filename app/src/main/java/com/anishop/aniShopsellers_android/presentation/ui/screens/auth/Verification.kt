package com.anishop.aniShopsellers_android.presentation.ui.screens.auth

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.TextWithDifferentColors
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun VerificationScreen(
    userEmail: String,
    userPassword: String,
    onContinueClick: () -> Unit,
    navigateUp: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val otpLength = 6
    val otpValues = remember { List(otpLength) { mutableStateOf("") } }
    var otpEntered by remember { mutableStateOf("") }
    val uiStateLoginOtpVerify by viewModel.uiStateLoginOtpVerify.collectAsState()
    val uiStateResendOtp by viewModel.uiStateLogin.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Verification",
                onBackNavigationClick = {
                    navigateUp()
                }
            )
        }
    ) {innerPadding ->
        Box(modifier = Modifier.fillMaxSize())
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //Spacer(modifier = Modifier.height(20.dp))
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
                                viewModel.loginEmail(userEmail, userPassword)
                            }
                    )
                    /*Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Email not received?",
                            color = Color(0xFF808080)
                        )
                        Text(
                            text = "Resend code",
                            color = Color(0XFF2391CE),
                            modifier = Modifier
                                .clickable {
                                    //onResendClick()
                                    // receives another OTP
                                }
                        )
                    }*/
                }
                Spacer(Modifier.height(40.dp))
                GradientButton(
                    text = "Enter OTP",
                    onClick = {
                        viewModel.loginOtpVerify(userEmail, otpEntered)
                    },
                    enabled = otpValues.isNotEmpty(),
                    buttonWidth = 1f,
                    modifier = Modifier
                        .padding(horizontal = 2.dp, vertical = 20.dp)
                )
            }
            if (uiStateLoginOtpVerify is UiState.Loading || uiStateResendOtp is UiState.Loading) {
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
        when (uiStateLoginOtpVerify) {
            is UiState.onSuccess -> {
                LaunchedEffect(Unit) {
                    onContinueClick()
                    viewModel.resetState()
                }
            }
            is UiState.onFailure -> {
                LaunchedEffect(uiStateLoginOtpVerify) {
                    Toast.makeText(
                        context,
                        (uiStateLoginOtpVerify as UiState.onFailure).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetState()
                }
            }
            else -> Unit
        }
        when (uiStateResendOtp) {
            is UiState.onSuccess -> {
                Toast.makeText(
                    context,
                    "OTP resent successfully",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetState()
            }
            is UiState.onFailure -> {
                LaunchedEffect(uiStateResendOtp) {
                    Toast.makeText(
                        context,
                        (uiStateLoginOtpVerify as UiState.onFailure).message,
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.resetState()
                }
            }
            else -> Unit
        }
    }
}

@Composable
fun OTPTextField(
    otpLength: Int,
    onOtpComplete: (String) -> Unit = { },
    otpValues: List<MutableState<String>>
) {
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        otpValues.forEachIndexed { index, otpValue ->
            OutlinedTextField(
                value = otpValue.value,
                onValueChange = { newValue ->
                    if (newValue.length <= 1) {
                        otpValue.value = newValue
                        if (newValue.isNotEmpty() && index < otpLength - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                        if (index == otpLength - 1 && newValue.isNotEmpty()) {
                            onOtpComplete(otpValues.joinToString("") { it.value })
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(50.dp, 50.dp)
                    .focusRequester(focusRequesters[index])
                    .onFocusChanged {
                        if (it.isFocused && otpValue.value.isNotEmpty()) {
                            otpValue.value = ""
                        }
                    }
                    .weight(1f)
                ,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF222222),
                    focusedContainerColor = Color(0xFF222222),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.onBackground
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                visualTransformation = VisualTransformation.None, shape = RoundedCornerShape(10.dp)
            )
        }
    }
}