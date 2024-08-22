package com.ravemaster.retrofittutorial.data

import com.ravemaster.retrofittutorial.data.models.Product
import kotlinx.coroutines.flow.Flow

//for overriding the get api interface in the implementation class

interface ProductsApiResponseRepository {
    suspend fun getProductsApiResponse(): Flow<Result<List<Product>>>
}