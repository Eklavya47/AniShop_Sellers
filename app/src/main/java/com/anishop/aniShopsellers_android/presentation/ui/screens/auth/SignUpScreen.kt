package com.anishop.aniShopsellers_android.presentation.ui.screens.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.EmailInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.GoogleAuthButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.PasswordInputField
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common.TextWithDifferentColors
import com.anishop.aniShopsellers_android.presentation.ui.screens.auth.viewModel.AuthViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState

@Composable
fun SignUpScreen(
    onLoginAccountClick: () -> Unit,
    onAccountVerify: (String, Boolean) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var panID by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordVerified by remember { mutableStateOf(false) }
    var isEmailVerified by remember { mutableStateOf(false) }
    var isNameVerified by remember { mutableStateOf(false) }
    var isFormValid by remember { mutableStateOf(false) }
    var isGoogleSignUp by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(email) {
        isEmailError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isEmailVerified = email.isNotEmpty() && !isEmailError
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        //Spacer(modifier = Modifier.height(140.dp))
                        // login headline
                        Column {
                            Text(
                                text = "Create an account",
                                modifier = Modifier,
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = (-0.05).em
                            )
                            Text(
                                text = "Let's create your account.",
                                modifier = Modifier,
                                fontSize = 16.sp,
                                color = Color(0xFF808080),
                                fontWeight = FontWeight.Normal
                            )
                        }
                        // Name input field
                        CustomInputField(
                            fieldTitle = "Full Name",
                            placeholderText = "Enter your full name",
                            input = fullName,
                            onValueChange = { fullName = it },
                            keyboardType = KeyboardType.Text
                        )
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
                        // pan id
                        CustomInputField(
                            fieldTitle = "Pan Id",
                            placeholderText = "Enter your pan id",
                            input = panID,
                            onValueChange = { panID = it },
                            keyboardType = KeyboardType.Number
                            //isVerified = isNameVerified
                        )
                        // terms & conditions
                        TermsAndConditionsText(
                            onTermsAndConditionsClick = { },
                            onPrivacyPolicyClick = { },
                            onCookieUseClick = { }
                        )
                        // Google sign in
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 20.dp)
                        ) {
                            GoogleAuthButton(
                                onClick = {
                                    //signInLauncher.launch(signInClient.signInIntent)
                                },
                                modifier = Modifier.padding(vertical = 6.dp),
                                buttonTitle = "Sign up with google"
                            )
                            GradientButton(
                                text = "Create an Account",
                                onClick = {
                                    /*if (isFormValid) {
                                        isGoogleSignUp = false
                                        viewModel.signUp(
                                            userName = fullName,
                                            userEmail = email,
                                            userPassword = password,
                                            isGoogleSignUp = isGoogleSignUp
                                        )
                                    }*/
                                },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                buttonWidth = 1f,
                                enabled = isFormValid && fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
                            )
                        }
                        TextWithDifferentColors(text1 = "Already have an account?",
                            text2 = " Log In",
                            color1 = Color(0xFF808080),
                            color2 = Color(0xFF2391CE),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    onLoginAccountClick()
                                })
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
                    .clickable(enabled = false) { }, contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
    /*when (uiState) {
        is UiState.onSuccess -> {
            onAccountVerify(email, isGoogleSignUp)
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

@Composable
fun TermsAndConditionsText(
    onPrivacyPolicyClick: () -> Unit,
    onTermsAndConditionsClick: () -> Unit,
    onCookieUseClick: () -> Unit
) {
    var currentTextLayoutResult: TextLayoutResult? = null
    val annotatedText = buildAnnotatedString {
        append("By signing up, you agree to our ")

        pushStringAnnotation(tag = "T&C", annotation = "terms")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Terms")
        }
        pop()
        append(", ")
        pushStringAnnotation(tag = "Privacy", annotation = "privacy")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Privacy Policy")
        }
        pop()
        append(" and ")
        pushStringAnnotation(tag = "Cookies", annotation = "cookies")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            append("Cookie Use")
        }
        pop()
    }
    Text(
        text = annotatedText,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color(0xFF999999)
        ),
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                val layoutResult = currentTextLayoutResult
                layoutResult?.let {
                    val position = layoutResult.getOffsetForPosition(offset)
                    annotatedText.getStringAnnotations(
                        start = position, end = position
                    ).firstOrNull()?.let { annotation ->
                        when (annotation.tag) {
                            "T&C" -> onTermsAndConditionsClick()
                            "Privacy" -> onPrivacyPolicyClick()
                            "Cookies" -> onCookieUseClick()
                        }
                    }
                }
            }
        })
}