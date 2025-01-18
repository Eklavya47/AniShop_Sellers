package com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anishop.aniShopsellers_android.R

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    email: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    isVerified: Boolean
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = "Email",
            modifier = Modifier
                .align(Alignment.Start),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = "Enter your email address",
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.Normal
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
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
            trailingIcon = {
                if(isVerified) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.check),
                        contentDescription = "password verified",
                        tint = Color(0xFF0C9409),
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                else if(isError) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.warning_circle),
                        contentDescription = null,
                        tint = Color(0xFFED1010)
                    )
                }
            },
            singleLine = true,
            isError = isError
        )
        if(isError) {
            Text(
                text = "Please enter valid email address",
                fontSize = 14.sp,
                fontWeight = FontWeight(510),
                color = Color(0xFFED1010)
            )
        }
    }
}