package com.anishop.aniShopsellers_android.presentation.ui.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.EmailInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.GoogleAuthButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.PasswordInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.TextWithDifferentColors
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun LoginScreen(
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    var isPasswordVerified by remember { mutableStateOf(false) }
    var isEmailVerified by remember { mutableStateOf(false) }

    var isGoogleSignUp by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(email) {
        isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isEmailVerified = email.isNotEmpty() && !isEmailError
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        //Spacer(modifier = Modifier.height(220.dp))
                        // login headline
                        Column(

                        ) {
                            Text(
                                text = "Login to your account",
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.05).em
                            )
                            Text(
                                text = "It’s great to see you again.",
                                modifier = Modifier,
                                fontSize = 16.sp,
                                color = Color(0xFF808080),
                                fontWeight = FontWeight.Normal
                            )
                        }
                        // Email input field
                        EmailInputField(
                            email = email,
                            onValueChange = { email = it },
                            isError = isEmailError,
                            isVerified = isEmailVerified
                        )
                        // Password input field
                        PasswordInputField(
                            password = password,
                            onValueChange = { password = it },
                            isError = isPasswordError,
                            isVerified = isPasswordVerified,
                            inputTitle = "Password",
                            placeHolderText = "Enter your password"
                        )
                        // forgot password button
                        TextWithDifferentColors(
                            text1 = "Forgot your password?",
                            text2 = " Reset your password",
                            color1 = Color(0xFF808080),
                            color2 = Color(0xFF2391CE),
                            modifier = Modifier
                                .clickable {
                                    onForgotPasswordClick()
                                }
                        )
                        Text(
                            text = "By signing up you agree to our Terms, Privacy Policy and Cookie Use",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color(0xFF808080)
                        )
                        // Google sign in
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                        ) {
                            GoogleAuthButton(
                                onClick = {
                                    //signInLauncher.launch(signInClient.signInIntent)
                                },
                                modifier = Modifier
                                    .padding(vertical = 6.dp),
                                buttonTitle = "Login in with google"
                            )
                            GradientButton(
                                text = "Log in ",
                                onClick = {
                                    /*isGoogleSignUp = false
                                    viewModel.loginEmail(
                                        userEmail = email,
                                        userPassword = password,
                                        isGoogleSignUp = isGoogleSignUp
                                    )*/
                                },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                buttonWidth = 1f,
                                enabled = isEmailVerified && password.isNotEmpty()
                            )
                        }
                        /*Text(
                            text = "Login as a Guest",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    onGuestLoginClick()
                                }
                                .padding(vertical = 4.dp, horizontal = 4.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )*/
                        HorizontalDivider(thickness = 1.dp, color = Color(0xFF2F2F2F))
                        TextWithDifferentColors(
                            text1 = "Don't have an account?",
                            text2 = " Create",
                            color1 = Color(0xFF808080),
                            color2 = Color(0xFF2391CE),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    onCreateAccountClick()
                                }
                        )
                        Spacer(Modifier.height(20.dp))
                    }
                }

            }

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

            onLoginVerifyClick(email, isGoogleSignUp)
            viewModel.resetState()
        }

        is UiState.onFailure -> {
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
        }

        else -> Unit

    }*/
}