package com.anishop.aniShopsellers_android.data.repository.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.anishop.aniShopsellers_android.data.dataSource.remote.ApiService
import com.anishop.aniShopsellers_android.data.model.common.SimpleResponse
import com.anishop.aniShopsellers_android.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

class AddProductRepository @Inject constructor(
    private val apiService: ApiService
): AddProductRepositoryInterface {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addNewProduct(
        authToken: String,
        productName: String,
        productDescription: String,
        basePrice: String,
        categoryId: String,
        discountPrice: String,
        currency: String,
        selectedSizes: List<String>,
        quantities: List<String>,
        images: List<File>
    ): Flow<DataState<SimpleResponse>> = flow {

        // Create Multipart parts for variants (size and quantity)
        val variantParts = mutableListOf<MultipartBody.Part>()

        for (i in selectedSizes.indices) {
            val sizePart = selectedSizes[i].toRequestBody("text/plain".toMediaTypeOrNull())
            val quantityPart = quantities[i].toRequestBody("text/plain".toMediaTypeOrNull())

            // Dynamically create part names for each variant
            variantParts.add(MultipartBody.Part.createFormData("variant_size_${i}", "", sizePart))
            variantParts.add(MultipartBody.Part.createFormData("variant_quantity_${i}", "", quantityPart))
        }

        /*val variantsParts = mutableListOf<MultipartBody.Part>()
        for (i in selectedSizes.indices) {
            if (selectedSizes.size != quantities.size) {
                emit(DataState.Error(IllegalArgumentException("Sizes and quantities lists have different lengths")))
                return@flow
            }

            // Add size as a part
            val sizePart = MultipartBody.Part.createFormData("variants[$i][size]", selectedSizes[i])
            variantsParts.add(sizePart)

            // Add stockQuantity as a part
            val quantityPart = MultipartBody.Part.createFormData("variants[$i][stockQuantity]", quantities[i])
            variantsParts.add(quantityPart)
        }*/

        // Convert images to MultipartBody.Part
        val imageParts = images.map { image ->
            val mimeType = Files.probeContentType(Paths.get(image.absolutePath)) ?: "image/jpeg"
            val requestBody = image.asRequestBody(mimeType.toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images[]", image.name, requestBody)
        }

        emit(DataState.Loading)
        try {
            val response = apiService.addNewProduct(
                authToken,
                productName.toRequestBody("text/plain".toMediaTypeOrNull()),
                productDescription.toRequestBody("text/plain".toMediaTypeOrNull()),
                basePrice.toRequestBody("text/plain".toMediaTypeOrNull()),
                categoryId.toRequestBody("text/plain".toMediaTypeOrNull()),
                discountPrice.toRequestBody("text/plain".toMediaTypeOrNull()),
                imageParts,
                currency.toRequestBody("text/plain".toMediaTypeOrNull()),
                *variantParts.toTypedArray()
            )
            Log.d("APIResponse", "$response")
            emit(DataState.Success(response))
        } catch (e: Exception) {
            Log.e("APIResponse", "$e")
            emit(DataState.Error(e))
        }
    }
}