package com.anishop.aniShopsellers_android.presentation.ui.screens.main.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.anishop.aniShopsellers_android.R
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomDropdownField
import com.anishop.aniShopsellers_android.presentation.ui.components.CustomInputField
import com.anishop.aniShopsellers_android.presentation.ui.components.appBars.AppTopBar
import com.anishop.aniShopsellers_android.presentation.ui.components.buttons.GradientButton
import com.anishop.aniShopsellers_android.presentation.ui.screens.main.home.viewModel.AddProductViewModel
import com.anishop.aniShopsellers_android.utils.network.UiState
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddProductScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddProductViewModel = hiltViewModel()
) {
    var productName by remember { mutableStateOf("") }
    var productBarcode by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val categoryOptions = listOf("Shirt", "T-Shirt", "Jeans")
    var selectedSizes by remember { mutableStateOf(listOf<String>()) }
    val sizeOptions = remember { mutableStateOf(listOf("S", "M", "L", "XL")) }
    var productQuantity by remember { mutableStateOf("") }
    var productBasePrice by remember { mutableStateOf("") }
    var productDiscountPrice by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var selectedImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val clipData = data?.clipData
            // Handle multiple selection (if available)
            if (clipData != null) {
                val uris = mutableListOf<Uri>()
                for (i in 0 until clipData.itemCount) {
                    uris.add(clipData.getItemAt(i).uri)
                }
                selectedImages += uris
            } else {
                // Single image selection
                data?.data?.let { uri ->
                    selectedImages += listOf(uri)
                }
            }
        }
    }

    val quantities = productQuantity.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    // Convert selectedImages (List<Uri>) to List<File>
    val selectedFiles = selectedImages.mapNotNull { uri ->
        uriToFile(uri, context)
    }

    // Initially displaying the first variant (size and quantity) inside a Row
    var productVariants by remember { mutableStateOf(listOf<Pair<String, String>>()) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Add Product",
                onBackNavigationClick = {
                    onNavigateBack()
                }
            )
        }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .navigationBarsPadding()
        ) {
            item{
                Box(modifier = Modifier.fillMaxSize()){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Product Name
                        CustomInputField(
                            fieldTitle = "Product Name",
                            placeholderText = "Enter product name",
                            input = productName,
                            onValueChange = { productName = it },
                            keyboardType = KeyboardType.Text
                        )

                        // Product Description
                        CustomInputField(
                            fieldTitle = "Product Description",
                            placeholderText = "Enter product description",
                            input = productDescription,
                            onValueChange = { productDescription = it },
                            keyboardType = KeyboardType.Text
                        )

                        // Product Images
                        AddImage(
                            selectedImages = selectedImages,
                            imagePickerLauncher = imagePickerLauncher,
                            onImageRemoved = { imageUri ->
                                selectedImages = selectedImages - imageUri
                            })

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = Color(0xFF3C3C3C),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )

                        Text(
                            text = "General info",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        // Category dropdown
                        CustomDropdownField(
                            fieldTitle = "Category",
                            placeholderText = "Enter product category",
                            input = category,
                            onValueChange = { category = it },
                            options = categoryOptions
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Product Size and Quantity",
                                modifier = Modifier
                                    .align(Alignment.Start),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(400)
                            )
                            productVariants.forEachIndexed{index, variant ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    ProductDropdownField(
                                        fieldTitle = "Size",
                                        placeholderText = "Enter size",
                                        input = variant.first,
                                        onValueChange = { size ->
                                            productVariants = productVariants.toMutableList().apply { this[index] = size to variant.second }
                                            sizeOptions.value = sizeOptions.value.filterNot { it == size }
                                        },
                                        options = sizeOptions.value,
                                    )
                                    VerticalDivider(
                                        color = Color.Gray,
                                        thickness = 10.dp,
                                        modifier = Modifier.fillMaxHeight()
                                    )
                                    // Product quantity input field
                                    ProductInputField(
                                        fieldTitle = "Quantity",
                                        placeholderText = "Enter quantity",
                                        input = variant.second,
                                        onValueChange = { quantity ->
                                            productVariants = productVariants.toMutableList().apply { this[index] = variant.first to quantity }
                                        },
                                        keyboardType = KeyboardType.Number,
                                    )
                                }
                            }
                            if (productVariants.size < 4) {
                                AddVariantButton {
                                    productVariants = productVariants + ("" to "")
                                }
                            }
                        }

                        // Product base price
                        CustomInputField(
                            fieldTitle = "Product base price",
                            placeholderText = "Enter product base price",
                            input = productBasePrice,
                            onValueChange = {productBasePrice = it},
                            keyboardType = KeyboardType.Number
                        )

                        // Product Discount price
                        CustomInputField(
                            fieldTitle = "Product discount price",
                            placeholderText = "Enter product discount price",
                            input = productDiscountPrice,
                            onValueChange = {productDiscountPrice = it},
                            keyboardType = KeyboardType.Number
                        )

                        // Add product button
                        GradientButton(
                            text = "Add Product",
                            onClick = {
                                // Ensure four variants: fill in missing sizes with 0 quantity
                                val allSizes = listOf("S", "M", "L", "XL")
                                val selectedSizeNames = productVariants.map { it.first }.toSet()
                                val finalVariants = allSizes.map { size ->
                                    if (selectedSizeNames.contains(size)) {
                                        productVariants.find { it.first == size } ?: (size to "0")
                                    } else {
                                        size to "0"
                                    }
                                }

                                viewModel.addNewProduct(
                                    productName = productName,
                                    productDescription = productDescription,
                                    basePrice = productBasePrice,
                                    categoryId = "2",
                                    discountPrice = productDiscountPrice,
                                    currency = "INR",
                                    images = selectedFiles,
                                    variantsSizeFirst = finalVariants[0].first,
                                    variantsQuantityFirst = finalVariants[0].second,
                                    variantsSizeSecond = finalVariants[1].first,
                                    variantsQuantitySecond = finalVariants[1].second,
                                    variantsSizeThird = finalVariants[2].first,
                                    variantsQuantityThird = finalVariants[2].second,
                                    variantsSizeFourth = finalVariants[3].first,
                                    variantsQuantityFourth = finalVariants[3].second
                                )
                            },
                            enabled = productName.isNotEmpty() && productDescription.isNotEmpty()  && category.isNotEmpty() && productBasePrice.isNotEmpty() && productDiscountPrice.isNotEmpty() && selectedImages.isNotEmpty()
                        )
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
    when (uiState) {
        is UiState.onSuccess -> {
            Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show()
            viewModel.resetState()
            onNavigateBack()
        }

        is UiState.onFailure -> {
            Toast.makeText(
                context, (uiState as UiState.onFailure).message, Toast.LENGTH_SHORT
            ).show()
            viewModel.resetState()
        }
        else -> Unit
    }
}

