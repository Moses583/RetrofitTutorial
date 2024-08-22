package com.ravemaster.retrofittutorial.data

import coil.network.HttpException
import com.ravemaster.retrofittutorial.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

//this is similar to the request manager class in the java implementation

class ProductsImplementation(
    private val getProducts: GetProducts
): ProductsApiResponseRepository {
    override suspend fun getProductsApiResponse(): Flow<Result<List<Product>>> {
        return flow {
            val productApiResponse = try {
                getProducts.getProducts()
            } catch (e: IOException){
                e.printStackTrace()
                emit(Result.Failure(message = "Error loading products"))
                return@flow
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Result.Failure(message = "Error loading products"))
                return@flow
            } catch (e: Exception){
                e.printStackTrace()
                emit(Result.Failure(message = "Error loading products"))
                return@flow
            }

            emit(Result.Success(productApiResponse.products))
        }
    }

}