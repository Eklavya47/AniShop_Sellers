package com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anishop.aniShopsellers_android.R

@Composable
fun PasswordInputField(
    password: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    isVerified: Boolean,
    inputTitle: String,
    placeHolderText: String,
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = inputTitle,
            modifier = Modifier
                .align(Alignment.Start),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
        OutlinedTextField(
            value = password,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = placeHolderText,
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.Normal
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(
                        painter = if (passwordVisibility) painterResource(id = R.drawable.eye)
                        else painterResource(id = R.drawable.eye_off),
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                        tint = if (password.isEmpty()) Color(0xFF474747) else MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF191919),
                focusedContainerColor = Color(0xFF191919),
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = Color(0xFFED1010),
                errorTextColor = Color(0xFF999999),
                cursorColor = Color.White,
                errorCursorColor = Color(0xFFED1010)
            ),
            shape = RoundedCornerShape(10.dp),
            isError = isError,
            singleLine = true
        )
    }
}