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
import retrofit2.HttpException
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
        images: List<File>,
        variantsSizeFirst: String,
        variantsQuantityFirst: String,
        variantsSizeSecond: String,
        variantsQuantitySecond: String,
        variantsSizeThird: String,
        variantsQuantityThird: String,
        variantsSizeFourth: String,
        variantsQuantityFourth: String
    ): Flow<DataState<SimpleResponse>> = flow {

        /*// Convert variants to MultipartBody.Part
        val variantParts = variants.flatMapIndexed { index, variant ->
            listOf(
                // Add size for each variant
                MultipartBody.Part.createFormData("variants[$index][Size]", variant.first),
                // Add stock quantity for each variant
                MultipartBody.Part.createFormData("variants[$index][Quantity]", variant.second)
            )
        }*/

        // Convert images to MultipartBody.Part
        val imageParts = images.map { image ->
            //val mimeType = Files.probeContentType(Paths.get(image.absolutePath)) ?: "image/jpeg"
            val requestBody = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("images", image.name, requestBody)
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
                variantsSizeFirst.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsQuantityFirst.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsSizeSecond.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsQuantitySecond.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsSizeThird.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsQuantityThird.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsSizeFourth.toRequestBody("text/plain".toMediaTypeOrNull()),
                variantsQuantityFourth.toRequestBody("text/plain".toMediaTypeOrNull()),
                currency.toRequestBody("text/plain".toMediaTypeOrNull()),
                /*variant*/
            )
            Log.d("APIResponse", "$response")
            emit(DataState.Success(response))
        } catch (e: Exception) {
            if (e is HttpException) {
                // This handles the error response (e.g., 400)
                val errorBody = e.response()?.errorBody()?.string() ?: "Unknown error"
                Log.e("APIResponse", "Error response: $errorBody")
                emit(DataState.Error(Exception(errorBody)))
            } else {
                Log.e("APIResponse", "Exception: ${e.message}")
                emit(DataState.Error(e))
            }
        }
    }
}