package com.app.productreview.viewmodel

import com.app.productreview.network.ApiHelper

class ProductRepository constructor(private val apiHelper: ApiHelper) {

    suspend fun getProductDetails() = apiHelper.getProductDetails()
}