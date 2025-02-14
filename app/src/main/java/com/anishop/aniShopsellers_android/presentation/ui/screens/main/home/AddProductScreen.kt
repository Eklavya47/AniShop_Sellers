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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

@RequiresApi(Build.VERSION_CODES.O)
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
    //var productSizes by remember { mutableStateOf("") }
    var selectedSizes by remember { mutableStateOf(listOf<String>()) }
    val sizeOptions = listOf("S", "M", "L", "XL")
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

    /*val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        // Update the selected images list when images are picked
        selectedImages += uris ?: emptyList()
    }*/

    val quantities = productQuantity.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    // Convert selectedImages (List<Uri>) to List<File>
    val selectedFiles = selectedImages.mapNotNull { uri ->
        uriToFile(uri, context)
    }

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

                        // Product Barcode
                        CustomInputField(
                            fieldTitle = "Product Barcode",
                            placeholderText = "Enter product barcode",
                            input = productBarcode,
                            onValueChange = { productBarcode = it },
                            keyboardType = KeyboardType.Number
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

                        // Product Variants
                        MultiSelectionDropdown(
                            fieldTitle = "Product Variants",
                            placeholderText = "Enter product sizes",
                            selectedItems = selectedSizes,
                            onValueChange = {selectedSizes = it},
                            options = sizeOptions
                        )

                        // Product Quantity
                        CustomInputField(
                            fieldTitle = "Product Quantity",
                            placeholderText = "Enter product quantity in the same order as sizes (e.g., 10, 15)",
                            input = productQuantity,
                            onValueChange = {productQuantity = it},
                            keyboardType = KeyboardType.Number
                        )

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
                                viewModel.addNewProduct(
                                    productName = productName,
                                    productDescription = productDescription,
                                    basePrice = productBasePrice,
                                    categoryId = "1",
                                    discountPrice = productDiscountPrice,
                                    currency = "INR",
                                    selectedSizes = selectedSizes,
                                    quantities = quantities,
                                    images = selectedFiles
                                )
                            },
                            enabled = productName.isNotEmpty() && productDescription.isNotEmpty() && productBarcode.isNotEmpty() && category.isNotEmpty() && productBasePrice.isNotEmpty() && productDiscountPrice.isNotEmpty() && selectedSizes.isNotEmpty() && productQuantity.isNotEmpty() && selectedImages.isNotEmpty()
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