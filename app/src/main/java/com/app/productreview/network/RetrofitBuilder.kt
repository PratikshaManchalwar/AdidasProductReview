package com.app.productreview.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL_PRODUCT = "http://localhost:3001/"
    private const val BASE_URL_REVIEW = "http://locahost:3002/"

    private fun getRetrofitProduct(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_PRODUCT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getRetrofitReview(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_REVIEW)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiServiceProduct: ApiService = getRetrofitProduct().create(ApiService::class.java)
    val apiServiceReview: ApiService = getRetrofitReview().create(ApiService::class.java)
}
