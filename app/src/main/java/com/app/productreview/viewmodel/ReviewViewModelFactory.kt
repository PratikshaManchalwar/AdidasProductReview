package com.app.productreview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.productreview.network.ApiHelper

class ReviewViewModelFactory constructor(private val apiHelper: ApiHelper): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(ReviewRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}