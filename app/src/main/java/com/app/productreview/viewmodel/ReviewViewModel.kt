package com.app.productreview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.productreview.model.Review
import com.app.productreview.resource.Resource
import kotlinx.coroutines.Dispatchers

class ReviewViewModel constructor(private val repository: ReviewRepository)  : ViewModel() {

    fun getReviews(id : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getReviews(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun addReviews(id : String, review : Review) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.addReviews(id, review)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}