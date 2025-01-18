package com.anishop.aniShopsellers_android.presentation.ui.screens.auth.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextWithDifferentColors(
    color1: Color,
    color2: Color,
    text1: String,
    text2: String,
    modifier: Modifier
) {
    val annotatedText = buildAnnotatedString {
        pushStyle(SpanStyle(color = color1))
        append(text1)
        pop()
        pushStyle(SpanStyle(color = color2))
        append(text2)
    }
    Text(
        text = annotatedText,
        fontSize = 16.sp,
        modifier = modifier,
        fontWeight = FontWeight.Normal
    )
}