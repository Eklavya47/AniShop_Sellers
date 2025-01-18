package com.anishop.aniShopsellers_android.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInputField(
    fieldTitle: String, placeholderText: String, input: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Text(
            text = fieldTitle,
            modifier = Modifier.align(Alignment.Start),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
        OutlinedTextField(
            value = input,
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    text = placeholderText, color = Color(0xFF474747)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth(),
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
            /*trailingIcon = {
                if (isVerified) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.check),
                        contentDescription = "password verified",
                        tint = Color(0xFF0C9409),
                        modifier = Modifier.size(24.dp)
                    )
                }
            },*/
            singleLine = true,
        )
    }
}