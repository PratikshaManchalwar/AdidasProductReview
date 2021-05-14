package com.app.productreview.network

import com.app.productreview.model.Product
import com.app.productreview.model.Review
import retrofit2.http.*

interface ApiService {

    @GET("product")
    suspend fun getProductDetails(): List<Product>

    @GET("reviews/{productId}")
    suspend fun getReviews(@Path("productId") productId: String): List<Review>


    @POST("/reviews/{productId}")
    suspend fun addReview(@Path("productId") id: String, @Body review : Review)
    : Review

}