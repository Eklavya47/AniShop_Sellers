package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun MultiSelectionDropdown(
    fieldTitle: String,
    placeholderText: String,
    selectedItems: List<String>,
    onValueChange: (List<String>) -> Unit,
    options: List<String>,
) {
    var expanded by remember { mutableStateOf(false) }

    var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }

    // Modifier to track the size of the TextField
    val modifier = Modifier.onGloballyPositioned { coordinates ->
        textFieldSize = coordinates.size.toSize()
    }

    Box{
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(vertical = 6.dp)
        ){
            Text(
                text = fieldTitle,
                modifier = Modifier
                    .align(Alignment.Start),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            )
            OutlinedTextField(
                value = selectedItems.joinToString(", "),
                onValueChange = {},
                readOnly = true,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { expanded = true },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = "Dropdown Icon",
                        tint = Color.White,
                        modifier = Modifier.clickable { expanded = true }
                    )
                },
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
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = Color(0xFF999999),
                        fontWeight = FontWeight.Normal
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )
        }
        if (expanded) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White)
                    .border(
                        1.dp,
                        Color.Gray,
                        RoundedCornerShape(10.dp)
                    ) // Optional: Border around the dropdown
                    .padding(top = 4.dp)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            val newSelection = selectedItems.toMutableList()
                            if (newSelection.contains(option)) {
                                newSelection.remove(option)  // Remove if already selected
                            } else {
                                newSelection.add(option)  // Add if not selected
                            }
                            onValueChange(newSelection)  // Update selected items
                            expanded = false
                        },
                        text = {
                            Text(text = option)
                        }
                    )
                }
            }
        }
    }
}