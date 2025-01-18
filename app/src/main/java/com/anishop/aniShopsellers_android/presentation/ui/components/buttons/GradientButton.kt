package com.anishop.aniShopsellers_android.presentation.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.anishop.aniShopsellers_android.R

@Composable
fun GradientButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    buttonWidth: Float = 1f,
    fontWeight: FontWeight = FontWeight.Medium,
    fontSize: TextUnit = TextUnit.Unspecified,
    enabled: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    Button(
        onClick = onClick,
        modifier = if(enabled) {
            modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF761221), Color(0xFFEA3539))
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .height(54.dp)
        } else {
            modifier
                .fillMaxWidth()
                .background(Color(0xFF414141), RoundedCornerShape(10.dp))
                .height(54.dp)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(dimensionResource(R.dimen.m)),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = textStyle,
            color = if(enabled) MaterialTheme.colorScheme.onBackground else Color(0xFF6F6F6F),
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = Modifier.padding(dimensionResource(R.dimen.s))
        )
    }
}