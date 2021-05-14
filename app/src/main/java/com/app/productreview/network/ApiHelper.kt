package com.app.productreview.network

import com.app.productreview.model.Review


class ApiHelper(private val apiService: ApiService) {

    suspend fun getProductDetails() = apiService.getProductDetails()

    suspend fun getReviews(id : String) = apiService.getReviews(id)

    suspend fun addReviews(id : String, review : Review) = apiService.addReview(id, review)

}