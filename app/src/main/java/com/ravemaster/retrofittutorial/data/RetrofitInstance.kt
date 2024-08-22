package com.ravemaster.retrofittutorial.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val getProducts: GetProducts = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GetProducts.BASE_URL)
        .client(client)
        .build()
        .create(GetProducts::class.java)
}