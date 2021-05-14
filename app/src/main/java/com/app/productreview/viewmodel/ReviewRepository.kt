package com.app.productreview.viewmodel

import com.app.productreview.model.Review
import com.app.productreview.network.ApiHelper

class ReviewRepository constructor(private val apiHelper: ApiHelper) {

    suspend fun getReviews(id : String) = apiHelper.getReviews(id)

    suspend fun addReviews(id: String, review : Review) = apiHelper.addReviews(id, review)
}