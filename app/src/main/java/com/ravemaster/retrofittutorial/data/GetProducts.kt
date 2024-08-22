package com.ravemaster.retrofittutorial.data

import com.ravemaster.retrofittutorial.data.models.ProductApiResponse
import retrofit2.http.GET

//this is similar to the get interface in the java implementation

interface GetProducts {

    @GET("products")
    suspend fun getProducts(): ProductApiResponse

    companion object{
        const val BASE_URL = "https://dummyjson.com/"
    }

//    @Headers("Static header one: example header value",
//        "Static header two: example header value")
//    @GET("example/{id}")
//    suspend fun getExample(
//        @Query("api_key") apiKey: String,
//        @Path("id") id: String,
//        @Header("Dynamic header") exampleHeader: String
//    ): ProductApiResponse

}