fun openGallery(imagePickerLauncher: ManagedActivityResultLauncher<Intent, androidx.activity.result.ActivityResult>) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple selection
    }
    imagePickerLauncher.launch(intent)
}

@Composable
fun AddImage(
    selectedImages: List<Uri>,
    imagePickerLauncher: ManagedActivityResultLauncher<Intent, androidx.activity.result.ActivityResult>,
    onImageRemoved: (Uri) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .padding(vertical = 6.dp)
    ){
        Text(
            text = "Add Image",
            modifier = Modifier
                .align(Alignment.Start),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_photo_camera_24),
                    contentDescription = "Calendar Icon",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        openGallery(imagePickerLauncher)
                    }
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
                    text = "Upload product images",
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.Normal
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )
        if (selectedImages.isNotEmpty()){
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(selectedImages){ imageUri ->
                    Box(
                        modifier = Modifier.size(100.dp)
                    ){
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Selected Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove Image",
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(20.dp)
                                .clickable {
                                    onImageRemoved(imageUri)
                                }
                        )
                    }
                }
            }
        }
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}")
    try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

@Composable
fun AddVariantButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .height(48.dp),
        border = BorderStroke(width = 1.dp, color = Color(0xFFED1010)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                contentDescription = "Add new Variant",
                tint = Color(0xFFED1010),
                modifier = Modifier
                    .size(22.dp)
            )
            Text(
                text = "Add New Variant",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFFED1010)
                )
            )
        }
    }
}

@Composable
fun ProductDropdownField(
    fieldTitle: String,
    placeholderText: String,
    input: String,
    onValueChange: (String) -> Unit,
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
                value = input,
                onValueChange = {},
                readOnly = true,
                modifier = modifier
                    .width(150.dp)
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
                            onValueChange(option)
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

@Composable
fun ProductInputField(
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
            modifier = Modifier.width(150.dp),
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